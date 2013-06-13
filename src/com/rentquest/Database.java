/**
 *
 * @author drenpro
 */
/*
 * Database class using Singleton Design Pattern to ensure that only one object 
 * which interacts with Database is created
 */

package com.rentquest;

/**
 *
 * @author drenpro
 */
import java.sql.*;
public final class Database {
    
    //Variable declaration
    ConnectionDetailsDataType config =null;
    private String url ="jdbc:mysql://localhost:3306/Rentquest";
    private String dbUser = "notroot";
    private String pass = "changeme";
    private Connection conn = null;
    
    String usersTable = "CREATE TABLE IF NOT EXISTS `Users` ("+
  "`Username` varchar(50) NOT NULL,"+
  "`Password` varbinary(512) NOT NULL,"+
  "`First_Name` varchar(50) NOT NULL,"+
  "`Last_Name` varchar(50) NOT NULL,"+
  "`National_ID_No` int(50) NOT NULL,"+
  "`User_Type` text(50) NOT NULL,"+
  "PRIMARY KEY (`Username`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";

String tenantsTable = "CREATE TABLE IF NOT EXISTS `Tenants` ("+
  "`First_Name` varchar(50) NOT NULL,"+
  "`Last_Name` varchar(50) NOT NULL,"+
  "`National_ID_No` int(50) NOT NULL,"+
  "`Room_No` varchar(50) NOT NULL,"+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Gender` text(50) NOT NULL,"+
  "`Tenant_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  "PRIMARY KEY (`Tenant_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String landLordsTable ="CREATE TABLE IF NOT EXISTS `LandLords` ("+
  "`First_Name` varchar(50) NOT NULL,"+
  "`Last_Name` varchar(50) NOT NULL,"+
  "`National_ID_No` int(50) NOT NULL,"+
  "`No_Of_Plots` int(50) NOT NULL,"+
  "`Telephone_No` varchar(50) NOT NULL,"+
  "`Gender` text(50) NOT NULL,"+
  "`LandLord_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  "PRIMARY KEY (`LandLord_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String plotsTable = "CREATE TABLE IF NOT EXISTS `Plots` ("+
  "`Plot_Name` varchar(50) NOT NULL,"+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`No_Of_Rooms` int(50) NOT NULL,"+
  "`Location` text(50) NOT NULL,"+
  "`LandLord_ID` int(50) NOT NULL,"+
  "PRIMARY KEY (`Plot_No`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";

String roomsTable = "CREATE TABLE IF NOT EXISTS `Rooms` ("+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Room_Type` varchar(50) NOT NULL,"+
  "`Room_Status` text(50) NOT NULL,"+
  "`Monthly_Rent` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Room_No` varchar(50) NOT NULL,"+
  "`Room_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  " CONSTRAINT no_same_plot_and_room_no UNIQUE(`Plot_No`,`Room_no`),"+
  "PRIMARY KEY (`Room_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String accountTable = "CREATE TABLE IF NOT EXISTS `Accounts` ("+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Total_Income` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Total_Expenses` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Date` date NOT NULL,"+
  "`Account_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  " PRIMARY KEY (`Account_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String expensesTable = "CREATE TABLE IF NOT EXISTS `Expenses` ("+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Amount` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Expense_Description` text(50) NOT NULL,"+
  "`Date` date NOT NULL,"+
  "`Expense_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  " PRIMARY KEY (`Expense_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String payRentTable  = "CREATE TABLE IF NOT EXISTS `PayRent` ("+
  "`Tenant_ID` int(50) NOT NULL,"+
  "`Amount` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Date_Of_Payment` date NOT NULL,"+
  " `Rent_Type` varchar(50) NOT NULL,"+
  "`PayRent_ID`int(50) NOT NULL AUTO_INCREMENT,"+
  "PRIMARY KEY (`PayRent_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String payRentAllTable  = "CREATE TABLE IF NOT EXISTS `PayRentAll` ("+
  "`Tenant_ID` int(50) NOT NULL,"+
  "`Amount` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Date_Of_Payment` date NOT NULL,"+
  " `Rent_Type` varchar(50) NOT NULL,"+
  "`PayRent_ID`int(50) NOT NULL AUTO_INCREMENT,"+
  "PRIMARY KEY (`PayRent_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

String settingsTable ="CREATE TABLE IF NOT EXISTS `Settings` ("+
  "`Overdue_Days` int(50) NOT NULL,"+
  "`Bonus_Days` int(50) NOT NULL,"+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Auto_Backup_Days` int(50) NOT NULL,"+
  " PRIMARY KEY (`Plot_No`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";

String vacationNoticeTable = "CREATE TABLE IF NOT EXISTS `Vacation_Notice` ("+
  "`Tenant_ID` int(50) NOT NULL,"+
  "`Plot_No` varchar(50) NOT NULL,"+
  "`Expected_Leaving_Date` date NOT NULL,"+
  "PRIMARY KEY (`Tenant_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";

String billsTable = "CREATE TABLE IF NOT EXISTS `Bills` ("+
  "`Tenant_ID` int(50) NOT NULL,"+
  "`Fixed_Charge` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Units` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Unit_Charge` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Utility` text(50) NOT NULL,"+
  "`Bill_Payment_Status` text(50) NOT NULL,"+
  "`Date` date NOT NULL,"+
  "`Total_Bill` DOUBLE PRECISION(50,2) NOT NULL,"+
  "`Bill_ID` int(50) NOT NULL AUTO_INCREMENT,"+
  "PRIMARY KEY (`Bill_ID`)"+
  ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";
    
    
    private static final Database singletoDatabaseObject = new Database();
    
    //private Database constructor
    private Database()
    {
        //add code later if necessary
        //ConnectionDetailsFileManip manip = new ConnectionDetailsFileManip();
        //manip.openFileForReading();
        //config = manip.readRecord();
        //manip.closeFile();
        
        //dbUser = config.getDbUser();
        //pass = config.getDbPass();
    }
    
    /*
     * Public method which returns static Database object
     */
    public static Database getDatabaseInstance()
    {
        return singletoDatabaseObject;
    }
    
    
    /*
     * Returns a ResultSet when passed a query String 
     */
    public ResultSet query(String query){ 
        ResultSet resset=null;
   
           try {
                    PreparedStatement ps = getConnection().prepareStatement(query);
                    
                    ps.execute();
                    
                    resset = ps.getResultSet();
                } 
           catch (Exception e) {
                    throw new RuntimeException(e);
                } 
            
        return resset;
    }//end of method

    

    /*
     * Returns a Database connection
     */
    public Connection getConnection() {
        try{
            // the following assumes that you have a method to return
            // the current db Connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,dbUser,pass);
            
            } catch(Exception e) {
            // NEVER ignore Exceptions. At the very least I usually
            // wrap them in an unchecked Exception, viz:
            throw new RuntimeException(e);
            }
         return conn;
    }
    
    /*
     * Closes Database connection
     */
    public void closeConnection(){
        if(conn!=null){
            try
            {
                conn.close();
            }
            catch(Exception e)
            {
                //GUI Code
                throw new RuntimeException(e);
            }
        }//end of if
    }//end of closeConnection Method
    
    
    
    /*
     * This method excutes any Action query
     * @param query - query String to be executed
     */
    public void runActionQuery(String query){ 
    PreparedStatement ps = null;
    
        try 
        {
             ps = getConnection().prepareStatement(query);
                    
             ps.executeUpdate();
        }
        catch (SQLException e) 
        {
            //GUI code
            throw new RuntimeException(e);
        }//end catch
        
    }//end of runActionQuery Method
    
    
    /*
     * This method  create All Tables Required by the Application.It should be Run by installer;
     */
    public void createAllTables()
    {
        try
        {
            //Create RentQuest Database
            setConnectionDetails(dbUser,"jdbc:mysql://localhost:3306/mysql", pass);
            runActionQuery("CREATE DATABASE Rentquest");
        
            //Create all tables
            setConnectionDetails(dbUser, url, pass);
            
            runActionQuery(usersTable);
            runActionQuery(tenantsTable);
            runActionQuery(landLordsTable);
            runActionQuery(payRentAllTable);
            runActionQuery(payRentTable);
            runActionQuery(accountTable);
            runActionQuery(billsTable);
            runActionQuery(expensesTable);
            runActionQuery(plotsTable);
            runActionQuery(roomsTable);
            runActionQuery(settingsTable);
            runActionQuery(vacationNoticeTable);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        
    }//end of createAllTables
    
    /*
     * This method sets the parameters used to make database connections
     */
    public void setConnectionDetails(String dbUser,String url,String pass)
    {
        this.dbUser = dbUser;
        this.url = url;
        this.pass = pass;
    }
    
}//end of class
