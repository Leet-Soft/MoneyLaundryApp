package uni.fmi.masters.moneylaundryapp.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {
    private int id;
    private String username;
    private String password;
    private String gender;
    private String fullName;
    private String avatarPath;
    private String validator;
    public UserEntity(){ }

    public UserEntity(String username, String password, String gender, String fullName) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

}
