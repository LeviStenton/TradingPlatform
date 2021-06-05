package GUI;

import Database.Asset;
import Network.ClientSocket;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This version uses an AddressBookDataSource and its methods to retrieve data
 *
 * @author Malcolm Corney
 * @version $Id: Exp $
 *
 */
public class ListModelScreen {

    DefaultListModel listModel;
    private ClientSocket socket;

    /* BEGIN MISSING CODE */
    List<Asset> assetList = new ArrayList<Asset>();
    /* END MISSING CODE */
    public List<Asset> getAssetList(){return this.assetList;}
    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public ListModelScreen(ClientSocket socket) {
        listModel = new DefaultListModel();
        this.socket = socket;

        // add the retrieved data to the list model
        for (Asset asset : socket.getAssets()) {
            listModel.addElement(asset.getAssetName());
            assetList.add(asset);
        }
    }

    /**
     * Adds a person to the address book.
     *
     */
    public void Update() {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        listModel.clear();
        assetList.clear();
        this.socket = new ClientSocket();

        for (Asset asset : socket.getAssets()) {
            listModel.addElement(asset.getAssetName());
            assetList.add(asset);
        }


    }

    /**
     * Retrieves Person details from the model.
     *
     * @param key the name to retrieve.
     * @return the Person object related to the name.
     */
//    public Person get(Object key) {
//        return addressData.getPerson((String) key);
//    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * @return the number of names in the Address Book.
     */
    public int getSize() {
        return assetList.size();
    }
}
