
package interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import miss_class_objects.Employee;

/**
 * 
 * @author Tanisha
 */
public interface ObjectMapper<T>
{

    /**
     * The mapObjectsToList
     * @param list
     * @param rs
     * @throws java.sql.SQLException
     */
    public void mapObjectsToList(ObservableList<T> list,ResultSet rs)  throws SQLException;
    
    /*
    Below is how the ObjectMapper can be defined when placing a table from 
    a database mappping each result set to an object placing the object into a list.
    
    public static ObjectMapper mapperMapped()
    {
        ObjectMapper<Store> myMapper = ((l, rs) ->
        {
                while (rs.next())
                {
                    Store store = new Store();
                    store.setStoreroomName(rs.getString("storeroom"));
                    store.setItemNum(rs.getInt("itemnum"));
                    store.setItemName(rs.getString("itemname"));
                    store.setItemDesc(rs.getString("itemdesc"));
                    store.setBin(rs.getString("bin"));
                    store.setCount(rs.getInt("count"));
                    store.setCost(rs.getInt("cost"));
                    l.add(store);
                }
          
        });
        return myMapper;
    }
    */
}
