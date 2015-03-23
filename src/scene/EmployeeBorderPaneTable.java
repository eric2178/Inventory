package scene;

import controllers.Controller;
import data_base_operations.DatabaseOperations;
import java.util.Calendar;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import messagepane.MessageBoxWindow;
import miss_class_objects.Employee;

/**
 *
 * @author Tanisha
 */
public class EmployeeBorderPaneTable extends BorderPane
{

    private Menubar menubar;
    private Controller controller;
    private Text title;
    private TableView<Employee> employeeTable;
    private String[] userPrompt;
    private TextField[] infoTextFields;
    private ComboBox activeCombo;
    private Button[] buttons;
    private String[] buttonNames;
    private String[] buttonTips;
    private int idNumber;

    public EmployeeBorderPaneTable(Menubar menubar)
    {
        // Initialize instance fields.
        intializeInstanceFields(menubar);

        // Places items in the north region of the BorderPane.
        this.setTop(setupNorthPanel(menubar, title));

        // Places items in the center region of the BorderPane.
        this.
            setCenter(setupCenterPanel(employeeTable, userPrompt, infoTextFields));
    }

    private void intializeInstanceFields(Menubar menubar)
    {
        this.menubar = menubar;
        this.controller = this.menubar.getControl();
        this.title = new Text("Employees");
        this.employeeTable = new TableView<Employee>();
        setupEmployeeTable(employeeTable);
        this.userPrompt = new String[]
        {
            "User ID:", "First Name:", "Last Name:", "Created By:"
        };
        this.infoTextFields = new TextField[userPrompt.length];
        this.buttonNames = new String[]
        {
            "Insert", "Search", "Update"
        };
        this.buttonTips = new String[]
        {
            "Add a new employee.", "Search for employee.", "Update employee\ninformation."
        };
        this.buttons = new Button[buttonNames.length];
    }

    private VBox setupNorthPanel(Menubar menubar, Text title)
    {
        // The VBox that holds the components.
        VBox mainVbox = new VBox();

        // Holds the title text.
        VBox titleHolderVbox = new VBox();
        titleHolderVbox.setPadding(new Insets(20, 25, 0, 25));

        // Add the title to titleHolderVbox
        titleHolderVbox.getChildren().add(title);

        // Add menubar and and titleHolderVbox to main VBox.
        mainVbox.getChildren().addAll(menubar, titleHolderVbox);
        return mainVbox;
    }

    private HBox setupCenterPanel(TableView table, String[] myArray, TextField[] textfields)
    {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(25, 25, 25, 25));

        hbox.getChildren().
            addAll(setupEmployeeTable(table), setupInfoFields(myArray, textfields));

        return hbox;
    }

    private HBox setupEmployeeTable(TableView table)
    {
//        String selectedStatus = "Y";

        table.setEditable(true);

        // Set each column to a getter in the Employee class.
        TableColumn<Employee, String> idCol = new TableColumn<Employee, String>("ID");
        TableColumn<Employee, String> firstNameCol = new TableColumn<Employee, String>("First Name");
        TableColumn<Employee, String> lastNameCol = new TableColumn<Employee, String>("Last Name");

        // Status choice
        TableColumn<Employee, String> statusCol = new TableColumn<Employee, String>("Status");
//        statusCol.setEditable(true);

        // Sets up the table columns for editing.
        setupTableColumns(idCol, "userid", true, false);
        setupTableColumns(firstNameCol, "firstname", true, false);
        setupTableColumns(lastNameCol, "lastname", true, false);
        setupTableColumns(statusCol, "status", true, true);

        // Tell the cell what to do when it has been edited.
        statusCol.setOnEditCommit((CellEditEvent<Employee, String> t) ->
        {
            //Let user make the change.
            String value = t.getNewValue().trim().toUpperCase();
            if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N"))
            {

                ((Employee) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setStatus(value.trim().
                        toUpperCase());

            }

        });

        // Add the columns to the table.
        table.getColumns().
            setAll(idCol, firstNameCol, lastNameCol, statusCol);
        HBox hbox = new HBox();
        // Add the table to HBox.
        hbox.getChildren().addAll(table);
        return hbox;
    }

    private void setupTableColumns(TableColumn<Employee, String> tableColumn, String propertyName, boolean sortable, boolean editable)
    {
        // Set the cell name property that the column will bind to.
        tableColumn.
            setCellValueFactory(new PropertyValueFactory<Employee, String>(propertyName));
        // Make the column sortable.
        tableColumn.setSortable(sortable);

        // Make the column editable.
        tableColumn.setEditable(editable);

        // set the cell factory.
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private GridPane setupInfoFields(String[] myArray, TextField[] textfields)
    {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Setup the combobox.
        activeComboBoxSetup();

        for (int i = 0; i < myArray.length; i++)
        {
            // Create TextFields and prompts.
            textfields[i] = new TextField();
            textfields[i].setPromptText("Enter " + myArray[i].
                substring(0, myArray[i].length() - 1));

            // Add elements to the grid.
            grid.add(new Label(myArray[i]), 0, i);
            grid.add(textfields[i], 1, i);
        }

        textfields[3].setText(this.menubar.getUser().getEmployeeId());
        textfields[3].setEditable(false);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(activeCombo, 1, 4);

        grid.
            add(setupHBoxButtonPanel(buttons, buttonNames, buttonTips), 1, 6, 1, 2);

        return grid;
    }

    private void setUpButtons(Button[] buttons, String[] buttonNames, String[] tooltips)
    {
        for (int i = 0; i < buttonNames.length; i++)
        {
            buttons[i] = new Button(buttonNames[i]);
            buttons[i].setOnAction(e -> buttonClicked(e));
            buttons[i].setTooltip(new Tooltip(tooltips[i]));
        }
    }

    private HBox setupHBoxButtonPanel(Button[] buttons, String[] buttonNames, String[] tooltips)
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25, 25, 25, 25));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        setUpButtons(buttons, buttonNames, tooltips);
        hbox.getChildren().addAll(buttons);
        return hbox;
    }

    private void activeComboBoxSetup()
    {
        activeCombo = new ComboBox();
        activeCombo.getItems().addAll("Y/N", "Y", "N");
        activeCombo.getSelectionModel().select("Y/N");

    }

    private void buttonClicked(ActionEvent e)
    {
        String s = ((Button) e.getSource()).getText();

        switch (s)
        {
            case "Insert":
                addEmployees();
                break;

            case "Search":
                searchEmployees();
                break;

            case "Update":
                UpdateEmployees();
                break;
            default:
                System.out.println("This choice does not exist.");
                break;
        }
    }

    /**
     * The Search method allows for db searches.
     */
    public void searchEmployees()
    {

        // Clear the controller list
        this.controller.getEmployeeModel().clearEmployeeList();

        // Clear table if it has items
        if (this.employeeTable.getItems().size() > 0)
        {
            this.employeeTable.getItems().clear();
        }

        // used to search the data base if no information was entered all employees
        // will be listed. 
        String active = (String) activeCombo.getValue();
        if (active.equals("Y/N"))
        {
            active = "_";
        }

        // Search database.
        this.controller.getEmployeeModel().
            searchEmployees(infoTextFields[0].getText(), infoTextFields[1].
                getText(), infoTextFields[2].getText(), active);

        // Gets the observable list of the table and adds the observable list
        // from the controller to it.
        employeeTable.getItems().addAll(this.controller.
            getEmployeeModel().getEmployeeList());

        // Display data in table. Table displays its list.
        employeeTable.setItems(employeeTable.getItems());
        employeeTable.getSelectionModel().select(0, null);
        clearFields();
    }

    /**
     * The Add employee method is used to add an employee to the database.
     */
    public void addEmployees()
    {

        // Get number of employees in db plus 1.
        idNumber = (DatabaseOperations.
            count("miss", "select count(*) from employees") + 1);

        // Get userid
        String userid = "E" + idNumber;
        // Get first name.
        String firstname = infoTextFields[1].getText();
        // Get lastname.
        String lastname = infoTextFields[2].getText();
        // Get createby.
        String createdBy = infoTextFields[3].getText();
        checkTextBox(infoTextFields[1], 11);
        checkTextBox(infoTextFields[2], 15);
        
        // Add employee to database.
        if (checkTextBox(infoTextFields[1], 11)
            && checkTextBox(infoTextFields[2], 15)
            && checkTextBox(infoTextFields[3], 11)
            && !(userid.isEmpty()))
        {
            MessageBoxWindow msg = new MessageBoxWindow();
            int addUser = msg.
                message(title.getText(), "Add Employee " + userid + " " + firstname + " " + lastname + ".");
            System.out.println(addUser);
            if (addUser == 0)
            {
                if (controller.getEmployeeModel().
                    createEmployeeInDB(firstname, lastname, createdBy, userid))
                {

                    clearFields();
                    //String firstname, String lastname, Date date, String employed,String userid,String created
                    Employee e = new Employee(firstname, lastname, new Date(), "Y", userid, createdBy);
                    employeeTable.getItems().add(e);
                    employeeTable.setItems(employeeTable.getItems());
                    ++idNumber;
                }
                else
                {
//                        JOptionPane.
//                            showMessageDialog(this, "Employee not Added", title, JOptionPane.INFORMATION_MESSAGE);

                }
                clearFields();

            }
            else
            {
//                    JOptionPane.
//                        showMessageDialog(this, "Employee not Added", title, JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    public void UpdateEmployees()
    {
        if (employeeTable.getItems().size() > 0)
        {
            Employee emp = employeeTable.getSelectionModel().getSelectedItem();

//            use to make sure all is well check yes/no
//            if (DatabaseOperations.lettersOnly(value))
            //String firstname, String lastname,String createdBy, String empId, String active
            String data = String.
                format("%s   %s   %s   %s   %s", emp.getFirstname(), emp.
                    getLastname(), emp.getCreatedby(), emp.getUserid(), emp.
                    getStatus());
            System.out.println(data);
            MessageBoxWindow msg = new MessageBoxWindow();
            int updateUser = msg.
                message(title.getText(), "Update user " + emp.getUserid() + " " + emp.
                    getFirstname() + " " + emp.getLastname() + ".");

            if (updateUser == 0)
            {
                if (this.controller.getEmployeeModel().
                    upDateEmployeeInDatabase(emp.
                        getFirstname(), emp.getLastname(), this.infoTextFields[3].
                        getText(), emp.getUserid(), emp.getStatus()))
                {
                    msg.message(title.getText(), "User updated in System.");
                }
                else
                {

                }

            }
            else
            {
                msg.message(title.getText(), "User not updated in System.");
            }
            clearFields();
        }

    }

    /**
     * The checkTextBox method checks to see if a textbox has data in it. If not
     * the box is turned pink.
     *
     * @param field
     */
    private boolean checkTextBox(TextField field, int number)
    {
        boolean isValid = false;
        if (field.getText().isEmpty() || field.getText().length() > number)
        {
            field.setStyle("-fx-background-color: #ff1493");
            field.setText("");
        }
        else
        {
            field.setStyle(null);
            isValid = true;
        }
        return isValid;
    }

    private void clearFields()
    {
        for (int i = 0; i < infoTextFields.length; i++)
        {
            infoTextFields[i].setText("");
            infoTextFields[i].setStyle(null);
        }
        infoTextFields[3].setText(this.menubar.getUser().getEmployeeId());
        infoTextFields[3].setEditable(false);
    }

}
