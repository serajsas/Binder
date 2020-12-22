package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observer;

public class User extends UserType implements Iterable<UserType> {
    private List<UserType> userTypes;

    //EFFECTS: Creates a user with user information and empty list of users that they like/pass
    public User(UserInformation userInformation) {
        super(userInformation);
        userTypes = new ArrayList<>();
    }

    public User() {
        super(null);
        userTypes = new ArrayList<>();
    }

    //EFFECTS: Creates a user with user information and empty list of users that they like/pass
    public User(UserInformation userInformation, Observer observer) {
        super(userInformation);
        userTypes = new ArrayList<>();
        addObserver(observer);
    }

    public boolean hasPassedBefore(User user) {
        return userTypes.contains(user);
    }
//    }
//
//    public void addUserType(UserType userType) {
//        if (!userTypes.contains(userType)) {
//            userTypes.add(userType);
//        }
//    }
//
//    public void removeUser(UserType userType) {
//        if (userTypes.contains(userType)) {
//            userTypes.remove(userType);
//        }
//    }

    //EFFECTS: pass a user
    public void pass(UserType user) {
        userTypes.add(new UserPass(user.getUserInformation()));
    }

    //EFFECTS: Like a user
    public void like(UserType user) {
        UserLike userLike = new UserLike(user.getUserInformation());
        userTypes.add(userLike);
        setChanged();
        notifyObservers(user);
    }

    //EFFECTS: Match a user and remove it from being likedUser
    public void match(UserType userType) {
        UserMatch userMatch = new UserMatch(userType.getUserInformation());
        userTypes.add(userMatch);
        userTypes.remove(userType);
    }


    //EFFECTS: Return true if the given userType is liked by user
    public boolean isLiked(UserType userType) {
        for (UserType user : userTypes) {
            if (user.equals(userType) && user instanceof UserLike) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: Return true if the given userType is passed by user
    public boolean isPassed(UserType userType) {
        for (UserType user : userTypes) {
            if (user.equals(userType) && user instanceof UserPass) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<UserType> iterator() {
        return userTypes.iterator();
    }


}
