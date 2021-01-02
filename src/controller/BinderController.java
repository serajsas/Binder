package controller;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Binder;
import model.User;
import model.UserInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Observable;

public class BinderController{
    private static ConnectionClass connectionClass;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    Iterator<User> binderIterator;
//    private JsonReader binderReader = new JsonReader("data/RegisteredUsers");
//    private JsonReader mainUserReader = new JsonReader("data/MainUser");
//    private JsonWriter jsonWriter = new JsonWriter("data/RegisteredUsers");
    private Binder binder;
    private User mainUser;
    private User nextUser;
    @FXML
    private Button swipeRight;

    @FXML
    private Button swipeLeft;

    @FXML
    private TextField otherUsers;

    @FXML
    void initialize() {
        init();
        showRegisteredUsers();
    }

    private void showRegisteredUsers() {
        showNextUser(isNextUserMainUser());
    }

    private void showNextUser(boolean isNextUserMainUser) {
        otherUsers.setText(nextUser.getUserInformation().getUserName());
    }

    private boolean isNextUserMainUser() {
        if (nextUser.equals(mainUser)) {
            nextUser = binderIterator.next();
            return false;
        }
        return true;
    }


    // MODIFIES: this
    // EFFECTS: initializes binder
    private void init() {
        initializeFields();
        initializeSwipeLeftRightButton();
    }

    public void intilizeUsers() {
        binder = new Binder();
        User result;
        String query = "SELECT * FROM binder.users";
        connectionClass = new ConnectionClass();
        try {
            connection = connectionClass.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String savedUsername = resultSet.getNString("username");
                String savedPassWord = resultSet.getNString("password");
                result = new User(new UserInformation(savedUsername, savedPassWord));
                binder.addUser(result);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }


    private void initializeSwipeLeftRightButton() {
        swipeRight.setOnAction(event -> {
            mainUser.like(nextUser);
            if (binderIterator.hasNext()) {
                nextUser = binderIterator.next();
                showNextUser(isNextUserMainUser());
            } else {
                otherUsers.setText("No More Users");
            }
        });
        swipeLeft.setOnAction(event -> {
            mainUser.pass(nextUser);
            if (binderIterator.hasNext()) {
                nextUser = binderIterator.next();
                showNextUser(isNextUserMainUser());
            } else {
                otherUsers.setText("No More Users");
            }
        });
    }

    private void initializeFields() {
        mainUser = new User(new UserInformation(SignInController.mainUserUserName,SignInController.mainUserPassWord));
        intilizeUsers();
        binderIterator = binder.iterator();
        nextUser = binderIterator.next();
    }

}
