/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controllers.Controller;
import data_base_operations.DatabaseOperations;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import messagepane.MessageBoxWindow;
import scene.Menubar;
import scene.Menubar;

/**
 *
 * @author Tanisha
 */
public class EmployeeHBorderPane extends BorderPane
{

    private Menubar menuBar;
//    private ListView<String> employeeListView;
    private String[] labelNames;
    private Label[] labels;
    private TextField[] textFields;
    private Button updateButton;
    private Button insertButton;
    private Controller controller;
    private RadioButton searchRadioButton;
    private RadioButton updateRadioButton;
    private Text titleText;
    private int idNumber;
    private final String update;
    private final String search;
    private final String insert;
    private ComboBox<String> activeCombo;
    private TableView employeeTable;

    public EmployeeHBorderPane(Menubar mb, Controller controller)
    {
        this.update = "Update Status";
        this.search = "Search";
        this.insert = "Add Employee";

        this.controller = controller;
        idNumber = (DatabaseOperations.
            count("miss", "select count(*) from employees") + 1);
        this.menuBar = mb;
        setEmployeeTable();
        activeComboBoxSetup();
        HBox holder = new HBox();
        BorderPane bp = new BorderPane();
        holder.getChildren().addAll(setupListPanel(), setupUserInputPanel());
        this.setTop(setTopPanel());
        this.setCenter(holder);
    }

    /*
     Contains the listbox
     */
    private HBox setupListPanel()
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25, 25, 25, 25));

        // Clear the controller list.
        this.controller.getEmployeeModel().clearEmployeeList();

        // Add employees to controller list.
//        this.controller.getEmployeeModel().addDatabaseEmployeesToList();
//        // Create a FXCollections.observableArrayList() String type
//        ObservableList<String> employeeList = FXCollections.
//            observableArrayList();
//
//        // Add controller list to
//        for (Employee employee : this.controller.getEmployeeModel().
//            getEmployeeList())
//        {
//            // Convert employee to string with format.
//            Function<Employee, String> emp = e -> String.
//                format("%-10s%-10s%-15s%-5s", e.getUserid(), e.getFirstname(), e.
//                    getLastname(), e.getEmployed());
//            employeeList.add(emp.apply(employee));
//        }
//        employeeListView = new ListView<String>();
//
//        employeeListView.getSelectionModel().selectedItemProperty().
//            addListener(e ->
//                {
//                    String data = employeeListView.getSelectionModel().
//                    getSelectedItem();
//
//            });
//
//        employeeListView.setPrefHeight(40);
//        hbox.getChildren().addAll(employeeListView);
//        return hbox;
        return null;
    }

    private HBox setEmployeeTable()
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25, 25, 25, 25));
        this.controller.getEmployeeModel().clearEmployeeList();
        return null;
    }

    private void activeComboBoxSetup()
    {
        activeCombo = new ComboBox();
        activeCombo.getItems().addAll("Y/N", "Y", "N");
        activeCombo.getSelectionModel().select("Y/N");
        
    }
    /*
     Contains the Title text.
     */

    private VBox setTopPanel()
    {
        VBox mainVbox = new VBox();
        VBox titleHolderVbox = new VBox();
        titleHolderVbox.setPadding(new Insets(20, 25, 0, 25));
        titleText = new Text("Employees");
        titleHolderVbox.getChildren().add(titleText);
        mainVbox.getChildren().addAll(menuBar, titleHolderVbox, radioButtons());
        return mainVbox;
    }

    private HBox radioButtons()
    {
        ToggleGroup group = new ToggleGroup();

        searchRadioButton = new RadioButton("Search");
        searchRadioButton.setSelected(true);

        updateRadioButton = new RadioButton("Insert\\Update");;

        searchRadioButton.setToggleGroup(group);
        updateRadioButton.setToggleGroup(group);

        searchRadioButton.setOnAction(e ->
        {
            textFields[0].setEditable(true);
            textFields[3].setEditable(true);
            clearFields();
            // Remove color
            for (TextField fields : textFields)
            {
                fields.setStyle(null);

            }
            // Change the name of the Update Employee button to Search.Update employee\nstatus
            updateButton.setText(search);
            updateButton.
                setTooltip(new Tooltip("Search database\nfor employee Records."));
            // Make the Add Employee invisble.
            insertButton.setVisible(false);

        });

        updateRadioButton.setOnAction(e ->
        {
            // Change the name of the Update Employee button to Search.Update employee\nstatus
            updateButton.setText(update);
            updateButton.
                setTooltip(new Tooltip("Update employee status."));
            // Make the Add Employee invisble.
            insertButton.setVisible(true);
            insertButton.setText(insert);
            insertButton.
                setTooltip(new Tooltip("Add new employees\nto database."));

            textFields[0].setEditable(false);
            textFields[3].setEditable(false);
            textFields[0].setText("E" + idNumber);
            //remove color
            for (TextField fields : textFields)
            {
                fields.setStyle(null);

            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(searchRadioButton, updateRadioButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        return hbox;
    }
    /*
     Contains the labels and textfields.
     */

    private GridPane setupUserInputPanel()
    {
        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        setupLabelsAndTextFields(grid);
        return grid;
    }

    /*
     Create labels and textfields.
     */
    private void setupLabelsAndTextFields(GridPane grid)
    {
        labelNames = new String[]
        {
            "ID:", "First Name:", "Last Name:", "Created By:"
        };
        labels = new Label[labelNames.length];
        textFields = new TextField[labelNames.length];

        for (int index = 0; index < labelNames.length; index++)
        {
            labels[index] = new Label(labelNames[index]);
            grid.add(labels[index], 0, index);

            textFields[index] = new TextField();
            grid.add(textFields[index], 1, index);
        }
        grid.add(new Label("Active:"), 0, 4);
        grid.add(activeCombo, 1, 4);
        grid.add(buttonsHBox(), 0, 6, 2, 1);
    }

    private HBox buttonsHBox()
    {
        updateButton = new Button("Search");
        updateButton.
            setTooltip(new Tooltip("Search database\nfor employee Records."));
//        updateButton.setMnemonicParsing(true);
        updateButton.setOnAction(e -> buttonClicked(e));
        insertButton = new Button("Add Employee");
        insertButton.setOnAction(e -> buttonClicked(e));
        insertButton.setVisible(false);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(insertButton, updateButton);
        return hbox;
    }

    private void actionListeners()
    {

    }

    private void buttonClicked(ActionEvent e)
    {

        String s = ((Button) e.getSource()).getText();
        System.out.println(s);
        if (s.equals(insert))
        {
            System.out.println(textFields[1].getText());
//            addEmployee();
        }
        else if (s.equals(update))
        {

        }
        else
        {
            search();
        }
    }

    /**
     * The Add employee method is used to add an employee to the database.
     */
    public void addEmployee()
    {
        if (updateRadioButton.isSelected())
        {
            // Get userid
            String userid = textFields[0].getText();
            // Get first name.
            String firstname = textFields[1].getText();
            // Get lastname.
            String lastname = textFields[2].getText();
            // Get createby.
            String createdBy = textFields[4].getText();

            // Check textboxes.
            checkTextBox(textFields[0], 10);
            checkTextBox(textFields[1], 11);
            checkTextBox(textFields[2], 15);
            checkTextBox(textFields[4], 11);

            // Insert new employee.
            // Add employee to database.
            if (!(firstname.isEmpty()) && !(lastname.isEmpty()) && !(createdBy.
                isEmpty()) && !(userid.isEmpty()))
            {
                MessageBoxWindow msg = new MessageBoxWindow();
                int addUser = msg.
                    message(titleText.getText(), "Add Employee " + firstname + " " + lastname + ".");
                System.out.println(addUser);
                if (addUser == 0)
                {
                    if (controller.getEmployeeModel().
                        createEmployeeInDB(firstname, lastname, createdBy, userid))
                    {
//                        JOptionPane.
//                            showMessageDialog(this, "Employee Added", titleText, JOptionPane.INFORMATION_MESSAGE);
//
//                        String newEmp = "E" + (defaultListModel.size() + 1);
//                        String newUserToAdd = String.
//                            format("%s,%s,%S,Y", newEmp, firstname, lastname);
//                        // Add to JList via List Model.
//                        defaultListModel.addElement(newUserToAdd);
                    }
                    else
                    {
//                        JOptionPane.
//                            showMessageDialog(this, "Employee not Added", title, JOptionPane.INFORMATION_MESSAGE);

                    }
                }
                else
                {
//                    JOptionPane.
//                        showMessageDialog(this, "Employee not Added", title, JOptionPane.INFORMATION_MESSAGE);
                }
                ++idNumber;
                textFields[0].setText("E" + idNumber);
            }

        }

    }

    public void search()
    {
//        this.employeeListView.getItems().add(textFields[1].getText());
//        if (searchRadioButton.isSelected())
//        {
//            this.controller.getEmployeeModel().clearEmployeeList();
//
//            if (this.employeeListView.getItems().size() > 0)
//            {
//                this.employeeListView.getItems().clear();
//            }
// 
//            // used to search the data base if no information was entered all employees
//            // will be listed. 
////            System.out.println(textFields[1].getText());
//            String active = activeCombo.getValue();
//            if(active.equals("Y/N")){active = "_";}
//            this.controller.getEmployeeModel().
//                searchEmployees(textFields[0].
//                    getText(), textFields[1].getText(), textFields[2].
//                    getText(), active);
//
//            // Get the list of employees from the controller.
//            ObservableList<Employee> employeeList = this.controller.
//                getEmployeeModel().getEmployeeList();
//
//            for (Employee e : employeeList)
//            {
//                System.out.println(e.
//                    getUserid() + " " + e.getFirstname() + " " + e.getLastname() + " " + e.
//                    getEmployed());
//                this.employeeListView.getItems().add(e.
//                    getUserid() + " " + e.getFirstname() + " " + e.getLastname() + " " + e.
//                    getEmployed());
//            }
//        }
    }

    /**
     * The checkTextBox method checks to see if a textbox has data in it. If not
     * the box is turned pink.
     *
     * @param field
     */
    private void checkTextBox(TextField field, int number)
    {
        if (field.getText().isEmpty() || field.getText().length() > number)
        {
            field.setStyle("-fx-background-color: #ff1493");
            field.setText("");
        }
        else
        {
            field.setStyle(null);
        }
    }

    private void clearFields()
    {
        for (TextField text : textFields)
        {
            text.setText("");
        }
    }
}
