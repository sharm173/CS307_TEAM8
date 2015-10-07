/**
 * Created by JoshFoeh on 10/6/15.
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@Path("/connect")
public class ClientComm {

    public String dbms = "mysql";
    public String serverName = "127.0.0.1"; //localhost in this case
    public String portNumber = "8889";
    public String dbName = "xpertise";


    @GET
    @Path("/second")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Profile> produceJSON(){
        ArrayList<Profile> profiles = new ArrayList<>();
        Profile p = new Profile();
        p.setFirstName("Josh");
        p.setLastName("Foeh");
        p.setPassword("pass");
        p.setEmail("jfoeh@purdue.edu");
        p.setCity("West Lafayette");
        p.setDescription("Pretty dec dude");
        p.setLat(1.42);
        p.setLng(4.21);
        profiles.add(p);
        return profiles;
    }

    @POST
    @Path("/first")
    @Consumes(MediaType.APPLICATION_JSON)
    public void consumeJSON(Profile[] profiles){
        //automatically builds profile objects for us from the JSON
    }

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "root");
        connectionProps.put("zeroDateTimeBehavior", "convertToNull");

        if (this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/" + dbName + "?",
                    connectionProps);
        }
        System.out.println("Connected to database");
        return conn;

    }


}
