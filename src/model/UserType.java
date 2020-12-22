package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;
import java.util.Observable;

public abstract class UserType extends Observable implements Writable {
    private UserInformation userInformation;

    public UserType(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        UserType userType = (UserType) o;
        return Objects.equals(userInformation, userType.userInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInformation);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("User Information", userInformation);
        return jsonObject;
    }
}
