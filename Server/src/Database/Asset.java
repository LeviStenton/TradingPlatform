package Database;

import java.io.Serializable;

/**
 * This class can be used to hold the Asset DB table in a list
 */
public class Asset implements Serializable {
    private int assetID;
    private String assetName;

    public Asset(int assetID, String assetName){
        this.assetID = assetID;
        this.assetName = assetName;
    }

    /**
     * Gets the Asset ID
     * @return The asset ID
     */
    public int getAssetID() {
        return this.assetID;
    }

    /**
     * Gets the Asset Name
     * @return The asset Name
     */
    public String getAssetName() {
        return this.assetName;
    }

    /**
     * Sets the asset ID
     * @param id The new ID of the asset
     */
    public void setAssetID(int id) {
        this.assetID = id;
    }

    /**
     * Sets the asset name
     * @param name The new name of the asset
     */
    public void setAssetName(String name) {
        this.assetName = name;
    }

}
