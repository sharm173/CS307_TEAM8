#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <iostream>
#include <string.h>
#include <pthread.h>
#include <fcntl.h>
#include <dirent.h>
#include <signal.h>
#include <dlfcn.h>
#include <link.h>
#include <errno.h>
#include <pthread.h>
#include <signal.h>
#include <error.h>
#include <pwd.h>

int QueueLength = 5;

void poolOfThreads(int slaveSocket);
void poolSlave(int masterSocket);
void createThread(int masterSocket);
void forkServer(int masterSocket);
void iterativeServer(int masterSocket);
void send404(int socket, char* contentType);
void httpHeader(int masterSocket, char* contentType);
void help();
void processRequestThread(int masterSocket);
void child(int sig);
const char* serverType;
pthread_mutex_t m;
pthread_mutexattr_t attr;
int sort;
int port;
int requests;
int first;


int main(int argc, char ** argv) {

	// Debug for arguments check
	/*
	for (int i = 0; i < argc; i++) {
	printf("argv[%d]: %s\n", i, argv[i]);
	}
	*/

	// Print usage if not enough arguments
	if (argc > 3) help();

	//Initialize some variables
	sort = 0;
	requests = 0;
	first = 0;

	//Set the flag for what type of server (iterative, fork, etc.) is to be run
	char* flag = (char*)malloc(1024 * sizeof(char));
	if (argc < 2) {
		strcpy(flag, "default");
		port = 60215;
	}
	else if (argv[1][0] == '-' && argv[2] == NULL) {
		if (argv[1][1] == 'f' || argv[1][1] == 't' || argv[1][1] == 'p') strcpy(flag, argv[1]);
		else help();
		port = 60215;
	}
	else if (argv[1][0] == '-' && argv[2] != NULL) {
		if (argv[1][1] == 'f' || argv[1][1] == 't' || argv[1][1] == 'p') strcpy(flag, argv[1]);
		else help();
		if (argv[2][0] == '-') help();
		else port = atoi(argv[2]);
		if (port < 1024 || port > 65536) help();
	}

	// Get the port from the arguments
	else if (argv[1] != NULL && argv[2] == NULL) {
		port = atoi(argv[1]);
		if (port < 1024 || port > 65536) help();
	}
	else help();
	pthread_mutexattr_init(&attr);
	pthread_mutex_init(&m, &attr);

	// Set the IP address and port for this server
	struct sockaddr_in serverIPAddress;
	memset(&serverIPAddress, 0, sizeof(serverIPAddress));
	serverIPAddress.sin_family = AF_INET;
	serverIPAddress.sin_addr.s_addr = INADDR_ANY;
	serverIPAddress.sin_port = htons((u_short)port);

	// Allocate a socket
	int masterSocket = socket(PF_INET, SOCK_STREAM, 0);
	if (masterSocket < 0) {
		perror("socket");
		exit(-1);
	}

	// Set socket options to reuse port. Otherwise we will
	// have to wait about 2 minutes before reusing the same port number
	int optval = 1;
	int err = setsockopt(masterSocket, SOL_SOCKET, SO_REUSEADDR,
		(char *)&optval, sizeof(int));

	// Bind the socket to the IP address and port
	int error = bind(masterSocket,
		(struct sockaddr *)&serverIPAddress,
		sizeof(serverIPAddress));
	if (error) {
		perror("bind");
		exit(-1);
	}

	// Put socket in listening mode and set the 
	// size of the queue of unprocessed connections
	error = listen(masterSocket, QueueLength);
	if (error) {
		perror("listen");
		exit(-1);
	}
	sigset(SIGCHLD, child);
	if (flag != NULL) {
		char a = flag[1];
		switch (a) {
		case 'f':
			printf("Fork Server Selected\n");
			printf("Listening on port: %d\n", port);
			serverType = "fork";
			forkServer(masterSocket);
			break;

		case 't':
			printf("Thread Server Selected\n");
			printf("Listening on port: %d\n", port);
			serverType = "thread";
			createThread(masterSocket);
			break;

		case 'p':
			printf("Pool of Threads Server Selected\n");
			printf("Listening on port: %d\n", port);
			serverType = "poolOfThreads";
			poolOfThreads(masterSocket);
			break;

		default:
			printf("Iterative Server Selected\n");
			printf("Listening on port: %d\n", port);
			serverType = "iterative";
			iterativeServer(masterSocket);
			break;
		}
	}
}

void child(int sig) {
	int status;
	struct rusage usage;
	pid_t pid2 = wait3(&status, 0, &usage);
}

void poolOfThreads(int slaveSocket) {
	pthread_t thread[5];
	pthread_create(&thread[0], NULL, (void *(*)(void *))poolSlave, (void*)slaveSocket);
	pthread_create(&thread[1], NULL, (void *(*)(void *))poolSlave, (void*)slaveSocket);
	pthread_create(&thread[2], NULL, (void *(*)(void *))poolSlave, (void*)slaveSocket);
	pthread_create(&thread[3], NULL, (void *(*)(void *))poolSlave, (void*)slaveSocket);
	pthread_create(&thread[4], NULL, (void *(*)(void *))poolSlave, (void*)slaveSocket);
	pthread_join(thread[0], NULL);
	pthread_join(thread[1], NULL);
	pthread_join(thread[2], NULL);
	pthread_join(thread[3], NULL);
	pthread_join(thread[4], NULL);
}

void poolSlave(int masterSocket) {
	while (1) {
		struct sockaddr clientIPAddress;
		int alen = (int) sizeof(clientIPAddress);
		int slaveSocket = accept(masterSocket, (struct sockaddr *)&clientIPAddress, (socklen_t*)&alen);
		if (slaveSocket < 0) {
			iterativeServer(masterSocket);
			//perror("accept");
			//exit(1);
		}
		printf("Successfully created slave socket\n");
		printf("Processing Request\n");
		dispatchHTTP(slaveSocket);
		printf("Request processed, closing slave socket\n");
		shutdown(slaveSocket, SHUT_RDWR);
		close(slaveSocket);
	}
}

void processRequestThread(int masterSocket) {
	dispatchHTTP(masterSocket);
	shutdown(masterSocket, SHUT_RDWR);
	close(masterSocket);
}

void createThread(int masterSocket) {
	pthread_t thread;
	while (1) {
		struct sockaddr clientIPAddress;
		int alen = sizeof(clientIPAddress);
		int slaveSocket = accept(masterSocket, (struct sockaddr *)&clientIPAddress, (socklen_t*)&alen);
		if (slaveSocket >= 0) {
			pthread_attr_t attr;
			pthread_attr_init(&attr);
			pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
			pthread_create(&thread, &attr, (void *(*)(void *))processRequestThread, (void*)slaveSocket);
		}
	}
}

void forkServer(int masterSocket) {
	while (1) {
		struct sockaddr clientIPAddress;
		int alen = (int) sizeof(clientIPAddress);
		int slaveSocket = accept(masterSocket, (struct sockaddr *)&clientIPAddress, (socklen_t*)&alen);
		if (slaveSocket < 0) {
			forkServer(masterSocket);
			//removed error check here since conflict with child() cleanup
			//perror("accept");
			//exit(1);
		}
		int ret = fork();
		if (ret == 0) {
			dispatchHTTP(slaveSocket);
			shutdown(slaveSocket, SHUT_RDWR);
			close(slaveSocket);
			exit(0);
		}
		close(slaveSocket);
	}
}

void iterativeServer(int masterSocket) {
	while (1) {
		struct sockaddr clientIPAddress;
		int alen = (int) sizeof(clientIPAddress);
		int slaveSocket = accept(masterSocket, (struct sockaddr *)&clientIPAddress, (socklen_t*)&alen);
		if (slaveSocket < 0) {
			iterativeServer(masterSocket);
			//removed error check here since conflict with child() cleanup
			//perror("accept");
			//exit(1);
		}
		printf("Successfully created slave socket\n");
		printf("Processing Request\n");
		dispatchHTTP(slaveSocket);
		printf("Request processed, closing slave socket\n");
		shutdown(slaveSocket, SHUT_RDWR);
		close(slaveSocket);
	}
}

void help() {
	printf("The format of the command should be:\n");
	printf("myhttpd [-f|-t|-p] [<port>]\n");
	printf("If no port is specified default port is 60215\n");
	printf("If no flag is specified default server type is iterative\n");
	exit(1);
}

void send404(int socket, char* contentType) {
	const char* notFound = "File not Found";
	const char* space = " ";
	const char* clrf = "\r\n";
	const char* protocol = "HTTP/1.1";
	write(socket, protocol, strlen(protocol));
	write(socket, space, 1);
	write(socket, "404", 3);
	write(socket, "File", 4);
	write(socket, "Not", 3);
	write(socket, "Found", 5);
	write(socket, clrf, 2);
	write(socket, "Server:", 7);
	write(socket, space, 1);
	write(socket, serverType, strlen(serverType));
	write(socket, "Content-type:", 13);
	write(socket, space, 1);
	write(socket, contentType, strlen(contentType));
	write(socket, clrf, 2);
	write(socket, clrf, 2);
	write(socket, notFound, strlen(notFound));
}

void httpHeader(int masterSocket, char* contentType) {
	write(masterSocket, "HTTP/1.1", 8);
	write(masterSocket, " ", 1);
	write(masterSocket, "200", 3);
	write(masterSocket, " ", 1);
	write(masterSocket, "OK", 2);
	write(masterSocket, "\r\n", 2);
	write(masterSocket, "Server:", 7);
	write(masterSocket, " ", 1);
	write(masterSocket, serverType, strlen(serverType));
	write(masterSocket, "\r\n", 2);
	write(masterSocket, "Content-type:", 13);
	write(masterSocket, " ", 1);
	write(masterSocket, contentType, strlen(contentType));
	write(masterSocket, "\r\n", 2);
	write(masterSocket, "\r\n", 2);
	return;
}

void dispatchHTTP(int masterSocket) {
	int n;
	int err;
	int length1 = 0;
	int length = 0;
	int gotGet = 0;
	int gotPost = 0;
	int gotContentType = 0;
	int writeToFile = 0;
	char newChar;
	char oldChar;
	char* curr_string = (char*)malloc(1024 * sizeof(char));
	char* docPath;
	char* temp;
	char* holder;
	char* contentType;
	int file;

	printf("Begin reading http request\n");
	//Reads the HTTP/1.1 request one char at a time and stores the document path
	while ((n = read(masterSocket, &newChar, sizeof(newChar)))) {
		
		//Writing the body of the HTTP/1.1 request to a file for JSON parsing later
		if (writeToFile == 1) {
			write(file, newChar, 1);
		}

		length++;

		//A space has been found. In HTTP/1.1, everything before the space is the request type (GET, POST, etc.)
		if (newChar == ' ') {

			//Check for the GET type of request
			temp = strstr(curr_string, "GET");
			if (temp != NULL && gotGet == 0) {
				length1 = length;
				gotGet = 1;
			}
			//A GET type request has been found, store the document path that preceeds the second space
			else if (gotGet == 1 && newChar == ' ') {
				curr_string[length - 1] = 0;
				holder = &curr_string[length1];
				docPath = (char*)malloc(1024 * sizeof(char));
				strcpy(docPath, holder);
				docPath[strlen(docPath)] = 0;
			}

			//Check for the POST type of request
			temp = strstr(curr_string, "POST");
			if (temp != NULL && gotPost == 0) {
				length1 = length;
				gotPost = 1;
			}
			//A POST type request has been found, store the document path that preceeds the second space
			else if (gotPost == 1 && newChar == ' ') {
				curr_string[length - 1] = 0;
				holder = &curr_string[length1];
				docPath = (char*)malloc(1024 * sizeof(char));
				strcpy(docPath, holder);
				docPath[strlen(docPath)] = 0;
			}

			//Check for the Content-Type
			temp = strstr(curr_string, "Content-Type:");
			if (temp != NULL && gotContentType == 0) {
				length1 = length;
				gotContentType = 1;
			}
		}

		//A carriage return has been found. In HTTP/1.1, signals the end of a line of the header 
		else if (newChar == '\n' && oldChar == '\r') {
			//Found the empty line between headers and body
			if (curr_string[0] == '\n') {
				//TODO
				//probably start writing the body to a file until end of read stream?
				writeToFile = 1;
				file = open("temp.txt", O_RDWR | O_TRUNC | O_CREAT);
				if ((dir = opendir(filePath)) == NULL) {
					perror("Directory error");
					exit(1);
				}
			}
			//Check for Content-Type
			else if (gotContentType == 1) {
				curr_string[length - 1] = 0;
				holder = &curr_string[length1];
				contentType = (char*)malloc(1024 * sizeof(char));
				strcpy(contentType, holder);
				contentType[strlen(docPath)] = 0;
			}

			//Always reset to take in a new line if a carriage return is found
			free(curr_string);
			curr_string = (char*)malloc(1024 * sizeof(char));
			length = 0;
			oldChar = '\0';
			newChar = '\0';
		}
		//Store the read character into a char array and copy it to a holder for later comparison purposes
		else {
			oldChar = newChar;
			curr_string[length - 1] = newChar;
		}
		//Continue read HTTP/1.1 request loop
	}

	printf("Attempting to send file\n");
	char count[1];
	httpHeader(masterSocket, contentType);
	//write(masterSocket, crlf, 2);
	//printf("Sending document data.");
	while (read(file, count, 1) == 1) {
		if (write(masterSocket, count, 1) != 1) {
			perror("write");
			exit(1);
		}
	}
	printf("\n");
	printf("File sucessfully sent\n");

	return;
}