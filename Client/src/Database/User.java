package Database;

import java.io.Serializable;

public class User implements Serializable {
    private int userID;
    private String userName;
    private final String password;
    private int orgID;
    private final boolean admin;

    /**
     * The constructor for the user object
     *
     * @param userID the ID number of the user
     * @param userName the name of the user
     * @param password the password of the user
     * @param orgID the ID of the organization they belong to
     * @param admin whether they are admin or not
     */
    public User(int userID, String userName, String password, int orgID, boolean admin){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.orgID = orgID;
        this.admin = admin;
    }

    /**
     * The constructor for the user object
     *
     * @param userID the ID number of the user
     * @param userName the name of the user
     * @param orgID the ID of the organization they belong to
     * @param admin whether they are admin or not
     */
    public User(int userID, String userName, int orgID, boolean admin){
        this.userID = userID;
        this.userName = userName;
        this.password = null;
        this.orgID = orgID;
        this.admin = admin;
    }

    /**
     * Gets the ID number of the user
     *
     * @return the user's ID number
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Gets the username of the user
     *
     * @return the user's username
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Gets the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the organization ID associated with the user
     *
     * @return the user's organization's ID
     */
    public int getOrgID() {
        return this.orgID;
    }

    /**
     * Gets whether the user is an admin or not
     *
     * @return whether the user is an admin or not
     */
    public boolean getAdmin() { return this.admin; }

    /**
     * Sets the ID of the user
     *
     * @param id the ID of the user to change to
     */
    public void setUserID(int id) {
        this.userID = id;
    }

    /**
     * Sets the password the of user
     *
     * @param name the password to change to
     */
    public void setPassword(String name) {
        this.userName = name;
    }

    /**
     * Sets the organization that the users belong to
     *
     * @param id the ID number of the organization to change to
     */
    public void setOrgID(int id) {
        this.orgID = id;
    }

    /**
     * Writes the user's key details to a string format
     *
     * @return the key details of the user as a string
     */
    public String toString(){
        return "Username: " + userName + "\nUser ID: " + userID + "\nOrganization ID: " + orgID;
    }

}
