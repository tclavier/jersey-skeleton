package fr.iutinfo.beans;

/**
 * Represent a User with his id, name, passwd, email and his relative cookie
 * @author Florent
 */
public class User {

    private int id = 0;
    private long facebookId;
    private String name;
    private String password;
    private String email;
    private String cookie;

    /**
     * Constructor of User
     * @param id
     * @param name 
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Void User constructor
     */
    public User() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getFacebookId() { return facebookId; }
    public void setFacebookId(long facebookId) { this.facebookId = facebookId; }

    public String getCookie() { return cookie; }
    public void setCookie(String cookie) { this.cookie = cookie; }
    
    @Override
    public String toString() { return id + ": " + name; }

    
    public boolean equals(Object other) { return this.id == ((User) other).id; }
}
