package Database;

public class Assets {
    private int assetID;
    private String assetName;

    public Assets(int assetID, String assetName){
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
