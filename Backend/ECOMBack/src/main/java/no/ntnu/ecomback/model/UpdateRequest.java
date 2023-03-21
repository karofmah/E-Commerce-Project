package no.ntnu.ecomback.model;

public class UpdateRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String newPassword;
    private String currentPassword;

    public UpdateRequest(String email, String firstName, String lastName, String newPassword, String currentPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
