package no.ntnu.ecomback.model;

import jakarta.persistence.*;
/**
 * The User class represents a user of the e-commerce application.
 */
@Entity
@Table
public class User {

    /**
     * The email address of the user.
     */
    @Id
    private String email;

    /**
     * The first name of the user.
     */
    @Column
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column
    private String lastName;

    /**
     * The username of the user.
     */
    @Column
    private String username;

    /**
     * The password of the user.
     */
    @Column
    private String password;

    /**
     * The role of the user
     */

    @Column
    private Role role;

    /**
     * Constructs a new User object with default values for all fields.
     */
    public User() {
    }

    /**
     * Constructs a new User object with the given values for all fields.
     * @param email the email address of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user
     */

    public User(String email, String firstName, String lastName, String username, String password, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the email address of the user.
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the first name of the user.
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username of the user.
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }
    /**
     * Sets the password of the user.
     * @param role the password to set
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
