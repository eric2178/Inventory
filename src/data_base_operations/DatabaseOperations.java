package data_base_operations;

import interfaces.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tanisha
 */
public class DatabaseOperations
{

    private static Connection conn;

    /**
     * The getConnection opens a connection to the database. Connection must be
     * closed within the calling method.
     *
     * @param db_url
     * @throws SQLException
     */
    private static void getConnection(String db_url)
        throws SQLException
    {
        // Create connection to database
        conn = DriverManager.getConnection(db_url);
    }

    /**
     * The checkConnection method gets the connection to a database.
     *
     * @param db_url database name.
     */
    public static void checkConnection(String db_url)
    {
        if (!db_url.isEmpty())
        {
            try
            {
                // Create connection to database
                getConnection(db_url);

                System.out.println("Connection is open.");
                conn.close();
                System.out.println("Connection successful.");
                System.out.println("Connection closed.");
            }
            catch (SQLException e)
            {
                JOptionPane.
                    showMessageDialog(null,
                        "Problem opening the database.",
                        "Database",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.
                showMessageDialog(null,
                    "No data entered.",
                    "Database",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The createDatabase method creates a brand new database.
     *
     * @param db_url database name.
     */
    public static void createNewDatabase(String db_url)
    {
        if (!db_url.equals(""))
        {
            // Cretae a new database.
            checkConnection("jdbc:derby:" + db_url + ";create=true");
            JOptionPane.
                showMessageDialog(null,
                    "New database " + db_url + " has been created.",
                    "Database",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.
                showMessageDialog(null,
                    "No data entered.",
                    "Database",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The createUpdateDeleteInsert method create tables, update tables, delete,
     * and insert into tables.
     *
     * @param sql
     * @param databaseName
     */
    public static void createUpdateDeleteInsert(String sql,
        String databaseName)
    {
        try
        {
            if (!sql.isEmpty() || !databaseName.isEmpty())
            {
                getConnection("jdbc:derby:" + databaseName);
                System.out.println(sql);
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
            }
            else
            {
                JOptionPane.
                    showMessageDialog(null,
                        "No data entered.",
                        "Database",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseOperations.class.getName()).
                log(Level.SEVERE,
                    null,
                    ex);
        }
    }

    /**
     * The getResultSetIntoArrayList places the results from the database into
     * an ArrayList. The ArrayList is generic
     *
     * @param query
     * @param list
     * @param mapper
     */
    public static void getResultSetIntoAList(String query,
        ObservableList list,
        ObjectMapper mapper)
    {
        try
        {
            System.out.println(query);
            conn = DriverManager.getConnection("jdbc:derby:miss");
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            mapper.mapObjectsToList(list,
                result); // Iface

            conn.close();
        }
        catch (SQLException ex)
        {
            System.out.
                println("DatabaseOperations getResultSetIntoArrayList: DB may be in use.");
        }
    }

    ////////////////////////// Data base insert method and helper below.
    /**
     * The insertIntoDatabase formats a string so that it is database insert
     * ready. Uses helper method lettersOnly.
     *
     * @param tableName
     * @param stringToFormat
     * @param delim
     * @return
     */
    public static String insertIntoDatabase(String tableName,
        String stringToFormat,
        String delim)
    {
        String[] info = stringToFormat.split(delim);
        String strToReturn = "INSERT INTO " + tableName.trim() + " VALUES (";
        for (String str
            : info)
        {
            for (int i = 0;
                i < str.length();
                i++)
            {
                // check to see if there are letters in the string.
                if (lettersOnly(str))
                {
                    strToReturn += String.format("'%s',",
                        str.trim());
                    break;
                }
                else
                {
                    strToReturn += String.valueOf(str.trim()) + ",";
                    break;
                }

            }
        }

        return strToReturn.substring(0,
            strToReturn.length() - 1) + ")";
    }

    /**
     *
     * @param db_url
     * @param query
     * @return
     */
    public static int count(String db_url,
        String query)
    {
        int count = 0;
        if (!db_url.isEmpty())
        {

            try
            {
                // Create connection to database
                getConnection("jdbc:derby:" + db_url);
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(query);
                if (rs.next())
                {
                    count = rs.getInt(1);
                }
                conn.close();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(DatabaseOperations.class.getName()).
                    log(Level.SEVERE,
                        null,
                        ex);
            }

        }
        return count;
    }

    /**
     * The getUserString method is used to retrieve one user record from the
     * database
     *
     * @param db_url
     * @param query
     * @return
     */
    public static String getUserString(String query)
    {
        String stringToReturn = "";
        try
        {
            // Create connection to database
            getConnection("jdbc:derby:miss");
            Statement stmt = conn.createStatement();

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                String empID = rs.getString("employeeid");
                String newPassword = rs.getString("newpassword");
                String oldPassword = rs.getString("oldpassword");
                String permissions = rs.getString("permissions");

                stringToReturn = empID + "," + newPassword + "," + oldPassword + "," + permissions;
            }

            conn.close();
        }
        catch (SQLException ex)
        {
            stringToReturn = "no good in database";
            System.out.println(stringToReturn);
        }

        return stringToReturn;
    }

    /**
     * The exists method checks to see wether the value that is being entered by
     * the user is a duplicate value.
     *
     * @param tableName
     * @param columnname
     * @param userString
     * @return
     */
    public static boolean exists(String tableName,
        String columnname,
        String userString)
    {
        boolean exists = false;
        String compareToUserString = "";
        String userInfo = userString.trim().
            toUpperCase();
        try
        {
            // Create a where clause that search for a user string. in this case we want the primarykey.
            String sql = "SELECT * FROM " + tableName + " WHERE " + columnname + " = '" + userInfo.
                trim().
                toUpperCase() + "'";
            getConnection("jdbc:derby:miss");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next())
            {
                compareToUserString = rs.getString(columnname);
            }

            if (compareToUserString.equals(userInfo))
            {
                exists = true;
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseOperations.class.getName()).
                log(Level.SEVERE,
                    null,
                    ex);
        }

        return exists;
    }

    public static boolean exists(String tableName,
        String columnname1,
        String userString, String columnname2, String userString2, String columnname3, String userString3)
    {
        boolean exists = true;

        try
        {
            // Create a where clause that search for a user string. in this case we want the primarykey.
            String sql = "SELECT * FROM " + tableName + " WHERE " + columnname1 + " = '" + userString.
                trim().
                toUpperCase() + "' and " + columnname2 + "= '"
                + userString2.trim().toUpperCase()
                + "' and " + columnname3 + " = '"
                + userString3.trim().toUpperCase()
                + "'";
            System.out.println(sql);
            getConnection("jdbc:derby:miss");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next())
            {
                exists &= rs.getString(columnname1).equals(userString);
                exists &= rs.getString(columnname2).equals(userString2);
                exists &= rs.getString(columnname3).equals(userString3);
            }

        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseOperations.class.getName()).
                log(Level.SEVERE,
                    null,
                    ex);
        }
        System.out.println(exists);
        return exists;
    }

    /**
     * The lettersOnly is used to determine if a string is composed of letters
     * only. helper method of formatString method.
     *
     * @param data
     * @return
     */
    public static boolean lettersOnly(String data)
    {
        boolean isAlphaNumeric = false;
        for (int i = 0;
            i < data.length();
            i++)
        {
            if (!Character.isDigit(data.charAt(i)) && data.charAt(i) != '.')
            {
                isAlphaNumeric = true;
            }
        }
        return isAlphaNumeric;
    }

}
