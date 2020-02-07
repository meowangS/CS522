package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;

public class ViewPeerActivity extends Activity {

    public static final String PEER_ID_KEY = "_id";

    private ChatDbAdapter chatDbAdapter;

    private TextView usernametv, timestamptv, addresstv;

    private ListView messageListView;

    long peerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        peerId = getIntent().getLongExtra(PEER_ID_KEY, -1);
        if (peerId < 0) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }

        chatDbAdapter = new ChatDbAdapter(this);

        // TODO init the UI
        usernametv = findViewById(R.id.view_user_name);
        timestamptv = findViewById(R.id.view_timestamp);
        addresstv = findViewById(R.id.view_address);
        messageListView = findViewById(R.id.message_list);


        try {
            displayData();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    void displayData() throws UnknownHostException, ParseException {
        Peer peer = chatDbAdapter.fetchPeer(peerId);

        //String  message = chatDbAdapter.fetchMessagesFromPeer(peer);
        usernametv.setText(peer.name);
        timestamptv.setText(peer.timestamp.toString());
        addresstv.setText(peer.address.toString());

        Cursor c = chatDbAdapter.fetchMessagesFromPeer(peer);
        ArrayList<String> messages = new ArrayList<String>();
        if(c.moveToFirst())
        {
            do {
                messages.add(c.getString(c.getColumnIndexOrThrow(MessageContract.MESSAGE_TEXT)));
            }while (c.moveToNext());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, messages );
        messageListView.setAdapter(arrayAdapter);

    }

}
