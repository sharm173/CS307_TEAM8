package com.example.Scott.myapplication.backend;

import com.google.appengine.api.utils.SystemProperty;
import com.sun.java.util.jar.pack.*;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Scott on 10/11/2015.
 */
public class databaseConnection {

    public static void doPost(String name) throws IOException {
        String url = "jdbc:google:mysql://endpoints-test-1095:database/helloNames?user=root";
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                if (name == ""); //TODO handle null name;
                else {
                    String statement = "INSERT INTO entries (name) VALUES(?)";
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.setString(1, name);
                    int success = -1;
                    success = stmt.executeUpdate();
                    if (success == 1) {
                        //TODO successfully posted to database
                    }
                    else {
                        //TODO post to database failed
                    }
                }
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<MyBean> goGet() {
        String url = "jdbc:google:mysql://endpoints-test-1095:database/helloNames?user=root";
        ArrayList<MyBean> beans = new ArrayList<MyBean>();

        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                String statement = "SELECT * FROM entries";
                PreparedStatement stmt = conn.prepareStatement(statement);
                ResultSet response = stmt.executeQuery();
                while (response.next()) {
                    MyBean bean = new MyBean();
                    bean.setData(response.getString("name"));
                    beans.add(bean);
                }
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return beans;
    }
}
