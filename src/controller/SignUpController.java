package controller;


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
import ui.ConsoleInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignUpController {
    JsonWriter jsonWriter;
    Binder binder = new Binder();
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
        jsonWriter = new JsonWriter("data/registeredUsers");
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
                try {
                    jsonWriter.open();
                } catch (FileNotFoundException e) {
                    System.out.println("Error in opening the file");
                }
                jsonWriter.write(binder);
                jsonWriter.close();
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


