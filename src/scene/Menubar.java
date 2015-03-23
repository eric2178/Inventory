/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import miss_class_objects.Users;

/**
 *
 * @author Tanisha
 */
public class Menubar extends MenuBar
{

//    private MenuBar menuBar;
    private Menu fileMenu;
    private Menu moduleMenu;

    private Menu invetoryMenu;
    private MenuItem storeroomItem;
    private MenuItem itemItem;
    private MenuItem locationItem;
    private MenuItem employeeItem;
    private Stage stage;
    private Controller control;
    private Users user;
    
    // Views. will need permissions to determine what
    // the user can use. work in modules to establish this.
    EmployeeBorderPaneTable employeeView;
    StoreroomBorderPane storeroomView;
    ItemBorderPane itemView;
    BinBorderPane binView;
    
    public Menubar(Stage stage, Users user)
    {
        this.stage = stage;
        this.user = user;
        this.control = new Controller();

        createFileMenu();
        createModule();
        this.getMenus().addAll(fileMenu, moduleMenu);
    }

    private void createFileMenu()
    {
        // Create menu.
        fileMenu = new Menu("File");

        // Create menuItem.
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e ->
        {
            System.exit(0);
        });

        // Add item to menu.
        fileMenu.getItems().add(exit);
    }

    private void createModule()
    {
        moduleMenu = new Menu("Modules");

        invetoryMenu = new Menu("Inventory");
        storeroomItem = new MenuItem("Storerooms");

        storeroomItem.setOnAction(e ->
        {
            storeroomView = new StoreroomBorderPane(this);
            
            Scene scene = new Scene(storeroomView);
            stage.setScene(scene);
            stage.sizeToScene();
        });
        
        itemItem = new MenuItem("Items");
        itemItem.setOnAction(e ->
        {
            itemView = new ItemBorderPane(this);
            
            Scene scene = new Scene(itemView);
            stage.setScene(scene);
            stage.sizeToScene();
        });
        locationItem = new MenuItem("Locations");
        locationItem.setOnAction(e ->
        {
            binView = new BinBorderPane(this);
             
            Scene scene = new Scene(binView);
            stage.setScene(scene);
            stage.sizeToScene();
        });
        employeeItem = new MenuItem("Employees");
        employeeItem.setOnAction(e ->
        {
            employeeView = new EmployeeBorderPaneTable(this);
            Scene scene = new Scene(employeeView);
            stage.setScene(scene);
            stage.sizeToScene();
        });
        invetoryMenu.getItems().addAll(storeroomItem, itemItem, locationItem);

        moduleMenu.getItems().addAll(invetoryMenu, employeeItem);

    }

    public Controller getControl()
    {
        return control;
    }

    public Users getUser()
    {
        return user;
    }

}
