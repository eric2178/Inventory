/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginScreenPanel;

/**
 *
 * @author Tanisha
 */ 
public class MainViewFrame extends Application
{

    private Stage stage;
    private LoginScreenPanel login;
    @Override
    public void start(Stage primaryStage)
    {
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        stage = primaryStage;

        login = new LoginScreenPanel(stage);
        
        Scene scene = login.getScene();
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
   
    
    

   
}
