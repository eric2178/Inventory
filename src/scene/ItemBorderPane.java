package scene;

import controllers.Controller;
import data_base_operations.DatabaseOperations;
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
import miss_class_objects.Item;
import miss_class_objects.Users;

/**
 *
 * @author Eric Moore
 */
public class ItemBorderPane extends BorderPane
{

    private Menubar menubar;         // Menubar for view.
    private Controller controller;   // Controller to access models.
    private Users user;
    private Text title;
    private TableView<Item> itemTable;
    private String[] userPrompt;
    private TextField[] userInputTextFields;
    private ComboBox typeCombox;
    private Button[] buttons;
    private String[] buttonNames;
    private String[] buttonTips;
    private int itemCount;

    public ItemBorderPane(Menubar menubar)
    {
        this.initializeInstanceFields(menubar);
        this.setTop(setupNorthPanel(menubar, title));
        this.setCenter(setupCenterPanel(itemTable,
            userPrompt,
            userInputTextFields));
    }

    private void initializeInstanceFields(Menubar menubar)
    {
        this.menubar = menubar;
        this.controller = this.menubar.getControl();
        this.user = this.menubar.getUser();
        this.title = new Text("Items");
        this.itemTable = new TableView<Item>();
        this.userPrompt = new String[]
        {
            "Item Number:", "Name:", "Description:"
        };
        this.userInputTextFields = new TextField[userPrompt.length];
//        this.typeCombox = new ComboBox();
        this.buttonNames = new String[]
        {
            "Insert", "Search", "Delete"
        };
        this.buttonTips = new String[]
        {
            "Add item to database.", "Search for items\nin the database.", "Removes item from database\nwhen item has no balance."
        };

        buttons = new Button[this.buttonNames.length];
    }

    private VBox setupNorthPanel(Menubar menubar, Text title)
    {
        // The VBox that holds the components.
        VBox mainVbox = new VBox();

        // Holds the title text.
        VBox titleHolderVbox = new VBox();
        titleHolderVbox.setPadding(new Insets(20, 25, 0, 25));

        // Add the title to titleHolderVbox
        titleHolderVbox.getChildren().
            add(title);

        // Add menubar and and titleHolderVbox to main VBox.
        mainVbox.getChildren().addAll(menubar, titleHolderVbox);
        return mainVbox;
    }

    private HBox setupCenterPanel(TableView table, String[] myArray, TextField[] textfields)
    {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(25, 25, 25, 25));

        hbox.getChildren().
            addAll(setupItemsTable(table), setupUserInputFields(myArray, textfields));

        return hbox;
    }

    private HBox setupItemsTable(TableView table)
    {
        table.setEditable(true);

        // Column header.
        TableColumn<Item, String> itemnumberCol = new TableColumn<Item, String>("Item Number");

        TableColumn<Item, String> itemnameCol = new TableColumn<Item, String>("Item Name");
        TableColumn<Item, String> descriptionCol = new TableColumn<Item, String>("Description");

        setupTableColumns(itemnumberCol, "itemnumber", true, false);
        setupTableColumns(itemnameCol, "itemname", true, false);
        setupTableColumns(descriptionCol, "description", true, false);

        // Add the columns to the table.
        table.getColumns().
            setAll(itemnumberCol, itemnameCol, descriptionCol);
        HBox hbox = new HBox();
        // Add the table to HBox.
        hbox.getChildren().
            addAll(table);
        return hbox;

    }

    private void setupTableColumns(TableColumn<Item, String> tableColumn,
        String propertyName, boolean sortable, boolean editable)
    {
        // Set the cell name property that the column will bind to.
        tableColumn.
            setCellValueFactory(new PropertyValueFactory<Item, String>(propertyName));
        // Make the column sortable.
        tableColumn.setSortable(sortable);

        // Make the column editable.
        tableColumn.setEditable(editable);

        // set the cell factory.
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private GridPane setupUserInputFields(String[] myArray, TextField[] textfields)
    {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,
            25,
            25,
            25));

        // Setup the combobox.
        typeComboxSetup();

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

        grid.add(new Label("Status:"),
            0,
            4);
        grid.add(typeCombox,
            1,
            4);

        grid.
            add(setupHBoxButtonPanel(buttons, buttonNames, buttonTips),
                1,
                6,
                1,
                2);

        return grid;
    }

    private void typeComboxSetup()
    {
        typeCombox = new ComboBox();
        typeCombox.getItems().
            addAll("Supply\\Equipment",
                "Supply",
                "Equipment");
        typeCombox.getSelectionModel().
            select("Supply\\Equipment");

    }

    private HBox setupHBoxButtonPanel(Button[] buttons, String[] buttonNames, String[] buttonTips)
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25,
            25,
            25,
            25));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        setUpButtons(buttons, buttonNames, buttonTips);
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
                addItemToDatabase();
                break;

            case "Search":
                searchDatabase();
                break;

            case "Delete":
                break;
            default:
                System.out.println("This choice does not exist.");
                break;
        }
    }

    private void addItemToDatabase()
    {
        String active = (String) typeCombox.getValue();
        // Get number of employees in db plus 1.
        itemCount = (DatabaseOperations.
            count("miss", "select count(*) from items") + 1);
        String itemnumber = "I" + itemCount;
        String itemName = userInputTextFields[1].getText();
        String description = userInputTextFields[2].getText();

        boolean isValid = true;
        isValid &= isValidData(userInputTextFields[1], 20);
        isValid &= isValidData(userInputTextFields[2], 80);

        MessageBoxWindow msg = new MessageBoxWindow();
        if (isValid)
        {
            int updateItem = msg.
                message(title.getText(), "Insert\nItem: " + itemnumber + "\nItem Name: " + itemName + "\nItem Description: " + description);
            if (updateItem == 0)
            {
                // String itemnumber, String itemname, String description, String type
                this.controller.getItemModel().
                    createItemInDB(itemnumber, itemName, description, comboString(active));
                msg.
                    message(title.getText(), "Item added");
                Item item = new Item(itemnumber, itemName, description, active);
                itemTable.getItems().add(item);
                itemTable.setItems(itemTable.getItems());
                clearFields();
            }
            else
            {
                msg.
                    message(title.getText(), "Item not added");
            }
        }
        else
        {
            msg.
                message(title.getText(), "Item not added");
        }
    }

    private void searchDatabase()
    {
        // Clear the controller list
        this.controller.getItemModel().getItemList().clear();

        // Clear table if it has items
        if (this.itemTable.getItems().size() > 0)
        {
            this.itemTable.getItems().clear();
        }
        
        String type = comboString((String) typeCombox.getValue()).equals("") ? "_" : comboString((String) typeCombox.
            getValue());
        // String itemnumber,String itemname, String description, String type
        String itemnumber = userInputTextFields[0].getText().toUpperCase();
        String itemname = userInputTextFields[1].getText().toUpperCase();
        String description = userInputTextFields[2].getText().toUpperCase();

        controller.getItemModel().
            searchItems(itemnumber, itemname, description, type);
        
        // Gets the observable list of the table and adds the observable list
        // from the controller to it.
        itemTable.getItems().addAll(this.controller.getItemModel().getItemList());

        // Display data in table. Table displays its list.
        itemTable.setItems(itemTable.getItems());
        itemTable.getSelectionModel().select(0, null);
        clearFields();

    }

    private String comboString(String data)
    {
        String strToReturn = "";
        switch (data)
        {
            case "Supply":
                strToReturn = "S";
                break;
            case "Equipment":
                strToReturn = "E";
                break;
            default:
                strToReturn = "";

        }
        return strToReturn;
    }

    private void clearFields()
    {
        for (int i = 0; i < userInputTextFields.length; i++)
        {
            userInputTextFields[i].setText("");
            userInputTextFields[i].setStyle(null);
        }
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
