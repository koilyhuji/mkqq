package mkqq.DTO;

public class UserDTO {
    private String userID;
    private String name;
    private String email;
    private String passwordHash;
    private boolean isAdmin;

    public UserDTO() {
    }

    public UserDTO(String userID, String fullName, String email, String passwordHash,
                   boolean isAdmin) {
        this.userID = userID;
        this.name = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }



}