package cz.muni.fi.pa165.rest;

/**
 * Represents the entry points for the API
 * this list can be increased so that it contains all the 
 * other URIs also for the sub-resources so that it can 
 * reused globally from all the controllers
 * 
 * @author Dominik Gmiterko
 */
public abstract class ApiUris {
    public static final String ROOT_URI_CUSTOMER = "/customer";
    public static final String ROOT_URI_EMPLOYEE = "/employee";
    public static final String ROOT_URI_DOG = "/dog";
}
