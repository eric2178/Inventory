package models;

import data_base_operations.DatabaseOperations;
import interfaces.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import miss_class_objects.Employee;

/**
 * The EmployeeModel contains all of the data need to manipulate the employee
 * information in the database.
 *
 * @author Eric Moore
 */
public class EmployeeModel
{

    private ObservableList<Employee> employeeList;

    public EmployeeModel()
    {
        employeeList = FXCollections.observableArrayList();
    }

    /**
     * The create_Employee_In_DB method is used to add an Employee to the
     * ArrayList
     *
     * @param firstname must be <= 11 characters @param l astName must be <= 15
     * characters @param c reator must be <= 11 characters @return boolean
     * @param lastName
     * @param creator
     * @param userid
     * @return 
     */
    public boolean createEmployeeInDB(String firstname, String lastName, String creator,String userid)
    {
        boolean canMakeEmployee = false;

        String empFirstname = lettersAndDigits(firstname.trim());
        String empLastname = lettersAndDigits(lastName.trim());
        String createdBy = lettersAndDigits(creator.toUpperCase().trim());
        String userId = lettersAndDigits(userid.toUpperCase().trim());
        
        if (firstname.length() <= 11 && lastName.length() <= 15 && creator.
            length() <= 11 && userId.length() <= 10)
        {

            if (!(empFirstname.isEmpty()) && !(empLastname.
                isEmpty()) && !(createdBy.isEmpty()) && !DatabaseOperations.exists("employees", "employeeid", userId))
            {
                
                //String firstname, String lastname, Date date, String employed,String userid,String created
                Employee emp = new Employee(empFirstname,empLastname,new Date(),"Y",userid,createdBy);

                // Function to insert data in data base. Takes an Empoyee object a makes it
                // into a String. The other way threw an exception.
                Function<Employee, String> employeeString = e -> e.getUserid() + "," + 
                                            e.getFirstname() + "," + e.getLastname() + "," + 
                                            e.getCreatedby() + "," + e.getCreationDate() + "," + 
                                            e.getStatus();

                // Create a formatted string to insert into database.
                String insertIntoDB = DatabaseOperations.
                    insertIntoDatabase("employees", employeeString.apply(emp), ",");
                DatabaseOperations.
                    createUpdateDeleteInsert(insertIntoDB, "miss");

                // Adds an Employee to the employeeList ArrayList
                this.employeeList.
                    add(emp);
                canMakeEmployee = true;
            }
        }
        return canMakeEmployee;
    }

    /**
     * The upDateEmployeeInDatabase method is used to make user inactive. Users
     * are not deleted for auditing purposes.
     *
     * @param firstname
     * @param lastname
     * @param userID
     * @param createdBy
     * @param empId
     * @param active must be a capital Y or a capital N
     * @return
     */
    public boolean upDateEmployeeInDatabase(String firstname, String lastname,String createdBy, String empId, String active)
    {
        boolean canUpdateEmployee = false;
        String fname = lettersAndDigits(firstname.trim().toUpperCase());
        String lname = lettersAndDigits(lastname.trim().toUpperCase());
        String creator = lettersAndDigits(createdBy.trim().toUpperCase());
        String employeeID = lettersAndDigits(empId.trim().toUpperCase());
        String status = lettersAndDigits(active.trim().toUpperCase());

        // Check to see if userID, createdBy, and empId are entered.
        if (!(creator.trim().isEmpty()) && !(employeeID.trim().isEmpty()) && (status.equals("Y") || (status.equals("N"))) &&
            !(fname.trim().isEmpty()) && !(lname.trim().isEmpty()))
        {    
         DateFormat df2 = new SimpleDateFormat("dd/MMM/yyyy");  
            
         String updateEmployee = "UPDATE Employees SET FIRSTNAME = '"
                + fname
                + "' ,LASTNAME = '"
                + lname
                + "', ACTIVE = '"
                + status +"'"
                + ",DATE = '"
                + df2.format(new Date())
                + "' Where EMPLOYEEID = '"
                + employeeID
                + "' AND CREATEDBY = '" + creator +"'";
            
            DatabaseOperations.
                createUpdateDeleteInsert(updateEmployee, "miss");
            
            canUpdateEmployee = true;
        }
        else
        {
            System.out.println("problem in update");
        }

        return canUpdateEmployee;
    }

    /**
     * The addDatabaseEmployeesToList add the Employees from the database to the
     * employeeList
     */
    public void addDatabaseEmployeesToList()
    {
        ObjectMapper<Employee> employeeMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                // Create employee object
                Employee employee = new Employee(); 
                
                employee.setUserid(rs.getString("employeeid"));
                employee.setFirstname(rs.getString("firstname"));
                employee.setLastname(rs.getString("lastname"));
                employee.setCreatedby(rs.getString("createdby"));
                employee.setStatus(rs.getString("active"));

                // Add object to the list.
                l.add(employee);
            }
        });

        DatabaseOperations.
            getResultSetIntoAList("SELECT * FROM EMPLOYEES", employeeList, employeeMapper);

    }
    //Update statement.
// update Employees set FIRSTNAME='RICK',LASTNAME='JAMES',CREATEDBY='E1',"DATE"=Date Where EMPLOYEEID='E15'
    public void searchEmployees(String employeeid, String firstname, String lastname, String active)
    {
        
        // Search String used to get data from database.
        String searchQuery = "SELECT * FROM EMPLOYEES WHERE employeeid like '%"
            + employeeid.trim().toUpperCase()
            + "%' AND firstname like '%"
            + firstname.trim().toUpperCase()
            + "%' AND lastname LIKE '%"
            + lastname.trim().toUpperCase()
            + "%' AND active LIKE '%"
            + active.trim().toUpperCase()
            + "%'";

        ObjectMapper<Employee> employeeMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                // Create employee object
                Employee employee = new Employee();
                employee.setUserid(rs.getString("employeeid"));
                employee.setFirstname(rs.getString("firstname"));
                employee.setLastname(rs.getString("lastname"));
                employee.setCreatedby(rs.getString("createdby"));
                employee.setStatus(rs.getString("active"));

                // Add object to the list.
                l.add(employee);
            }
        });

        DatabaseOperations.
            getResultSetIntoAList(searchQuery, employeeList, employeeMapper);
    }

    /*
     The lettersAndDigits makes sure there are
     only letters and digits in a string. All letters
     will be capitalized. Everything else is removed
     from the Streing
     */
    private String lettersAndDigits(String info)
    {

        String dataToReturn = "";
        for (int i = 0; i < info.length(); i++)
        {
            if (Character.isLetterOrDigit(info.charAt(i)))
            {
                dataToReturn += info.charAt(i);
            }
        }

        return dataToReturn.trim().toUpperCase();
    }

    /**
     * The getEmployeeList method gets the list of Employees
     *
     * @return
     */
    public ObservableList<Employee> getEmployeeList()
    {
        return employeeList;
    }

    public void clearEmployeeList()
    {
        // Clear ArrayList if it has items.
        if (employeeList.size() > 0)
        {
            employeeList.clear();
        }
    }

}
