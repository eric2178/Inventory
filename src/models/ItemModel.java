package models;

import data_base_operations.DatabaseOperations;
import interfaces.ObjectMapper;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import miss_class_objects.Item;

/**
 *
 * @author Tanisha
 */
public class ItemModel
{

    private ObservableList<Item> itemList;
    
    public ItemModel()
    {
        itemList = FXCollections.observableArrayList();
    }

    public boolean createItemInDB(String itemnumber, String itemname, String description, String type)
    {
        boolean canAddToDb = true;
        Item item = new Item();
        item.setItemnumber(lettersAndDigits(itemnumber, ""));
        item.setItemname(lettersAndDigits(itemname, " "));
        item.setDescription(lettersAndDigits(description, " "));
        item.setType(lettersAndDigits(type, ""));

        canAddToDb &= !(item.getItemnumber().isEmpty()) && item.getItemnumber().
            length() <= 11;
        canAddToDb &= !(item.getItemname().isEmpty()) && item.getItemname().
            length() <= 20;
        canAddToDb &= !(item.getDescription().isEmpty()) && item.
            getDescription().length() <= 80;
        canAddToDb &= !(item.getType().isEmpty()) && item.getType().length() == 1;

        canAddToDb &= !DatabaseOperations.
            exists("items", "itemnumber", itemnumber);
        System.out.println(canAddToDb);
        if (canAddToDb)
        {
            // Function to insert data in data base. Takes an Item object a makes it
            // into a String.
            Function<Item, String> itemFunc = e -> e.getItemnumber() + ","
                + e.getItemname() + "," + e.getDescription() + ","
                + e.getType();

            // Create a formatted string to insert into database.
            String insertIntoDB = DatabaseOperations.
                insertIntoDatabase("items", itemFunc.apply(item), ",");
            DatabaseOperations.
                createUpdateDeleteInsert(insertIntoDB, "miss");
        }

        return canAddToDb;
    }

    
    public void searchItems(String itemnumber, String itemname, String description, String type)
    {
        // Search String used to get data from database.
        String searchQuery = "SELECT * FROM items WHERE itemnumber like '%"
            + itemnumber.trim().toUpperCase()
            + "%' AND itemname like '%"
            + itemname.trim().toUpperCase()
            + "%' AND description LIKE '%"
            + description.trim().toUpperCase()
            + "%' AND type LIKE '%"
            + type.trim().toUpperCase()
            + "%'";

        ObjectMapper<Item> itemMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                // Create employee object
                Item item = new Item();
                item.setItemnumber(rs.getString("itemnumber"));
                item.setItemname(rs.getString("itemname"));
                item.setDescription(rs.getString("description"));
                item.setType("type");

                // Add object to the list.
                l.add(item);
            }
        });

        DatabaseOperations.
            getResultSetIntoAList(searchQuery, itemList, itemMapper);
    }

    /*
     The lettersAndDigits makes sure there are
     only letters and digits in a string. All letters
     will be capitalized. Everything else is removed
     from the Streing
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

    public ObservableList<Item> getItemList()
    {
        return itemList;
    }
    
    
}
