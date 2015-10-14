package com.example.Scott.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "aPIApi",
        version = "v1",
        resource = "aPI",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Scott.example.com",
                ownerName = "backend.myapplication.Scott.example.com",
                packagePath = ""
        )
)
public class APIEndpoint {

    private static final Logger logger = Logger.getLogger(APIEndpoint.class.getName());

    /**
     * This method gets the <code>API</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>API</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getAPI")
    public API getAPI(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getAPI method");
        return null;
    }

    /**
     * This inserts a new <code>API</code> object.
     *
     * @param aPI The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertAPI")
    public API insertAPI(API aPI) {
        // TODO: Implement this function
        logger.info("Calling insertAPI method");
        return aPI;
    }
}