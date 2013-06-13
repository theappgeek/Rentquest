/*
 * This class is currently Deprecated.
 */
package com.rentquest;

import java.io.Serializable;

/**
 *
 * @author drenpro
 */
public class ConnectionDetailsDataType implements Serializable{
    
    //Variable Declaration
    String dbUser = null;
    String dbPass = null;
    
    //constructor
    public ConnectionDetailsDataType(String User, String pass)
    {
        this.dbPass = pass;
        this.dbUser = User;
    }
    
    public void setDbUser(String user)
    {
        this.dbUser = user;
    }
    
    public void setDbPass(String pass)
    {
        this.dbPass = pass;
    }
    
    public String getDbUser()
    {
        return dbUser;
    }
    
    public String getDbPass()
    {
        return dbPass;
    }
    
}//end of classs
