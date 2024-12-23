package model;

public class PostRegisterSchema {
    private String email;
    private String password;

    public PostRegisterSchema(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
