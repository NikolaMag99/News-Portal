package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String email;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String first_name;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String last_name;


    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String password;


    private int type;


    private int status;

    public User() {
    }

    public User(String email, String first_name, String last_name, String password) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
