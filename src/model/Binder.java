package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Binder implements Iterable<User>, Writable {
    private List<User> users;

    public Binder() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);

        }
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }


    @Override
    public String toString() {
        return "Binder: " + users;
    }

    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Binder", usersToJson());
        return jsonObject;
    }

    //EFFECTS: returns tasks in this todolist to JSON array
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            jsonArray.put(user.toJson());
        }
        return jsonArray;
    }
}
