package Database;

import java.io.Serializable;

public class OrgAssets implements Serializable {

    private int orgID;
    private int assetID;
    private double quantity;

    public OrgAssets (int orgID, int assetID, double quantity){
        this.orgID = orgID;
        this.assetID = assetID;
        this.quantity = quantity;
    }


    public int getOrgID() {
        return this.orgID;
    }

    public int getAssetID() {
        return this.assetID;
    }

    public double getQuantity() { return this.quantity; }


    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
