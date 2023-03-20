package no.ntnu.ecomback.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class LoginRequest {
    private String email;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("email") final String email, @JsonProperty("password") final String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {

    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

}
