package st.com.st25androiddemoapp;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.st.st25sdk.NFCTag;
import com.st.st25sdk.STException;
import com.st.st25sdk.TagHelper;
import com.st.st25sdk.ndef.NDEFMsg;
import com.st.st25sdk.ndef.UriRecord;

import static com.st.st25sdk.ndef.UriRecord.NdefUriIdCode.NDEF_RTD_URI_ID_HTTP_WWW;

public class MainActivity extends AppCompatActivity implements TagDiscovery.onTagDiscoveryCompletedListener {
    static final String TAG = "MainActivity";

    private NfcAdapter mNfcAdapter;
    private TextView mTagMemSizeTextView;

    // Last tag taped
    private NFCTag mNfcTag;

    enum Action {
        WRITE_NDEF_MESSAGE,
        READ_MEMORY_SIZE
    };

    enum ActionStatus {
        ACTION_SUCCESSFUL,
        ACTION_FAILED,
        TAG_NOT_IN_THE_FIELD
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        mTagMemSizeTextView = findViewById(R.id.tagMemSizeTextView);

        Button writeNdefMessageButton = (Button) findViewById(R.id.writeNdefMessageButton);
        writeNdefMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNfcTag != null) {
                    // All the actions doing a transceive() to communicate with the tag should be done
                    // in an Async Task to avoid disturbance of Android UI Thread
                    executeAsynchronousAction(Action.WRITE_NDEF_MESSAGE);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if if this phone has NFC hardware
        if (mNfcAdapter == null) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Warning!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("This phone doesn't have NFC hardware!")
                    .setCancelable(true)
                    .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } else {
            //Toast.makeText(this, "We are ready to play with NFC!", Toast.LENGTH_SHORT).show();

            // Give priority to the current activity when receiving NFC events (over other actvities)
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter[] nfcFilters = null;
            String[][] nfcTechLists = null;
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcFilters, nfcTechLists);
        }

        // The current activity can be resumed for several reasons (NFC tag tapped is one of them).
        // Check what was the reason which triggered the resume of current application
        Intent intent = getIntent();
        String action = intent.getAction();

        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TECH_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {

            // If the resume was triggered by an NFC event, it will contain an EXTRA_TAG providing
            // the handle of the NFC Tag
            Tag androidTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (androidTag != null) {
                // This action will be done in an Asynchronous task.
                // onTagDiscoveryCompleted() of current activity will be called when the discovery is completed.
                new TagDiscovery(this).execute(androidTag);
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // onResume() gets called after this to handle the intent
        setIntent(intent);
    }

    @Override
    public void onTagDiscoveryCompleted(NFCTag nfcTag, TagHelper.ProductID productId, STException error) {

        if (error != null) {
            Toast.makeText(getApplication(), "Error while reading the tag: " + error.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        if (nfcTag != null) {
            mNfcTag = nfcTag;

            try {

                String tagName = nfcTag.getName();
                TextView tagNameTextView = (TextView) findViewById(R.id.tagNameTextView);
                tagNameTextView.setText(tagName);

                String uidString = nfcTag.getUidString();
                TextView uidTextView = (TextView) findViewById(R.id.uidTextView);
                uidTextView.setText(uidString);

                executeAsynchronousAction(Action.READ_MEMORY_SIZE);

            } catch (STException e) {
                e.printStackTrace();
                Toast.makeText(this, "Discovery successful but failed to read the tag!", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Tag discovery failed!", Toast.LENGTH_LONG).show();
        }
    }

    private void executeAsynchronousAction(Action action) {
        Log.d(TAG, "Starting background action " + action);
        new myAsyncTask(action).execute();
    }


    private class myAsyncTask extends AsyncTask<Void, Void, ActionStatus> {
        Action mAction;
        int memSizeInBytes;

        public myAsyncTask(Action action) {
            mAction = action;
        }

        @Override
        protected ActionStatus doInBackground(Void... param) {
            ActionStatus result;

            try {
                switch (mAction) {
                    case WRITE_NDEF_MESSAGE:
                        // Create a NDEFMsg
                        NDEFMsg ndefMsg = new NDEFMsg();

                        // Create a URI record containing http://www.st.com
                        UriRecord uriRecord = new UriRecord(NDEF_RTD_URI_ID_HTTP_WWW, "st.com/st25");

                        // Add the record to the NDEFMsg
                        ndefMsg.addRecord(uriRecord);

                        // Write the NDEFMsg into the tag
                        mNfcTag.writeNdefMessage(ndefMsg);

                        // If we get to this point, it means that no STException occured so the action was successful
                        result = ActionStatus.ACTION_SUCCESSFUL;
                        break;
                    case READ_MEMORY_SIZE:
                        memSizeInBytes = mNfcTag.getMemSizeInBytes();
                        // If we get to this point, it means that no STException occured so the action was successful
                        result = ActionStatus.ACTION_SUCCESSFUL;
                        break;

                    default:
                        result = ActionStatus.ACTION_FAILED;
                        break;
                }

            } catch (STException e) {
                switch (e.getError()) {
                    case TAG_NOT_IN_THE_FIELD:
                        result = ActionStatus.TAG_NOT_IN_THE_FIELD;
                        break;

                    default:
                        e.printStackTrace();
                        result = ActionStatus.ACTION_FAILED;
                        break;
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(ActionStatus actionStatus) {

            switch(actionStatus) {
                case ACTION_SUCCESSFUL:
                    switch (mAction) {
                        case WRITE_NDEF_MESSAGE:
                            Toast.makeText(MainActivity.this, "Write successful", Toast.LENGTH_LONG).show();
                            break;
                        case READ_MEMORY_SIZE:
                            mTagMemSizeTextView.setText(String.valueOf(memSizeInBytes));
                            break;
                    }
                    break;

                case ACTION_FAILED:
                    Toast.makeText(MainActivity.this, "Action failed!", Toast.LENGTH_LONG).show();
                    break;

                case TAG_NOT_IN_THE_FIELD:
                    Toast.makeText(MainActivity.this, "Tag not in the field!", Toast.LENGTH_LONG).show();
                    break;
            }

            return;
        }
    }

}
