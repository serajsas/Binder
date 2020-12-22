package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.*;
import persistence.JsonReader;

import java.io.IOException;
import java.util.Iterator;

public class BinderController {
    Iterator<User> binderIterator;
    private JsonReader binderReader = new JsonReader("data/RegisteredUsers");
    private JsonReader mainUserReader = new JsonReader("data/MainUser");
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
        try {
            binder = binderReader.read();
            mainUser = mainUserReader.readUser();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        binderIterator = binder.iterator();
        nextUser = binderIterator.next();
    }
}
