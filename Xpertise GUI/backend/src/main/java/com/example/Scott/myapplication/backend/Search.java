package com.example.Scott.myapplication.backend;

/**
 * Created by TheMaster on 10/28/2015.
 */
public class Search {

    // Returns MyBean object with latitude and longitude values of a bounding box around
    // a central lat long point with a given square radius
    // @param distance: distance of radius in miles
    // TODO: Make this function general for world
    // TODO: Currently, assumes user is in NW quadrant of Earth
    public MyBean boundingBox(double lat_start, double lng_start, double distance){
        MyBean bean = new MyBean();

        // 'Complicated' math to calculate new latitude and longitude
        // Latitude goes North-South
        // Longitude goes East-West
        double radius_earth = 3963.1676;    // miles
        double brng_east = 0;           // 0 degrees converted to radians
        double brng_north = 1.57;       // 90 degrees converted to radians
        double brng_west = 2 * brng_north;
        double brng_south = 3 * brng_north;

        double lat_start_rad = Math.toRadians(lat_start);
        double lng_start_rad = Math.toRadians(lng_start);

        // latitude boundaries
        double boundary_north = Math.asin(
                Math.sin(lat_start_rad) * Math.cos(distance / radius_earth) +
                Math.cos(lat_start_rad) * Math.sin(distance / radius_earth) * Math.cos(brng_north)
        );

        double boundary_south = Math.asin(
                Math.sin(lat_start_rad) * Math.cos(distance / radius_earth) +
                Math.cos(lat_start_rad) * Math.sin(distance / radius_earth) * Math.cos(brng_south)
        );

        // longitude boundaries
        double boundary_east = lng_start_rad + Math.atan2(
                Math.sin(brng_east) * Math.sin(distance / radius_earth) * Math.cos(lat_start_rad),
                Math.cos(distance / radius_earth) - Math.sin(lat_start_rad) * Math.sin(boundary_north)
        );
        double boundary_west = lng_start_rad + Math.atan2(
                Math.sin(brng_west) * Math.sin(distance / radius_earth) * Math.cos(lat_start_rad),
                Math.cos(distance / radius_earth) - Math.sin(lat_start_rad) * Math.sin(boundary_north)
        );

        // change boundaries to degrees
        boundary_north = Math.toDegrees(boundary_north);
        boundary_south = Math.toDegrees(boundary_south);
        boundary_east = Math.toDegrees(boundary_east);
        boundary_west = Math.toDegrees(boundary_west);

        // latitude increaes towards the equator
        bean.setHiLat(boundary_south);
        bean.setLoLat(boundary_north);

        // longitude increases away from the prime meridian
        bean.setHiLng(boundary_west);
        bean.setLoLng(boundary_east);

        return bean;
    }

}
