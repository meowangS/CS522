package edu.stevens.cs522.chatserver.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.CursorAdapter;

import java.net.UnknownHostException;
import java.text.ParseException;

import edu.stevens.cs522.chatserver.async.AsyncContentResolver;
import edu.stevens.cs522.chatserver.async.IContinue;
import edu.stevens.cs522.chatserver.async.IEntityCreator;
import edu.stevens.cs522.chatserver.async.IQueryListener;
import edu.stevens.cs522.chatserver.async.QueryBuilder;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;



public class PeerManager extends Manager<Peer> {


    public static final int LOADER_ID = 2;
    final static Uri CONTENT_URI = PeerContract.CONTENT_URI;

    private static final IEntityCreator<Peer> creator = new IEntityCreator<Peer>() {
        @Override
        public Peer create(Cursor cursor) throws ParseException, UnknownHostException {
            return new Peer(cursor);
        }
    };

    public PeerManager(Context context) {
        super(context,creator,2);
    }

    public void getAllPeersAsync(IQueryListener<Peer> listener) {
        // TODO get a list of all peers in the database
        // use QueryBuilder to complete this
        QueryBuilder.executeQuery(
                tag,
                (Activity) context,
                PeerContract.CONTENT_URI, LOADER_ID,
                new IEntityCreator<Peer>() {
                    @Override
                    public Peer create(Cursor cursor) {
                        return null;
                    }
                },
                listener
        );
    }

    public void persistAsync(final Peer peer, final IContinue<Long> callback) {
        // TODO upsert the peer into the database
        // use AsyncContentResolver to complete this
        ContentValues values = new ContentValues();
        peer.writeToProvider(values);
        AsyncContentResolver asyncResolver = getAsyncResolver();
        asyncResolver.insertAsync(PeerContract.CONTENT_URI,
                values,
                new IContinue<Uri>() {
                    public void kontinue(Uri uri) throws ParseException, UnknownHostException {
                        callback.kontinue(peer.id);
                    }
                });
    }

}
