package edu.stevens.cs522.chatserver.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity implements AdapterView.OnItemClickListener {

    /*
     * TODO See ChatServer for example of what to do, query peers database instead of messages database.
     */
    ListView peerList;

    private ChatDbAdapter chatDbAdapter;

    private SimpleCursorAdapter peerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);


        // TODO initialize peerAdapter with result of DB query
        chatDbAdapter = new ChatDbAdapter(this);

        peerList = findViewById(R.id.peer_list);
        Cursor cursor = chatDbAdapter.fetchAllPeers();
        String [] from = new String[] {PeerContract.PEER_NAME, PeerContract.PEER_TIMESTAMP};
        int [] to = new int[] {android.R.id.text1, android.R.id.text2};
        ListAdapter listAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,cursor,from,to);
        peerList.setAdapter(listAdapter);
        peerList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
         * Clicking on a peer brings up details
         */
        Intent intent = new Intent(this, ViewPeerActivity.class);
        intent.putExtra(ViewPeerActivity.PEER_ID_KEY, id);
        startActivity(intent);
    }
}
