package ui;

import connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import model.UserInformation;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    private static ConnectionClass connectionClass;
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        launch(args);
//        User user;
//        JsonReader jsonReader = new JsonReader("data/RegisteredUsers");
//        try {
//             user = jsonReader.readUser();
//            System.out.println(user.getUserInformation().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../scenes/SignUpScene.fxml"));
        primaryStage.setTitle("");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
