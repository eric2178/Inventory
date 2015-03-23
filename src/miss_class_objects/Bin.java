package miss_class_objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Tanisha
 */
public class Bin
{
    private SimpleStringProperty storeroomname;
    private SimpleStringProperty itemnumber;
    private SimpleStringProperty binnumber;

    private SimpleIntegerProperty count;
    private SimpleIntegerProperty physicalcount;

    private SimpleDoubleProperty cost;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("dd/MMM/yyyy");

    private SimpleStringProperty countDate;
    private SimpleStringProperty physcountDate;

    public Bin()
    {
        storeroomname = new SimpleStringProperty();
        itemnumber = new SimpleStringProperty();
        binnumber = new SimpleStringProperty();
        count = new SimpleIntegerProperty();
        physicalcount = new SimpleIntegerProperty();
        cost = new SimpleDoubleProperty();
        countDate = new SimpleStringProperty();
        physcountDate = new SimpleStringProperty();
    }

    public Bin(String storeroomname, String itemnumber, String binnumber, Integer count, Integer physicalcount, Double cost, Date countDate, Date physcountDate)
    {
        this.storeroomname = new SimpleStringProperty(storeroomname.trim().toUpperCase());
        this.itemnumber = new SimpleStringProperty(itemnumber.trim().toUpperCase());
        this.binnumber = new SimpleStringProperty(binnumber.trim().toUpperCase());
        this.count = new SimpleIntegerProperty(count);
        this.physicalcount = new SimpleIntegerProperty(physicalcount);
        this.cost = new SimpleDoubleProperty(cost);
        this.countDate = new SimpleStringProperty(dateformat.format(countDate).trim().toUpperCase());
        this.physcountDate = new SimpleStringProperty(dateformat.format(physcountDate).trim().toUpperCase());
    }

    public String getStoreroomname()
    {
        return storeroomname.get();
    }

    public void setStoreroomname(String storeroomname)
    {
        this.storeroomname.set(storeroomname.trim().toUpperCase());
    }

    public String getItemnumber()
    {
        return itemnumber.get();
    }

    public void setItemnumber(String itemnumber)
    {
        this.itemnumber.set(itemnumber.trim().toUpperCase());
    }

    public String getBinnumber()
    {
        return binnumber.get();
    }

    public void setBinnumber(String binnumber)
    {
        this.binnumber.set(binnumber.trim().toUpperCase());
    }

    public Integer getCount()
    {
        return count.get();
    }

    public void setCount(int count)
    {
        this.count.set(count);
    }

    public Integer getPhysicalcount()
    {
        return physicalcount.get();
    }

    public void setPhysicalcount(int physicalcount)
    {
        this.physicalcount.set(physicalcount);
    }

    public Double getCost()
    {
        return cost.get();
    }

    public void setCost(double cost)
    {
        this.cost.set(cost);
    }

    public String getCountDate()
    {
        return countDate.get();
    }

    public void setCountDate(Date countDate)
    {
        this.countDate.set(dateformat.format(countDate).trim().toUpperCase());
    }

    public String getPhyscountDate()
    {
        return physcountDate.get();
    }

    public void setPhyscountDate(Date physcountDate)
    {
        this.physcountDate.set(dateformat.format(physcountDate).trim().toUpperCase());
    } 
}
