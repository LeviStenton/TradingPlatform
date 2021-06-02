package Database;

public class OrgDetails {
    private int orgID;
    private double credits;
    private String orgName;

    public OrgDetails (int orgID, double credits, String orgName){
        this.orgID = orgID;
        this.credits = credits;
        this.orgName = orgName;
    }

    public int getOrgID() {
        return this.orgID;
    }

    public double getCredits() {
        return this.credits;
    }

    public String getOrgName() { return this.orgName; }


    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
