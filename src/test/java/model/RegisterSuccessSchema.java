package model;

public class RegisterSuccessSchema {
    private String id;
    private String token;
    public RegisterSuccessSchema(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
