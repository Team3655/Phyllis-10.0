package com.st.myst25app;

import java.io.IOException;

import com.st.myst25app.view.NfcTagController;
import com.st.st25pc.model.readers.RFGenericReader;
import com.st.st25pc.model.readers.st.STReader;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This is an example of a very simple PC application working with a CR95HF or ST25R3911B evaluation kit.
 *
 */
public class MainApp extends Application {

    private RFGenericReader mActiveRFReader = null;
    private Stage mPrimaryStage;
    private StringProperty mReaderStatus = new SimpleStringProperty();

    @Override
    public void start(Stage primaryStage) {
        mPrimaryStage = primaryStage;
        mPrimaryStage.setTitle("MyST25App");

        initStage();

        // Scan for USB reader and open device
        scanForReaders();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the NfcTagView Stage
     */
    public void initStage() {
        try {
            // Load root layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NfcTagView.fxml"));
            AnchorPane rootLayout = loader.load();

            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            mPrimaryStage.setScene(scene);

            // Give the controller access to the main app
            NfcTagController mNfcTagController = loader.getController();
            mNfcTagController.setMainApp(this);

            // Display Stage on screen
            mPrimaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scan USB ports for readers
     */
    public void scanForReaders() {

        // Try to instantiate a STReader (CR95HF or ST25R3911B-DISCO) to determine if one is connected
        STReader stReader = new STReader();

        if (stReader.connect()) {
            // Now able to communicate with the reader
            mActiveRFReader = stReader;
        }

        if (mActiveRFReader != null) {
            setReaderStatus(stReader.getName() + " is connected");
        } else {
            setReaderStatus("No reader is connected");
        }
    }

    /**
     * @return the active RF Reader
     */
    public RFGenericReader getActiveRFReader() {
        return mActiveRFReader;
    }

    public final StringProperty readerStatusProperty() {
        return mReaderStatus;
    }

    public final String getReaderStatus() {
        return readerStatusProperty().get();
    }

    public final void setReaderStatus(final String mReaderStatus) {
        readerStatusProperty().set(mReaderStatus);
    }

}
