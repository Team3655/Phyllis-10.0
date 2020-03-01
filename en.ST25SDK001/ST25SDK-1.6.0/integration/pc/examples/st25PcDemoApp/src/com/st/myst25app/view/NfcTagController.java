package com.st.myst25app.view;

import java.util.ArrayList;
import java.util.List;

import com.st.myst25app.MainApp;
import com.st.st25sdk.Helper;
import com.st.st25sdk.NFCTag.NfcTagTypes;
import com.st.st25sdk.RFReaderInterface;
import com.st.st25sdk.STException;
import com.st.st25sdk.TagHelper;
import com.st.st25sdk.TagHelper.ProductID;
import com.st.st25sdk.command.Iso15693Command;
import com.st.st25sdk.command.Iso15693Protocol;
import com.st.st25sdk.ndef.NDEFMsg;
import com.st.st25sdk.ndef.UriRecord;
import com.st.st25sdk.ndef.UriRecord.NdefUriIdCode;
import com.st.st25sdk.type5.STType5Tag;
import com.st.st25sdk.type5.Type5Tag;
import com.st.st25sdk.type5.lri.LRi1KTag;
import com.st.st25sdk.type5.lri.LRi2KTag;
import com.st.st25sdk.type5.lri.LRiS2KTag;
import com.st.st25sdk.type5.m24lr.LRiS64KTag;
import com.st.st25sdk.type5.m24lr.M24LR04KTag;
import com.st.st25sdk.type5.m24lr.M24LR16KTag;
import com.st.st25sdk.type5.m24lr.M24LR64KTag;
import com.st.st25sdk.type5.st25dv.ST25DVTag;
import com.st.st25sdk.type5.st25dv.ST25TV16KTag;
import com.st.st25sdk.type5.st25dv.ST25TV64KTag;
import com.st.st25sdk.type5.st25dvpwm.ST25DV02KW1Tag;
import com.st.st25sdk.type5.st25dvpwm.ST25DV02KW2Tag;
import com.st.st25sdk.type5.st25tv.ST25TVTag;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author STMicroelectronics
 *
 */
public class NfcTagController {

    // GUI elements
    @FXML private Button discoverButton;
    @FXML private Label tagUidLabel;
    @FXML private Label tagSizeLabel;
    @FXML private Label writeStatusLabel;
    @FXML private Label readerStatusLabel;
    @FXML private Button writeNdefButton;

    // Reference to the main application
    private MainApp mainApp;

    // List of detected tag UIDs
    private List<byte[]> uidList = new ArrayList<>();
    private Type5Tag recognizedType5Tag;

    private StringProperty firstTagUid = new SimpleStringProperty();
    private StringProperty firstTagSize = new SimpleStringProperty();

    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file is loaded.
     */
    @FXML
    private void initialize () {
        writeStatusLabel.setText("");
        readerStatusLabel.setText("No reader is connected");

        // Handle clicks on "Discover Tag" button
        discoverButton.setOnAction(event -> {
            startIso15693DiscoveryProcess();
            updateUidLabel(uidList);
            updateSizeLabel(uidList);
        });

        // Bind tag's UID label content to the value found in the first element of the inventory list
        tagUidLabel.textProperty().bind(firstTagUidProperty());

        // Bind tag's size label content to the value found in the first element of the inventory list
        tagSizeLabel.textProperty().bind(firstTagSizeProperty());

        writeNdefButton.setOnAction(event -> updateWriteStatus(writeNdefToTag()));
    }

    /**
     * setMainApp is called by the main application to give a reference of itself to the controller.
     *
     * @param mainApp reference to the MainApp class
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Bind reader connection status to a label that displays the updated value whenever there is a change
        readerStatusLabel.textProperty().bind(mainApp.readerStatusProperty());
    }

    private void startIso15693DiscoveryProcess() {
        // Empty lists of detected tags
        uidList.clear();

        if (mainApp.getActiveRFReader() == null) {
            return;
        }

        try {
            // Call reader's 15693 anti-collision algorithm
            uidList = mainApp.getActiveRFReader().getTransceiveInterface().inventory(RFReaderInterface.InventoryMode.NFC_TYPE_5);

            if (!uidList.isEmpty()) {
                resetTags();
            }
        } catch (STException e) {
            e.printStackTrace();
        }
    }

    /**
     * The Inventory implementation on the RF reader may use the anti-collision algorithm
     * and send the "Stay Quiet" command to a tag once detected.
     * resetTags() sends the "Reset to Ready" command to all tags in order to reset them to the ready state.
     *
     * @throws STException
     */
    private void resetTags() throws STException {
        // Use the Commands API to create an object containing all Iso15693 commands
        Iso15693Command cmd = new Iso15693Command(mainApp.getActiveRFReader().getTransceiveInterface(), null);

        cmd.setFlag(Iso15693Protocol.HIGH_DATA_RATE_MODE);
        cmd.resetToReady();
    }

    /**
     * Updates tagUidLabel with the UID string of the first element in the inventory list.
     * This function first reverses the byte array containing the UID then converts it into a String.
     */
    private void updateUidLabel(List<byte[]> myList) {
        if (myList.isEmpty()) {
            setFirstTagUid("No ISO15693/Type5 tag discovered");
        } else {
            byte[] uid = Helper.reverseByteArray(myList.get(0));
            String tagName = "";

            try {
                recognizedType5Tag = identifyType5Tag(uid);
            } catch (STException e) {
                tagName = "Unknown";
            }

            if (recognizedType5Tag != null) {
                tagName = recognizedType5Tag.getName();
            }

            setFirstTagUid(Helper.convertByteArrayToHexString(Helper.reverseByteArray(myList.get(0))) + " - " + tagName);
        }
    }

    /**
     * Updates tagSizeLabel with Size of the first element in the inventory list
     */
    private void updateSizeLabel(List<byte[]> myList) {
        if (myList.isEmpty()) {
            setFirstTagSize("No tag discovered");
        } else {
            try {
                if (recognizedType5Tag != null) {
                    setFirstTagSize(String.valueOf(recognizedType5Tag.getMemSizeInBytes() * 8) + " bits");
                } else {
                    setFirstTagSize("Tag could not be recognized");
                }

            } catch (STException e) {
                setFirstTagSize("Memory size data could not be extracted");
            }
        }
    }

    /**
     * identifyType5Tag detects the tag type given an uid value
     *
     * @param uid Value of the unique identifier for the tag to identify
     * @return Instance of a Type5Tag if identified, or null if no tag was recognized
     * @throws STException
     */
    private Type5Tag identifyType5Tag(byte[] uid) throws STException {

        /* The reader interface is extracted from the reader's implementation
         * as we later need to pass it as a parameter to the Tags constructor.
         * This allows the st25sdk library to function on any reader that
         * provides an implementation to the RFReaderInterface
         */
        RFReaderInterface readerInterface = mainApp.getActiveRFReader().getTransceiveInterface();

        // You can also make direct calls to the API of the reader's RF implementation
        NfcTagTypes tagType = readerInterface.decodeTagType(uid);

        recognizedType5Tag = null;

        // Use a method from the TagHelper class to determine the tag's name
        ProductID productName;
        if (tagType == NfcTagTypes.NFC_TAG_TYPE_V) {
            productName = TagHelper.identifyTypeVProduct(readerInterface, uid);
        } else {
            productName = ProductID.PRODUCT_UNKNOWN;
        }

        // Only a short selection of tags is represented in this sample app.
        // Add values from ProductID as needed.
        switch (productName) {
            /************** TYPE 5 PRODUCTS *************/
            case PRODUCT_ST_LRiS64K:
                recognizedType5Tag = new LRiS64KTag(readerInterface, uid);
                break;
            case PRODUCT_ST_M24LR04E_R:
                recognizedType5Tag = new M24LR04KTag(readerInterface, uid);
                break;
            case PRODUCT_ST_M24LR16E_R:
                recognizedType5Tag = new M24LR16KTag(readerInterface, uid);
                break;
            case PRODUCT_ST_M24LR64_R:
            case PRODUCT_ST_M24LR64E_R:
                recognizedType5Tag = new M24LR64KTag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;

            case PRODUCT_ST_ST25DV04K_I:
            case PRODUCT_ST_ST25DV04K_J:
            case PRODUCT_ST_ST25DV16K_I:
            case PRODUCT_ST_ST25DV16K_J:
            case PRODUCT_ST_ST25DV64K_I:
            case PRODUCT_ST_ST25DV64K_J:
                recognizedType5Tag = new ST25DVTag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;

            case PRODUCT_ST_ST25TV512:
            case PRODUCT_ST_ST25TV02K:
                recognizedType5Tag = new ST25TVTag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;

            case PRODUCT_ST_ST25TV16K:
                recognizedType5Tag = new ST25TV16KTag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;

            case PRODUCT_ST_ST25TV64K:
                recognizedType5Tag = new ST25TV64KTag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;

            case PRODUCT_ST_ST25DV02K_W1:
                recognizedType5Tag = new ST25DV02KW1Tag(readerInterface, uid);
                break;
            case PRODUCT_ST_ST25DV02K_W2:
                recognizedType5Tag = new ST25DV02KW2Tag(readerInterface, uid);
                break;

                /* LEGACY PRODUCTS */
            case PRODUCT_ST_LRi1K:
                recognizedType5Tag = new LRi1KTag(readerInterface, uid);
                break;
            case PRODUCT_ST_LRi2K:
                recognizedType5Tag = new LRi2KTag(readerInterface, uid);
                break;
            case PRODUCT_ST_LRiS2K:
                recognizedType5Tag = new LRiS2KTag(readerInterface, uid);
                break;

                /* GENERIC PRODUCTS */
            case PRODUCT_GENERIC_TYPE5_AND_ISO15693:
                // Non ST or unrecognized Type 5 products
                recognizedType5Tag = new STType5Tag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;
            case PRODUCT_GENERIC_TYPE5:
            default:
                // Non ST or unrecognized Type 5 products
                recognizedType5Tag = new Type5Tag(readerInterface, uid);
                recognizedType5Tag.setName(productName.toString());
                break;
        }

        return recognizedType5Tag;
    }

    private void updateWriteStatus(boolean success) {
        if (success) {
            writeStatusLabel.setText("Write successful");
        } else {
            writeStatusLabel.setText("Write failed");
        }
    }

    /**
     * Creates a NDEF message containing a single NDEF URI record then writes it in the tag's memory
     *
     * @return false for write failure, true for success
     */
    private boolean writeNdefToTag() {
        if (recognizedType5Tag != null) {
            // Create a new NDEF Uri record then store it in a NDEF message
            UriRecord uri = new UriRecord(NdefUriIdCode.NDEF_RTD_URI_ID_HTTP_WWW, "st.com/st25");
            NDEFMsg ndef = new NDEFMsg(uri);

            // Write the NDEF message in the tag's EEPROM
            try {
                recognizedType5Tag.writeNdefMessage(ndef);
            } catch (STException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    // JavaFX properties + setters
    private final StringProperty firstTagUidProperty() {
        return firstTagUid;
    }

    private final void setFirstTagUid(final String firstTagUid) {
        firstTagUidProperty().set(firstTagUid);
    }

    private final StringProperty firstTagSizeProperty() {
        return firstTagSize;
    }

    private final void setFirstTagSize(final String firstTagSize) {
        firstTagSizeProperty().set(firstTagSize);
    }
}
