package au.org.garvan.vsal.ocga.entity;

public class LoginRequest {
    private String password;

    public LoginRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
