package Database;

import java.io.Serializable;

/**
 * Holds a row from the Asset DB table
 * intended for storing the table in a temp list
 */
public class Asset implements Serializable {
    private int assetID;
    private String assetName;

    /**
     * Constructor for Asset
     * @param assetID The ID of the asset
     * @param assetName The name of the asset
     */
    public Asset(int assetID, String assetName){
        this.assetID = assetID;
        this.assetName = assetName;
    }

    /**
     * Gets the AssetID
     * @return The ID of the asset
     */
    public int getAssetID() {
        return this.assetID;
    }

    /**
     * Gets the AssetName
     * @return the name of the Asset
     */
    public String getAssetName() {
        return this.assetName;
    }

    /**
     * Sets the ID of the Asset
     * @param id the ID of the asset
     */
    public void setAssetID(int id) {
        this.assetID = id;
    }

    /**
     * Sets the name of the asset
     * @param name the asset name
     */
    public void setAssetName(String name) {
        this.assetName = name;
    }

}
