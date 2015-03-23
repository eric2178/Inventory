package models;

import data_base_operations.DatabaseOperations;
import java.util.Date;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import miss_class_objects.Bin;

/**
 *
 * @author Eric Moore
 */
public class BinModel
{

    private ObservableList<Bin> binList;

    public BinModel()
    {
        binList = FXCollections.observableArrayList();
    }

    public boolean createBinInDataBase(String storeroomname, String itemnumber, String binname, int count, int physcount, double cost)
    {
        boolean isValid = true;

        String storename = lettersAndDigits(storeroomname.trim(), " ");
        String itemnum = lettersAndDigits(itemnumber.trim(), "");
        String binnum = lettersAndDigits(binname.trim(), "_");

        isValid &= !storename.isEmpty() && storename.length() <= 20;
        isValid &= !itemnum.isEmpty() && itemnum.length() <= 11;
        isValid &= !binnum.isEmpty() && binnum.trim().length() <= 8;
        isValid &= (0 <= count) && (0 <= physcount) && (0.0 <= cost);
        isValid &= DatabaseOperations.
            exists("bins", "binname", binname,"storeroomname",storeroomname,"itemnumber",itemnumber);

        if (isValid)
        {
            Date date = new Date();
           // all data is valid create a bin.
            Bin bin = new Bin(storename,itemnum,binnum,count,physcount,cost,date,date);
            // Function to insert data in data base. Takes an Item object a makes it
            // into a String.
            Function<Bin, String> binFunc = e -> e.getStoreroomname() + ","
                + e.getItemnumber() + "," + e.getBinnumber() + ","
                + e.getCountDate() + "," + e.getPhyscountDate() + "," + e.getCount() + 
                "," + e.getPhysicalcount() + "," + e.getCost();

            // Create a formatted string to insert into database.
            String insertIntoDB = DatabaseOperations.
                insertIntoDatabase("bins", binFunc.apply(bin), ",");
            DatabaseOperations.
                createUpdateDeleteInsert(insertIntoDB, "miss");
        }
        return isValid;
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

    public ObservableList<Bin> getBinList()
    {
        return binList;
    }
}
