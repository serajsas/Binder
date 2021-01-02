package controller;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Binder;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {
    protected static String mainUserUserName;
    protected static String mainUserPassWord;
//    JsonReader jsonReader = new JsonReader("data/RegisteredUsers");
//    JsonWriter jsonWriter = new JsonWriter("data/MainUser");
//    Binder binder;
    private ConnectionClass connectionClass;
    private Connection connection;
    private PreparedStatement preparedStatement;


    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginSignInButton;

    @FXML
    private Button loginSignUPButton;

    @FXML
    private TextField invalid;

    @FXML
    void initialize() {
        proceedLogin();
        proceedSignUp();
//        try {
//            binder = jsonReader.read();
//        } catch (IOException e) {
//            System.out.println("Error");
//        }
    }

    private void proceedSignUp() {
        loginSignUPButton.setOnAction(event -> {
            //Take users to signup screen
            loginSignUPButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../scenes/SignUpScene.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void proceedLogin() {
        loginSignInButton.setOnAction(event -> {
            String userName = loginUsernameField.getText().trim();
            String passWord = loginPasswordField.getText().trim();
            boolean isValid = checkValid(userName, passWord);
            boolean isCorrect = isCredentialsCorrect(userName, passWord);
            if (!isCorrect || !isValid) {
                invalid.setText("Check your credentials");
            } else {
                //Take user to main application screen
                loginSignInButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../scenes/BinderScene.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

    }

    private boolean isCredentialsCorrect(String userName, String passWord) {
        String query = "SELECT * FROM binder.users";
        connectionClass = new ConnectionClass();
        try {
            connection = connectionClass.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                String savedUsername = resultSet.getNString("username");
                String savedPassWord = resultSet.getNString("password");
                if (savedUsername.equals(userName) && savedPassWord.equals(passWord)) {
                    mainUserUserName = savedUsername;
                    mainUserPassWord = savedPassWord;
                    return true;
                }

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private boolean checkValid(String userName, String passWord) {
        return !userName.equals("") && !passWord.equals("");
    }


}
