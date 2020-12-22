package model;

import java.util.Objects;

public class UserInformation {
    private int id;
    private String userName;
    private String Name;
    private String passWord;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private RelationShip relationShip;

    public UserInformation(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public RelationShip getRelationShip() {
        return relationShip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(userName, that.userName) && Objects.equals(passWord, that.passWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, passWord);
    }

    @Override
    public String toString() {
        return  userName + "," + passWord;
    }
}
