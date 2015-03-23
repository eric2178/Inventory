package login;

import data_base_operations.DatabaseOperations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import miss_class_objects.Users;
import scene.Menubar;

/**
 *
 * @author Tanisha
 */
public class LoginScreenPanel
{

    private Scene scene;
    private TextField userTextField;
    private PasswordField passwordField;
    private Text actionTarget;
    private Text scentitle;
    private Stage stage;
    private Menubar menubar;
    private Users user;
// select * from USERS where EMPLOYEEID = 'E1' and NEWPASSWORD = '3r1cM@@r3'

    public LoginScreenPanel(Stage stage)
    {

        // Creates a Grid type layout. That will hold components
        // such as buttons, labels, you get it. Its kind of like your JPanel
        // Here this is centered.
        GridPane grid = new GridPane();

        // Places the grid where you want it
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Sets the title in the Scene/JPanel
        scentitle = new Text("Welcome");

        grid.add(scentitle, 0, 0, 2, 1);

        // Create Label/JLabel.
        Label usernameLabel = new Label("User Name:");
        grid.add(usernameLabel, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        grid.add(passwordField, 1, 2);

        //Add Button/JButton
        Button loginButton = new Button("Login");
        Button changePasswordButton = new Button("Change Password");

        // HBox is a horizontal box adds stuff to it
        // in flowlayout fashion/style.
        HBox hbox = new HBox(10); // Another layout which can be used like a JPanel.
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(changePasswordButton, loginButton);

        grid.add(hbox, 1, 4);

        // Add action text.
        actionTarget = new Text();

        grid.add(actionTarget, 1, 6);

        loginButton.setOnAction(e ->
        {

            if (loginValidation(userTextField.getText(), passwordField.
                getText()))
            {
                this.stage = stage;
                this.menubar = new Menubar(this.stage, user);
                VBox root = new VBox(menubar);
                Scene scene = new Scene(root, 150, 150);
                this.stage.setScene(scene);
            }
            else
            {
                actionTarget.setText("Credentials not verified.");
            }
        });

        // Allows to see gridlines
//        grid.setGridLinesVisible(true);
        // The Scene adds the grid/layout with components.
        // Priactical application: Swap layouts into scene.
        // Extend layouts instead of JPanels.
        scene = new Scene(grid, 300, 275);

        myCssStuff();
    }

    /**
     * Style sheets are applied to Scene objects.
     */
    private void myCssStuff()
    {
        // This goes to a css file remmeber these must be in the same folder.
        scene.getStylesheets().add(LoginScreenPanel.class.
            getResource("Login.css").
            toExternalForm());

        // Set the Id from the css.
        actionTarget.setId("actiontarget");
        scentitle.setId("welcometext");
    }

    public Scene getScene()
    {
        return scene;
    }

    public TextField getUserTextField()
    {
        return userTextField;
    }

    public TextField getPasswordField()
    {
        return passwordField;
    }

    /**
     * The loginValidation method checks to see if the user is valid and allows
     * access to the system.
     *
     * @param username
     * @param userpassword
     * @return
     */
    public boolean loginValidation(String username, String userpassword)
    {
        boolean isAUser = false;

        String query = "SELECT users.EMPLOYEEID,users.NEWPASSWORD,users.OLDPASSWORD,users.PERMISSIONS "
            + "FROM users,EMPLOYEES "
            + "where USERS.EMPLOYEEID = EMPLOYEES.EMPLOYEEID "
            + "and EMPLOYEES.ACTIVE = 'Y' "
            + "and USERS.EMPLOYEEID = '"
            + username
            + "' "
            + "and USERS.NEWPASSWORD = '"
            + userpassword
            + "'";

        String[] userData = DatabaseOperations.getUserString(query).split(",");
        if (!username.isEmpty() && !userpassword.isEmpty())
        {
//        empID + "," + newPassword + "," + oldPassword + "," + permissions;
            // Create a new user object to use for data finger print.
            if (userData[0].equals(username) && userData[1].equals(userpassword))
            {
                this.user = new Users();
                this.user.setEmployeeId(userData[0]);
                this.user.setNewPassword(userData[1]);
                this.user.setOldPassword(userData[2]);
                this.user.setPermissions(userData[3]);
                isAUser = true;
            }
        }
        return isAUser;
    }

}
