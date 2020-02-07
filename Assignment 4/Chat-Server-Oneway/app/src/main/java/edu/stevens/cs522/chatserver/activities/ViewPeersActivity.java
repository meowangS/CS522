package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.net.UnknownHostException;
import java.text.ParseException;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    /*
     * TODO See ChatServer for example of what to do, query peers database instead of messages database.
     */

    private SimpleCursorAdapter peerAdapter;
    ListView peerList;
    static final private int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);

        // TODO initialize peerAdapter with empty cursor (null)
        peerList = findViewById(R.id.peerList);
        String [] from = new String[] {PeerContract.PEER_NAME,PeerContract.PEER_TIMESTAMP};
        int [] to = new int[] {android.R.id.text1, android.R.id.text2};
        peerAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,null,from,to,0);
        peerList.setAdapter(peerAdapter);
        getLoaderManager().initLoader(LOADER_ID,null,this);
        peerList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
         * Clicking on a peer brings up details
         */
        Cursor cursor = peerAdapter.getCursor();
        if (cursor.moveToPosition(position)) {
            Intent intent = new Intent(this, ViewPeerActivity.class);
            Peer peer = null;
            try {
                peer = new Peer(cursor);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
            startActivity(intent);
        } else {
            throw new IllegalStateException("Unable to move to position in cursor: "+position);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO use a CursorLoader to initiate a query on the database
        return new CursorLoader(this, PeerContract.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO populate the UI with the result of querying the provider
        peerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO reset the UI when the cursor is empty
        peerAdapter.swapCursor(null);
    }

}
