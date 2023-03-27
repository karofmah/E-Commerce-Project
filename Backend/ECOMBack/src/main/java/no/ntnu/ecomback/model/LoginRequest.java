package no.ntnu.ecomback.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {
    @Schema(description = "The email address of the user to be logged in")
    private String email;
    @Schema(description = "The password of the user to be logged in")
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
