/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controllers.Controller;
import data_base_operations.DatabaseOperations;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import miss_class_objects.Users;
import scene.BinBorderPane;
import scene.ItemBorderPane;
import scene.Menubar;

/**
 *
 * @author Tanisha
 */
public class Test extends Application
{

    @Override
    public void start(Stage primaryStage)
    {

        Controller control = new Controller();
        Users use = new Users();
        use.setEmployeeId("E1");
        use.setNewPassword("3r1cM@@r3");
        use.setOldPassword("password");
        use.setPermissions("master");

        Menubar mb = new Menubar(primaryStage, use);
        BinBorderPane emp = new BinBorderPane(mb);

        StackPane root = new StackPane();
        root.getChildren().add(emp);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
