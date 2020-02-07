package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.providers.ChatProvider;

public class ViewPeerActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String PEER_KEY = "peer";
    static final private int LOADER_ID = 1;
    private TextView userName,timeStamp,address;
    private ListView viewMessages;
    private SimpleCursorAdapter messagesAdapter;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
        userName = findViewById(R.id.view_user_name);
        timeStamp = findViewById(R.id.view_timestamp);
        address = findViewById(R.id.view_address);
        viewMessages = findViewById(R.id.view_messages);
        name = peer.name;
        userName.setText(peer.name);
        timeStamp.setText(peer.timestamp.toString());
        address.setText(peer.address.toString());

        String [] from = new String[] {MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP};
        int [] to = new int[] {android.R.id.text1, android.R.id.text2};
        messagesAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,null,from,to,0);
        viewMessages.setAdapter(messagesAdapter);

        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO use a CursorLoader to initiate a query on the database
        // Filter messages with the sender id
        String selection = "(" + MessageContract.SENDER + "='" + name + "')";
        return new CursorLoader(this, MessageContract.CONTENT_URI(ChatProvider.MESSAGES_SINGLE_ROW),null,selection,null,null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO populate the UI with the result of querying the provider
        messagesAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO reset the UI when the cursor is empty
        messagesAdapter.swapCursor(null);
    }

}
