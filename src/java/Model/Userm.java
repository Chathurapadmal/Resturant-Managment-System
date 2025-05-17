package Model;

public class Userm {
    private int id;
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String bio; // New field
    private String profilePicture; // New field (file name of the image)

    public Userm() {}

    public Userm(int id, String username, String password, String role, String fullName, String bio, String profilePicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    // ID
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Username
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // Password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Role
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Full Name
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    // Bio
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    // Profile Picture
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
