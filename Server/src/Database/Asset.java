package Database;

import java.io.Serializable;

public class Asset implements Serializable {
    private int assetID;
    private String assetName;

    public Asset(int assetID, String assetName){
        this.assetID = assetID;
        this.assetName = assetName;
    }

    public int getAssetID() {
        return this.assetID;
    }

    public String getAssetName() {
        return this.assetName;
    }

    public void setAssetID(int id) {
        this.assetID = id;
    }

    public void setAssetName(String name) {
        this.assetName = name;
    }

}
