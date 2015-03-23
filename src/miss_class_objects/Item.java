package miss_class_objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Tanisha
 */
public class Item
{
   private SimpleStringProperty itemnumber;
   private SimpleStringProperty itemname;
   private SimpleStringProperty description;
   private StringProperty type;

    public Item(String itemnumber,String itemname, String description, String type)
    {
        this.itemnumber = new SimpleStringProperty(itemnumber.trim().toUpperCase());
        this.itemname = new SimpleStringProperty(itemname.trim().toUpperCase());
        this.description = new SimpleStringProperty(description.trim().toUpperCase());
        this.type = new SimpleStringProperty(type.trim().toUpperCase());
    }
    
    public Item()
    {
        this.itemnumber = new SimpleStringProperty();
        this.itemname = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
    }

    public String getItemnumber()
    {
        return itemnumber.get();
    }

    public void setItemnumber(String itemnumber)
    {
        this.itemnumber.set(itemnumber.trim().toUpperCase());
    }

    public String getDescription()
    {
        return description.get();
    }

    public void setDescription(String description)
    {
        this.description.set(description.trim().toUpperCase());
    }

    public String getType()
    {
        return type.get();
    }

    public void setType(String type)
    {
        this.type.set(type.trim().toUpperCase());
    }

    public String getItemname()
    {
        return itemname.get();
    }

    public void setItemname(String itemname)
    {
        this.itemname.set(itemname.trim().toUpperCase());
    }
   
   
}
