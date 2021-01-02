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
import model.User;
import model.UserInformation;
import persistence.JsonWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
//    JsonWriter jsonWriter;
    Binder binder = new Binder();
    private ConnectionClass connectionClass;
    private Connection connection;
    private PreparedStatement preparedStatement;
    @FXML
    private TextField invalid;
    @FXML
    private Button registerButton;

    @FXML
    private TextField signUpUsername;
    @FXML
    private Button login;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    void initialize() {
//        jsonWriter = new JsonWriter("data/registeredUsers");
        registerButton.setOnAction(event -> {
            String userName = signUpUsername.getText();
            String passWord = signUpPassword.getText();
            boolean isValid = checkValid(userName, passWord);
            if (!isValid) {
                invalid.setText("Check your credentials");
            } else {
                invalid.setText("");
                binder.addUser(new User(new UserInformation(userName, passWord)));
            }
            if (isValid) {
                connectionClass = new ConnectionClass();
                String insert = "INSERT INTO users(username,password)"
                        + "VALUES(?,?)";

                try {
                    connection = connectionClass.getDbConnection();
                    preparedStatement = connection.prepareStatement(insert);
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, passWord);
                    preparedStatement.executeUpdate();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                invalid.setText("You have been successfully registered");
            }
        });
        login.setOnAction(event -> {

            //Take users to signup screen
            login.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../scenes/LoginScene.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        });

    }

    private boolean checkValid(String userName, String passWord) {
        return !userName.equals("") && !passWord.equals("");
    }

}


