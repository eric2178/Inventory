package models;

import data_base_operations.DatabaseOperations;
import interfaces.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import miss_class_objects.Storeroom;

/**
 * The StoreroomModel contains the logic for adding data to the database can add
 * to db here
 *
 * @author Tanisha
 */
public class StoreroomModel
{

    ObservableList<Storeroom> storeroomList;

    public StoreroomModel()
    {
        storeroomList = FXCollections.observableArrayList();
    }

    /**
     * The create_Storeroom method is used to add a Storeroom to the ArrayList
     *
     * @param storeroomName
     * @param userName
     * @return boolean
     */
    public boolean create_Storeroom(String storeroomName, String userName)
    {
        boolean canMakeStoreroom = false;

        if (storeroomName.length() <= 20 && userName.length() <= 11)
        {
            String storeName = lettersAndDigits(storeroomName," ");
            String username = lettersAndDigits(userName,"");

            if ((!storeName.trim().isEmpty()) && (!username.trim().isEmpty()) && !DatabaseOperations.
                exists("storerooms",
                    "storeroomname",
                    storeroomName))
            {
                Storeroom storeroom = new Storeroom(storeName, username, "Y", new Date());

                // Add Storeroom to the database.
                // Function to insert data in data base. Takes an Empoyee object a makes it
                // into a String. The other way threw an exception.
                Function<Storeroom, String> storeRoomString = e -> e.
                    getStoreroomName() + ","
                    + e.getCreatedby() + "," + e.getCreationDate() + "," + e.
                    getStatus();

                // Create a formatted string to insert into database.
                String insertIntoDB = DatabaseOperations.
                    insertIntoDatabase("storerooms", storeRoomString.
                        apply(storeroom), ",");
                DatabaseOperations.
                    createUpdateDeleteInsert(insertIntoDB, "miss");
                canMakeStoreroom = true;
                // Add to database code.
            }
        }
        return canMakeStoreroom;
    }

    public void searchStorerooms(String storeroomname, String status)
    {

        // Search String used to get data from database.
        String searchQuery = "SELECT * FROM storerooms WHERE storeroomname like '%"
            + storeroomname.trim().toUpperCase()
            + "%' AND status like '%"
            + status.trim().toUpperCase()
            + "%'";

        ObjectMapper<Storeroom> employeeMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                // Create Storeroom object
                Storeroom storeroom = new Storeroom();
                storeroom.setStoreroomName(rs.getString("storeroomname"));
                storeroom.setCreatedby(rs.getString("employeeid"));
                storeroom.setStatus(rs.getString("status"));
                storeroom.setCreationDate(new Date());
                // Add object to the list.
                l.add(storeroom);
            }
        });

        if (storeroomList.size() > 0)
        {
            // Reset list;
            storeroomList.clear();
        }
        DatabaseOperations.
            getResultSetIntoAList(searchQuery, storeroomList, employeeMapper);
    }

    public boolean updateStoreroom(String storeroomname, String creator, String status, Date date)
    {
        boolean canUpdate = false;
        
        String storeroom = lettersAndDigits(storeroomname.trim().toUpperCase()," ");
        String createdBy = lettersAndDigits(creator.trim().toUpperCase(),"");
        String stat = lettersAndDigits(status.trim().toUpperCase(),"");
        
        if (!storeroom.isEmpty() && !createdBy.isEmpty() && !stat.isEmpty()
            && storeroom.length() <= 20 && createdBy.length() <= 11 && stat.
            length() == 1)

        {   
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MMM/yyyy");
            String sql = "update STOREROOMS set employeeid = '"
                + createdBy
                + "',creationdate = '"
                + df2.format(date)
                + "',status = '"
                + stat
                + "' where STOREROOMNAME = '"
                + storeroom
                + "'";
            DatabaseOperations.createUpdateDeleteInsert(sql, "miss");
            canUpdate = true;
        }
        
        return canUpdate;
    }

    /*
     The lettersAndDigits makes sure there are
     only letters and digits in a string. All letters
     will be capitalized upon return. Removes all Spaces.
     */
    private String lettersAndDigits(String info, String allowedChar)
    {

        String dataToReturn = "";
        for (int i = 0; i < info.length(); i++)
        {
            if (Character.isLetterOrDigit(info.charAt(i)) || allowedChar.
                charAt(0) == info.charAt(i))
            {
                dataToReturn += info.charAt(i);
            }
        }

        return dataToReturn.trim().toUpperCase();
    }

    /**
     * The getStoreroomList method gets the list of Storerooms
     *
     * @return ArrayList(Storeroom)
     */
    public ObservableList<Storeroom> getStoreroomList()
    {
        return storeroomList;
    }

}
