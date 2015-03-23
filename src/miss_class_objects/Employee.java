package miss_class_objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Tanisha 
 */
public class Employee
{
    private final SimpleStringProperty firstname;           // set by user
    private final SimpleStringProperty lastname;            // set by user
    private final SimpleStringProperty createdby;
    
    private final SimpleStringProperty status;
    private final SimpleStringProperty userid;
    
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    private Date creationDate;
    

    public Employee(String firstname, String lastname, Date date, String employed,String userid,String created)
    {
        this.userid = new SimpleStringProperty(userid.trim().toUpperCase());
        this.firstname =  new SimpleStringProperty(firstname.trim().toUpperCase());
        this.lastname = new SimpleStringProperty(lastname.trim().toUpperCase());
        this.setCreationDate(date);
        this.status = new SimpleStringProperty(employed.trim().toUpperCase());
        this.createdby = new SimpleStringProperty(created.trim().toUpperCase());
    }
    
    public Employee()
    {
        this.userid = new SimpleStringProperty();
        this.firstname =  new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.setCreationDate(new Date());
        this.status = new SimpleStringProperty();
        this.createdby = new SimpleStringProperty();
    }
    
    public String getUserid()
    {
        return userid.get().trim().toUpperCase();
    }
    public void setUserid(String userid)
    {
        this.userid.set(userid.trim().toUpperCase());
    }

    public String getFirstname()
    {
        return firstname.get().trim().toUpperCase();
    }

    public void setFirstname(String firstname)
    {
        this.firstname.set(firstname.trim().toUpperCase());
    }

    public String getLastname()
    {
        return lastname.get().trim().toUpperCase();
    }

    public void setLastname(String lastname)
    {
        this.lastname.set(lastname.trim().toUpperCase());
    }

    public String getCreatedby()
    {
        return createdby.get();
    }

    public void setCreatedby(String createdby)
    {
        this.createdby.set(createdby.trim().toUpperCase(Locale.FRENCH));
    }
    /**
     * The
     *
     * @return creationDate
     */
    public String getCreationDate()
    {   
        return dateFormat.format(creationDate);
    }

    /**
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getStatus()
    {
        return status.get();
    }

    public void setStatus(String status)
    {
        this.status.set(status.trim().toUpperCase());
    }

    public DateFormat getDateFormat()
    {
        return dateFormat;
    }
    
    
}
