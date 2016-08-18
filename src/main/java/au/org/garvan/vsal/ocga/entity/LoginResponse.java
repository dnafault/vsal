package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LoginResponse")
public class LoginResponse {

    private String sessionId;
    private String userId;

    public LoginResponse() {
        // needed for JAXB
    }

    public LoginResponse(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
