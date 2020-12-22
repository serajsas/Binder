package persistence;


import model.Binder;
import model.User;
import model.UserInformation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Used the code provided on Github to implement Json Reader
// Represents a reader that reads JSON representation of todolist
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Binder read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBinder(jsonObject);
    }

    public User readUser() throws IOException {
        String jsonUser = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonUser);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses binder from JSON object and returns it
    private Binder parseBinder(JSONObject jsonObject) {
        Binder binder = new Binder();
        addUsers(binder, jsonObject);
        return binder;
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String userInformation = jsonObject.getString("User Information");
        String[] userInfo = userInformation.split(",");
        User user = new User(new UserInformation(userInfo[0], userInfo[1]));
        return user;
    }

    // MODIFIES: todolist
    // EFFECTS: parses tasks from JSON object and adds them to console interface
    private void addUsers(Binder binder, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Binder");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(binder, nextUser);
        }
    }

    // MODIFIES: todolist
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addUser(Binder binder, JSONObject jsonObject) {
        String userInformation = jsonObject.getString("User Information");
        String[] userInfo = userInformation.split(",");
        User user = new User(new UserInformation(userInfo[0], userInfo[1]));
        binder.addUser(user);
    }

}
