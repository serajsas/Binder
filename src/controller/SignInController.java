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
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignInController {
    JsonReader jsonReader = new JsonReader("data/RegisteredUsers");
    JsonWriter jsonWriter = new JsonWriter("data/MainUser");
    Binder binder;


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
        try {
            binder = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Error");
        }
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
                try {
                    jsonWriter.open();
                } catch (FileNotFoundException e) {
                    System.out.println("IO Exception");
                }
                jsonWriter.write(new User(new UserInformation(userName, passWord)));
                jsonWriter.close();
                //Take users to main application screen
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
        UserInformation userInformation = new UserInformation(userName, passWord);
        for (User user : binder) {
            if (user.getUserInformation().equals(userInformation)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkValid(String userName, String passWord) {
        return !userName.equals("") && !passWord.equals("");
    }


}
