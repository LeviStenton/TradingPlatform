package Database;

import java.io.Serializable;

public class OrgDetails implements Serializable {
    private int orgID;
    private double credits;
    private String orgName;

    /**
     * The constructor for the object
     *
     * @param orgID the organization ID
     * @param credits the quantity of credits they organization possesses
     * @param orgName the name of the organization
     */
    public OrgDetails (int orgID, double credits, String orgName){
        this.orgID = orgID;
        this.credits = credits;
        this.orgName = orgName;
    }

    /**
     * Gets the ID of the organization
     *
     * @return the ID number of the organization
     */
    public int getOrgID() {
        return this.orgID;
    }

    /**
     * Gets the total number of credits belonging to the organization
     *
     * @return the number of the organization's credits
     */
    public double getCredits() {
        return this.credits;
    }

    /**
     * Gets the name of the organization
     *
     * @return the name of the organization
     */
    public String getOrgName() { return this.orgName; }

    /**
     * Sets the ID number of the organization
     *
     * @param orgID The ID to change to
     */
    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    /**
     * Sets the total credits of the organization
     *
     * @param credits the new amount of credits to change to
     */
    public void setCredits(double credits) {
        this.credits = credits;
    }

    /**
     * Sets the name of the organization
     *
     * @param orgName the name of the organization to change to
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
