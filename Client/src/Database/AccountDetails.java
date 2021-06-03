package Database;

import java.io.Serializable;

public class AccountDetails implements Serializable {
    private int userID;
    private String userName;
    private String password;
    private int orgID;

    public AccountDetails(int userID, String userName, String password, int orgID){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.orgID = orgID;
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

}
