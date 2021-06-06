package GUI;

import Database.Asset;
import Network.ClientSocket;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import java.util.Collection;
import java.util.List;

/**
 * This version uses an AddressBookDataSource and its methods to retrieve data
 *
 * @author Malcolm Corney
 * @version $Id: Exp $
 *
 */
public class ListModelScreen {

    DefaultListModel<Object> listModel;

    /* BEGIN MISSING CODE */
    /* END MISSING CODE */
    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public ListModelScreen(Collection<String> list) {
        listModel = new DefaultListModel<>();
        listModel.addAll(list);
        // add the retrieved data to the list model
//        for (Asset item : list) {
//            listModel.addElement(item.getAssetName());
//        }
    }

    /**
     * Adds a person to the address book.
     *
     */
    public void Update(Collection<String> list) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        listModel.clear();
        listModel.addAll(list);
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
    public ListModel<Object> getModel() {
        return listModel;
    }


}
