package miss_class_objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 * Used to create a Storeroom object.
 *
 * @author Eric Moore
 */
public class Storeroom
{
    
    private final SimpleStringProperty storeroomName; 
    private final SimpleStringProperty createdby;
    private final SimpleStringProperty status;
    private Date creationDate;
    private final DateFormat datFormatter = new SimpleDateFormat("dd/MMM/yyyy");

    /**
     * The Storeroom no arg constructor is used to initialize the
     * SimpleStringProperties.
     */
    public Storeroom()
    {
        this.storeroomName = new SimpleStringProperty();
        this.createdby = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.setCreationDate(new Date());
    }

    /**
     * Constructor
     *
     * @param storeroomName
     * @param creationDate Use a date object today's time and date
     * @param userName
     * @param active
     */
    public Storeroom(String storeroomName,String createdby, String status,Date creationDate)
    {
        this.storeroomName = new SimpleStringProperty(storeroomName.trim().toUpperCase());
        this.createdby = new SimpleStringProperty(createdby.trim().toUpperCase());
        this.status = new SimpleStringProperty(status.trim().toUpperCase());
        this.setCreationDate(creationDate);
    }

    /**
     *
     * @return storeroomName
     */
    public String getStoreroomName()
    {
        return this.storeroomName.get();
    }

    /**
     *
     * @param storeroomName
     */
    public void setStoreroomName(String storeroomName)
    {
        this.storeroomName.set(storeroomName.trim().toUpperCase());
    }

    /**
     *
     * @return creationDate
     */
    public String getCreationDate()
    {
        return this.datFormatter.format(creationDate);
    }

    /**
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return userName
     */
    public String getCreatedby()
    {
        return this.createdby.get();
    }

    // Create a user class for this.
    public void setCreatedby(String createdby)
    {
        this.createdby.set(createdby.trim().toUpperCase());
    }
    
    public String getStatus()
    {
        return this.status.get();
    }
    
    public void setStatus(String status)
    {
        if (status.equalsIgnoreCase("Y") || status.equalsIgnoreCase("N"))
        {
            this.status.set(status.trim().toUpperCase());
        }
        else
        {
            this.status.set("N");
        }
    }
    
    public DateFormat getDatFormatter()
    {
        return this.datFormatter;
    }
    
}
