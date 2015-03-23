package data_base_operations;

/**
 *
 * @author Tanisha
 */
public class MISSCreator
{

    /**
     * The createNewMissDataBase creates a new MISS database.
     */
    public static void createNewMissDataBase()
    {
        DatabaseOperations.createNewDatabase("miss");
    }

    /**
     * The createMISSDatabaseTables method creates a new miss database.
     */
    private static void createMISSDatabaseTables()
    {
        DatabaseOperations.createUpdateDeleteInsert("CREATE TABLE employees ("
            + "employeeid VARCHAR(11) NOT NULL PRIMARY KEY,"
            + "firstname VARCHAR(15),"
            + "lastname VARCHAR(15),"
            + "employeeid VARCHAR(11))", "miss");
        
       /////////////////////////////////////////////////////// 
        // Create this table when enum for 
        DatabaseOperations.createUpdateDeleteInsert("CREATE TABLE users ("
            + "employeeid VARCHAR(11) REFERENCES employees(employeeid),"
            + "oldpassword VARCHAR(16),"
            + "newpassword VARCHAR(16))", "miss");///////////
        //////////////////////////////////////////////////////////////
        
        DatabaseOperations.createUpdateDeleteInsert("CREATE TABLE storerooms ("
            + "storeroomname VARCHAR(20) NOT NULL PRIMARY KEY, "
            + "employeeid VARCHAR(11) REFERENCES employees(employeeid),"
            + "creationdate VARCHAR(20))", "miss");

        DatabaseOperations.createUpdateDeleteInsert("CREATE TABLE items ("
            + "itemnumber VARCHAR(11) NOT NULL PRIMARY KEY,"
            + "itemname VARCHAR(20),"
            + "description VARCHAR(80),"
            + "type CHAR(1))", "miss");

        DatabaseOperations.createUpdateDeleteInsert(
            "CREATE TABLE bins (storeroomname VARCHAR(20) REFERENCES storerooms(storeroomname),"
             + "itemnumber VARCHAR(11) REFERENCES items(itemnumber),"
             + "binname VARCHAR(8),"
             + "countdate VARCHAR(20),"
             + "physicalcountdate VARCHAR(20),"
             + "count INTEGER,"
             + "physicalcount INTEGER,"
             + "cost DOUBLE)", "miss");
    }
}
