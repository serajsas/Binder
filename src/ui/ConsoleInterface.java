package ui;

import model.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ConsoleInterface implements Observer {
    public static final String JSON_STORE = "data/saved";
    private boolean isLoggedIn;
    private Scanner input;
    private Binder binder;
    private User mainUser;


    // EFFECTS: runs the To-do Application
    public ConsoleInterface() {
        runInterface();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runInterface() {
        boolean keepGoing = true;
        String command;

        init();
        while (keepGoing) {
            if (!isLoggedIn)
                displayMenu();
            else displayMenuAfterLogin();
            command = input.nextLine();
            command = command.toLowerCase().trim();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                if (!isLoggedIn)
                    processCommand(command);
                else
                    processCommandAfterLogin(command);
            }
        }

        System.out.println("Have a nice day!!");
    }

    // MODIFIES: this
    // EFFECTS: initializes todoList
    private void init() {
        input = new Scanner(System.in);
        binder = new Binder();
        isLoggedIn = false;
    }

    //EFFECTS: Displays the menu of options to user
    private void displayMenu() {
        System.out.println("Select from:");
        System.out.println("\ta -> Register");
        System.out.println("\tb -> Login");
        System.out.println("\tq -> quit");
    }

    private void displayMenuAfterLogin() {
        System.out.println("\tc -> Show Matches");
        System.out.println("\td -> Show People I Passed");
        System.out.println("\te -> Show People I Liked");
        System.out.println("\tf -> Show Users");
        System.out.println("\tg -> Log Out");
    }

    //EFFECTS: Process user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                Register();
                break;
            case "b":
                isLoggedIn = Login();
                break;
        }
    }

    private void processCommandAfterLogin(String command) {
        switch (command) {
            case "c":
                showPeopleMatch();
                break;
            case "d":
                showPeoplePassed();
                break;
            case "e":
                showPeopleLiked();
                break;
            case "f":
                showUsers();
                break;
            case "g":
                logOut();
                break;
        }

    }

    private void logOut() {
        updateUser();
        isLoggedIn = false;
    }

    private void updateUser() {
        for (User next : binder) {
            if (next.equals(mainUser)) {
                next = mainUser;
            }
        }
    }

    private void showUsers() {
        for (User user : binder) {
            if (!user.equals(mainUser)) {
                if (showUser(user)) break;
            }
        }
    }

    private boolean showUser(User user) {
        if (!mainUser.hasPassedBefore(user)) {
            System.out.println(user.getUserInformation().getUserName());
            System.out.println("Press R to swipe right and L to swipe left");
            String answer = input.nextLine().toUpperCase();
            if (answer.equals("R")) {
                swipeRight(user);
                return true;
            } else if (answer.equals("L")) {
                swipeLeft(user);
                return true;
            }
        }
        return false;
    }


    private void Register() {
        System.out.println("Enter your username:");
        String userName = input.nextLine();
        System.out.println("Enter your Password:");
        String passWord = input.nextLine();
        User user = new User(new UserInformation(userName, passWord), this);
        binder.addUser(user);
    }

    private boolean Login() {
        System.out.println("Enter your username:");
        String userName = input.nextLine();
        System.out.println("Enter your Password:");
        String passWord = input.nextLine();
        String response = "null";
        while (!isCredentialsCorrect(userName, passWord)) {
            System.out.println("Please check your username and password");
            System.out.println("Enter your username:");
            userName = input.nextLine().trim();
            System.out.println("Enter your Password:");
            passWord = input.nextLine().trim();
            if (isCredentialsCorrect(userName, passWord))
                break;
            System.out.println("Press q to quit or enter to try again");
            response = input.nextLine();
        }
        if (!response.equals("q")) {
            System.out.println("You have successfully logged into your account");
            for (User user : binder) {
                if (user.getUserInformation().equals(new UserInformation(userName, passWord))) {
                    mainUser = user;
                }
            }
            return true;
        }
        return false;
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

    private boolean swipeRight(User user) {
        mainUser.like(user);
        return mainUser.isLiked(user);
    }

    private boolean swipeLeft(User user) {
        mainUser.pass(user);
        return mainUser.isPassed(user);
    }

    private void showPeoplePassed() {
        for (UserType passed : mainUser) {
            if (passed instanceof UserPass)
                System.out.println(passed.getUserInformation().getUserName());
        }
    }

    private void showPeopleLiked() {
        for (UserType passed : mainUser) {
            if (passed instanceof UserLike)
                System.out.println(passed.getUserInformation().getUserName());
        }
    }

    private void showPeopleMatch() {
        for (UserType passed : mainUser) {
            if (passed instanceof UserMatch)
                System.out.println(passed.getUserInformation().getUserName());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof User) {
            User liker = (User) o;
            User likedUser = (User) arg;
            pushNotification(liker, likedUser);

        }
    }

    private void notifyUser(User user, User liker) {
        System.out.println(user.getUserInformation().getUserName() + " has matched with: "
                + liker.getUserInformation().getUserName());
    }

    private void pushNotification(User liker, User likedUser) {
        if (isMatched(liker, likedUser)) {
            notifyUser(liker, likedUser);
            notifyUser(likedUser, liker);
        }
    }

    //EFFECTS: Check when a liker likes likedUser and returns true in case it's a match
    //         Otherwise return false
    private boolean isMatched(User liker, User likedUser) {
        if (likedUser.isLiked(liker)) {
            liker.match(likedUser);
            likedUser.match(liker);
            return true;
        }
        return false;
    }


}
