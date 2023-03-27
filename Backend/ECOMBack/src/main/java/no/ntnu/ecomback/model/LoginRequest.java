/**

 The LoginRequest class represents a request to log in a user with their email and password.
 */
package no.ntnu.ecomback.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {
    /**
     * The email address of the user to be logged in.
     */
    @Schema(description = "The email address of the user to be logged in")
    private String email;

    /**
     * The password of the user to be logged in.
     */
    @Schema(description = "The password of the user to be logged in")
    private String password;

    /**
     * Constructor for the LoginRequest class.
     *
     * @param email    the email address of the user to be logged in
     * @param password the password of the user to be logged in
     */
    @JsonCreator
    public LoginRequest(@JsonProperty("email") final String email, @JsonProperty("password") final String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Default constructor for the LoginRequest class.
     */
    public LoginRequest() {

    }

    /**
     * Returns the email address of the user to be logged in.
     *
     * @return the email address of the user to be logged in
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password of the user to be logged in.
     *
     * @return the password of the user to be logged in
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
}