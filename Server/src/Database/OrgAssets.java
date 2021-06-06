package Database;

import java.io.Serializable;

public class OrgAssets implements Serializable {

    private int orgID;
    private int assetID;
    private double quantity;

    /**
     * The constructor for the OrgAssets object
     *
     * @param orgID The ID number of the organization
     * @param assetID The ID number of the asset
     * @param quantity The quantity number of the asset
     */
    public OrgAssets (int orgID, int assetID, double quantity){
        this.orgID = orgID;
        this.assetID = assetID;
        this.quantity = quantity;
    }

    /**
     * Gets the organization ID
     *
     * @return The organization ID
     */
    public int getOrgID() {
        return this.orgID;
    }

    /**
     * Gets the asset ID
     *
     * @return the asset ID
     */
    public int getAssetID() {
        return this.assetID;
    }

    /**
     * Gets the quantity of the asset
     *
     * @return the quantity of the asset
     */
    public double getQuantity() { return this.quantity; }

    /**
     * Sets the organization ID
     *
     * @param orgID the organization ID to change to
     */
    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    /**
     * Sets the asset ID
     *
     * @param assetID the asset ID to change to
     */
    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    /**
     * Sets the quantity of the asset
     *
     * @param quantity the quantity of the asset to change to
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
