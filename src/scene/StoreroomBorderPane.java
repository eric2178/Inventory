/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.Controller;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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
import miss_class_objects.Storeroom;
import miss_class_objects.Users;

/**
 *
 * @author Tanisha
 */
public class StoreroomBorderPane extends BorderPane
{

    private Menubar menubar;         // Menubar for view.
    private Controller controller;   // Controller to access models.
    private Users user;
    private Text title;              // 
    private TableView<Storeroom> storeRoomsTable;
    private String[] userPrompt;
    private TextField[] userInputTextFields;
    private ComboBox statusCombox;
    private Button[] buttons;
    private String[] buttonNames;
    private String[] buttonTips;

    public StoreroomBorderPane(Menubar menubar)
    {
        initializeInstanceVariables(menubar);
        this.setTop(setupNorthPanel(menubar,
            title));
        this.setCenter(setupCenterPanel(storeRoomsTable,
            userPrompt,
            userInputTextFields));
    }

    private void initializeInstanceVariables(Menubar menubar)
    {
        this.menubar = menubar;
        this.controller = this.menubar.getControl();
        this.user = this.menubar.getUser();
        this.title = new Text("Storerooms");
        this.storeRoomsTable = new TableView<Storeroom>();
        this.userPrompt = new String[]
        {
            "Storeroom Name:",
            "Created By:"
        };
        this.userInputTextFields = new TextField[userPrompt.length];
        this.buttonNames = new String[]
        {
            "Insert",
            "Search",
            "Update"
        };
        this.buttonTips = new String[]
        {
            "Add a new storeroom.",
            "Search for a storeroom.",
            "Update storeroom\ninformation."
        };
        this.buttons = new Button[buttonNames.length];
        statusCombox = new ComboBox();
    }

    private VBox setupNorthPanel(Menubar menubar,Text title)
    {
        // The VBox that holds the components.
        VBox mainVbox = new VBox();

        // Holds the title text.
        VBox titleHolderVbox = new VBox();
        titleHolderVbox.setPadding(new Insets(20,
            25,
            0,
            25));

        // Add the title to titleHolderVbox
        titleHolderVbox.getChildren().
            add(title);

        // Add menubar and and titleHolderVbox to main VBox.
        mainVbox.getChildren().
            addAll(menubar,
                titleHolderVbox);
        return mainVbox;
    }

    private HBox setupCenterPanel(TableView table,String[] myArray,TextField[] textfields)
    {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(25,25,25,25));

        hbox.getChildren().
            addAll(setupStoreroomTable(table),
                setupInfoFields(myArray,
                    textfields));

        return hbox;
    }

    private HBox setupStoreroomTable(TableView table)
    {
        table.setEditable(true);

        // Column header.
        TableColumn<Storeroom, String> storeroomnameCol = new TableColumn<Storeroom, String>("Storeroom");

        TableColumn<Storeroom, String> creator = new TableColumn<Storeroom, String>("Created By");
        TableColumn<Storeroom, String> statusCol = new TableColumn<Storeroom, String>("Status");

        setupTableColumns(storeroomnameCol, "storeroomName", true, false);
        setupTableColumns(creator, "createdby", true, false);
        setupTableColumns(statusCol, "status", true, true);

        // Tell the cell what to do when it has been edited.
        statusCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Storeroom, String> t) ->
            {
                //Let user make the change.
                String value = t.getNewValue().
                trim().
                toUpperCase();
                if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase(
                    "N"))
                {

                    ((Storeroom) t.getTableView().
                    getItems().
                    get(
                        t.getTablePosition().
                        getRow())).setStatus(value.trim().
                        toUpperCase());

                }

            });

        // Add the columns to the table.
        table.getColumns().
            setAll(storeroomnameCol, creator, statusCol);
        HBox hbox = new HBox();
        // Add the table to HBox.
        hbox.getChildren().
            addAll(table);
        return hbox;
    }

    private void setupTableColumns(TableColumn<Storeroom, String> tableColumn,
        String propertyName, boolean sortable, boolean editable)
    {
        // Set the cell name property that the column will bind to.
        tableColumn.
            setCellValueFactory(new PropertyValueFactory<Storeroom, String>(propertyName));
        // Make the column sortable.
        tableColumn.setSortable(sortable);

        // Make the column editable.
        tableColumn.setEditable(editable);

        // set the cell factory.
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private GridPane setupInfoFields(String[] myArray,
        TextField[] textfields)
    {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,
            25,
            25,
            25));

        // Setup the combobox.
        statusComboBoxSetup();

        for (int i = 0;
            i < myArray.length;
            i++)
        {
            // Create TextFields and prompts.
            textfields[i] = new TextField();
            textfields[i].setPromptText("Enter " + myArray[i].
                substring(0,
                    myArray[i].length() - 1));

            // Add elements to the grid.
            grid.add(new Label(myArray[i]),
                0,
                i);
            grid.add(textfields[i],
                1,
                i);
        }
        textfields[1].setText(this.user.getEmployeeId());
        textfields[1].setEditable(false);
        grid.add(new Label("Status:"),
            0,
            4);
        grid.add(statusCombox,
            1,
            4);

        grid.
            add(setupHBoxButtonPanel(buttons,
                    buttonNames,
                    buttonTips),
                1,
                6,
                1,
                2);

        return grid;
    }

    private void statusComboBoxSetup()
    {
        statusCombox = new ComboBox();
        statusCombox.getItems().
            addAll("Y/N",
                "Y",
                "N");
        statusCombox.getSelectionModel().
            select("Y/N");

    }

    private HBox setupHBoxButtonPanel(Button[] buttons,
        String[] buttonNames,
        String[] buttonTips)
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25,
            25,
            25,
            25));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        setUpButtons(buttons,
            buttonNames,
            buttonTips);
        hbox.getChildren().
            addAll(buttons);
        return hbox;
    }

    private void setUpButtons(Button[] buttons,
        String[] buttonNames,
        String[] tooltips)
    {
        for (int i = 0;
            i < buttonNames.length;
            i++)
        {
            buttons[i] = new Button(buttonNames[i]);
            buttons[i].setOnAction(e -> buttonClicked(e));
            buttons[i].setTooltip(new Tooltip(tooltips[i]));
        }
    }

    private void buttonClicked(ActionEvent e)
    {
        String buttonText = ((Button) e.getSource()).getText();
        
        switch (buttonText)
        {
            case "Insert":
                addStoreroom();
                break;

            case "Search":
                searchStoreroom();
                break;

            case "Update":
                update();
                break;
            default:
                System.out.println("This choice does not exist.");
                break;
        }
    }

    private void addStoreroom()
    {
        // Storeroom entered by user.
        String storeRoomName = userInputTextFields[0].getText();
        // employeeId created from system at login. 
        String employeeId = userInputTextFields[1].getText();

        if (isValidData(userInputTextFields[0], 20))
        {
            MessageBoxWindow message = new MessageBoxWindow();
            int makeStoreroom = message.
                message(title.getText(),
                    "Create Storeroom " + storeRoomName.toUpperCase() + ".");

            // If user clicks yes.
            if (makeStoreroom == 0)
            {
                if (controller.getStoreroomModel().
                    create_Storeroom(storeRoomName,
                        employeeId))
                {
                    makeStoreroom = message.
                        message(title.getText(),
                            "Done.");
                    clearFields();
                }
                else
                {

                    makeStoreroom = message.
                        message(title.getText(),
                            "Duplicate values are not permitted.");
                    clearFields();
                }

            }
            else
            {
                // message boxes.
                makeStoreroom = message.
                    message(title.getText(),
                        "Insert aborted...");

            }
        }
    }

    private void searchStoreroom()
    {

        // Clear table if it has items
        if (this.storeRoomsTable.getItems().
            size() > 0)
        {
            this.storeRoomsTable.getItems().
                clear();
        }

        // used to search the data base if no information was entered all employees
        // will be listed. 
        String active = (String) statusCombox.getValue();
        if (active.equals("Y/N"))
        {
            active = "_";
        }

        controller.getStoreroomModel().
            searchStorerooms(userInputTextFields[0].getText(),
                active);

        // Gets the observable list of the table and adds the observable list
        // from the controller to it.
        storeRoomsTable.getItems().
            addAll(this.controller.getStoreroomModel().
                getStoreroomList());

        // Display data in table. Table displays its list.
        storeRoomsTable.setItems(storeRoomsTable.getItems());
        storeRoomsTable.getSelectionModel().
            select(0,
                null);
        clearFields();
    }

    private void update()
    {
        if (storeRoomsTable.getItems().size() > 0)
        {
            Storeroom storeroom = storeRoomsTable.getSelectionModel().
                getSelectedItem();

            String data = String.
                format("%s   %s   %s", storeroom.getStoreroomName(), storeroom.
                    getCreatedby(), storeroom.getStatus());
            System.out.println(data);
            MessageBoxWindow msg = new MessageBoxWindow();
            int updateUser = msg.
                message(title.getText(), "Update user " + data);

            if (updateUser == 0)
            {
                Date date = new Date();
                if (this.controller.getStoreroomModel().
                    updateStoreroom(storeroom.getStoreroomName(), storeroom.
                        getCreatedby(), storeroom.getStatus(), date))
                {
                    msg.
                        message(title.getText(), data + "\nUser updated in System.");
                }
                else
                {
                    msg.message(title.getText(), "User not updated in System.");
                }

            }
            else
            {
                msg.message(title.getText(), "User not updated in System.");
            }
            clearFields();
        }

    }

    private void clearFields()
    {
        for (int i = 0;
            i < userInputTextFields.length;
            i++)
        {
            userInputTextFields[i].setText("");
            userInputTextFields[i].setStyle(null);
        }
        userInputTextFields[1].setText(this.menubar.getUser().
            getEmployeeId());
        userInputTextFields[1].setEditable(false);
    }

    private boolean isValidData(TextField field, int number)
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
}
