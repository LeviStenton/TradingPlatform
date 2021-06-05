package Database;

import java.io.Serializable;

public class User implements Serializable {
    private int userID;
    private String userName;
    private String password;
    private int orgID;
    private boolean admin;

    public User(int userID, String userName, String password, int orgID, boolean admin){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.orgID = orgID;
        this.admin = admin;
    }

    public User(int userID, String userName, int orgID, boolean admin){
        this.userID = userID;
        this.userName = userName;
        this.password = null;
        this.orgID = orgID;
        this.admin = admin;
    }


    public int getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public int getOrgID() {
        return this.orgID;
    }

    public boolean getAdmin() { return this.admin; }

    public void setUserID(int id) {
        this.userID = id;
    }

    public void setPassword(String name) {
        this.userName = name;
    }

    public void setAssetName(String name) {
        this.password = name;
    }

    public void setOrgID(int id) {
        this.orgID = id;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public String toString(){
        return "Username: " + userName + "\nUser ID: " + userID + "\nOrganization ID: " + Integer.toString(orgID);
    }

}
