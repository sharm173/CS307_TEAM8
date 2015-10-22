package com.example.Scott.myapplication.backend;

/**
 * Contains the client IDs and scopes for allowed clients consuming the helloworld API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "replace this with your web client ID";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
  public static final String API_EXPLORER_CLIENT_ID = com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

  // URL to connect to the database
  public static final String DATABASE_URL = "jdbc:google:mysql://xpertiseservergae:xpertise-db/XpertiseDB?user=root";

  // Import path for the Google Driver to enable the google cloud api
  public static final String GOOGLE_DRIVER = "com.mysql.jdbc.GoogleDriver";

  // Used to specify which type of error occurred
  public enum DB_ERROR {INSERT_ERROR, SELECT_ERROR, UPDATE_ERROR, BAD_INPUT_ERROR};
}
