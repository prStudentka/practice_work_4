package model;

public class UpdateUserSchema {
    private String name;
    private String job;

    public UpdateUserSchema(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
