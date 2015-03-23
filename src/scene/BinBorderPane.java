package scene;

import controllers.Controller;
import data_base_operations.DatabaseOperations;
import interfaces.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import miss_class_objects.Bin;

/**
 *
 * @author Tanisha
 */
public class BinBorderPane extends BorderPane
{

    private Menubar menubar;         // Menubar for view.
    private Controller controller;   // Controller to access models.
    private Text title;
    private TableView<Bin> binTable;
    private String[] userPrompt;
    private TextField[] binNameTextField;
    private ListView<String> storeroomsList;
    private ListView<String> itemsList;
    private Button[] buttons;
    private String[] buttonNames;
    private String[] buttonTips;

    public BinBorderPane(Menubar menubar)
    {
        this.instantiateInstanceFields(menubar);
        this.setTop(setupNorthPanel(menubar, title));
        this.setCenter(centerViewPanel(binTable));
    }

    private void instantiateInstanceFields(Menubar menubar)
    {
        this.menubar = menubar;
        this.controller = this.menubar.getControl();   // Controller to access models.
        this.title = new Text("Bins");
        this.binTable = new TableView<Bin>();
        this.userPrompt = new String[]
        {
            "Bin Name:"
        };
        this.binNameTextField = new TextField[this.userPrompt.length];
        this.storeroomsList = new ListView<String>();
        this.itemsList = new ListView<String>();
        this.buttonNames = new String[]
        {
            "Insert", "Search", "Delete"
        };
        this.buttonTips = new String[]
        {
            "Add bin to the database.", "Enter a bin number to\nsearch for bins.", "Remove a bin\nfrom the database."
        };
        this.buttons = new Button[this.buttonNames.length];
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

    // need a hbox to hold stuff
    private HBox centerViewPanel(TableView table)
    {
        VBox storeroomAndItemVbox = new VBox();
        storeroomAndItemVbox.getChildren().
            addAll(storeroomsListVbox(), itemsListVbox());

        VBox tableAndUserInput = new VBox();
        tableAndUserInput.getChildren().
            addAll(setupGridPane(this.userPrompt, this.binNameTextField), setupEmployeeTable(table));

        HBox hbox = new HBox();
        hbox.getChildren().
            addAll(storeroomAndItemVbox, tableAndUserInput);
        return hbox;
    }

    private VBox setupEmployeeTable(TableView table)
    {
        table.setEditable(true);

        // Set each column to a getter in the Employee class.
        TableColumn<Bin, String> storeroomCol = new TableColumn<Bin, String>("Storeroom");
        TableColumn<Bin, String> itemCol = new TableColumn<Bin, String>("Item");
        TableColumn<Bin, String> binnumCol = new TableColumn<Bin, String>("Bin");
        TableColumn<Bin, String> curbalCol = new TableColumn<Bin, String>("Count");

        // Sets up the table columns for editing.
        setupTableColumns(storeroomCol, "storeroomname", true, false);
        setupTableColumns(itemCol, "itemnumber", true, false);
        setupTableColumns(binnumCol, "binnumber", true, false);
        setupTableColumns(curbalCol, "count", true, false);

        // Add the columns to the table.
        table.getColumns().
            setAll(storeroomCol, itemCol, binnumCol, curbalCol);
        table.setMaxHeight(200);
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        // Add the table to HBox.
        vbox.getChildren().addAll(new Label("Search Results:"), table);
        return vbox;
    }

    private void setupTableColumns(TableColumn<Bin, String> tableColumn, String propertyName, boolean sortable, boolean editable)
    {
        // Set the cell name property that the column will bind to.
        tableColumn.
            setCellValueFactory(new PropertyValueFactory<Bin, String>(propertyName));
        // Make the column sortable.
        tableColumn.setSortable(sortable);

        // Make the column editable.
        tableColumn.setEditable(editable);

        // set the cell factory.
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private VBox storeroomsListVbox()
    {
        Label label = new Label("Choose a Storeroom");
        ObjectMapper<String> storeroomMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                l.add(rs.getString("storeroomname"));
            }
        });
        String searchQuery = "select * from storerooms";
        DatabaseOperations.
            getResultSetIntoAList(searchQuery, storeroomsList.getItems(), storeroomMapper);
        storeroomsList.setItems(storeroomsList.getItems());
        storeroomsList.setPrefWidth(200);
        storeroomsList.setPrefHeight(100);
        storeroomsList.getSelectionModel().select(0);
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        vbox.getChildren().addAll(label, storeroomsList);
        return vbox;
    }

    private VBox itemsListVbox()
    {
        Label label = new Label("Choose an Item");
        ObjectMapper<String> storeroomMapper = ((l, rs) ->
        {
            while (rs.next())
            {
                l.add(rs.getString("itemnumber") + "    " + rs.
                    getString("itemname"));
            }
        });
        String searchQuery = "select * from items";
        DatabaseOperations.
            getResultSetIntoAList(searchQuery, itemsList.getItems(), storeroomMapper);
        itemsList.setItems(itemsList.getItems());
        itemsList.setPrefWidth(200);
        itemsList.setPrefHeight(100);
        itemsList.getSelectionModel().select(0);
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        vbox.getChildren().addAll(label, itemsList);
        return vbox;
    }

    private GridPane setupGridPane(String[] myArray, TextField[] textfields)
    {
        int count = 0;
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25, 25, 25, 25));

        for (int i = 0; i < myArray.length; i++)
        {
            // Create TextFields and prompts.
            textfields[i] = new TextField();
            textfields[i].setPromptText("Enter " + myArray[i].
                substring(0, myArray[i].length() - 1));

            // Add elements to the grid.
            grid.add(new Label(myArray[i]), 0, i);
            grid.add(textfields[i], 1, i);
            count = i + 2;
        }

        grid.
            add(buttonHbox(buttonNames, buttonTips, buttons), 1, count, 1, 2);

        return grid;
    }

    private HBox buttonHbox(String[] buttonnames, String[] buttontips, Button[] buttons)
    {
        for (int i = 0; i < buttonnames.length; i++)
        {
            buttons[i] = new Button(buttonnames[i]);
            buttons[i].setOnAction(e -> buttonClicked(e));
            buttons[i].setTooltip(new Tooltip(buttontips[i]));
        }

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(buttons);
        return hbox;
    }

    private void buttonClicked(ActionEvent e)
    {
        String s = ((Button) e.getSource()).getText();

        switch (s)
        {
            case "Insert":
                addBinnumberToDatabase();
                break;

            case "Search":
                break;

            case "Delete":
                break;
            default:
                System.out.println("This choice does not exist.");
                break;
        }
    }

    private void insertBinIntoDB()
    {
//        "Insert", "Delete"
    }

    private void addBinnumberToDatabase()
    {
        String storeroom = (String) storeroomsList.getSelectionModel().
            getSelectedItem();
        String[] itemnumber = ((String) itemsList.getSelectionModel().
            getSelectedItem()).split(" ");
        checkTextBox(binNameTextField[0], 8);
        MessageBoxWindow msg = new MessageBoxWindow();
        int addBin = msg.
            message(title.getText(), "Add bin to"
                + "\nStoreroom: " + storeroom
                + "\nItem Number:"
                + itemnumber[0]
                + "\nBin Number: " + binNameTextField[0].
                getText());
        if (addBin == 0)
        {
            if (controller.getBinModel().
                createBinInDataBase(storeroom, itemnumber[0], binNameTextField[0].
                    getText(), 0, 0, 0.0))
            {
                clearFields(binNameTextField);
            }
            else
            {
                msg.
                    message(title.getText(), "Bin not Added.");
            }
        }
        else
        {
            msg.
                message(title.getText(), "Bin not Added.");
        }
    }

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

    private void clearFields(TextField[] infoTextFields)
    {
        for (int i = 0; i < infoTextFields.length; i++)
        {
            infoTextFields[i].setText("");
            infoTextFields[i].setStyle(null);
        }
    }
}
