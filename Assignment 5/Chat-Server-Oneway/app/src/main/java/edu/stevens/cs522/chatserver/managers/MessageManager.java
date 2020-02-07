package edu.stevens.cs522.chatserver.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.net.UnknownHostException;
import java.text.ParseException;

import edu.stevens.cs522.chatserver.async.AsyncContentResolver;
import edu.stevens.cs522.chatserver.async.IContinue;
import edu.stevens.cs522.chatserver.async.IEntityCreator;
import edu.stevens.cs522.chatserver.async.IQueryListener;
import edu.stevens.cs522.chatserver.async.QueryBuilder;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.providers.ChatProvider;


public class MessageManager extends Manager<Message> {

    public static final int LOADER_ID = 1;
    public static final int SINGLE_MSG_LOADER_ID = 10;
    final static Uri CONTENT_URI = MessageContract.CONTENT_URI;
    private static final IEntityCreator<Message> creator = new IEntityCreator<Message>() {
        @Override
        public Message create(Cursor cursor) {
            return new Message(cursor);
        }
    };
    public MessageManager(Context context) {
        super(context,creator,LOADER_ID);
    }

    public void getAllMessagesAsync(IQueryListener<Message> listener) {
        // TODO use QueryBuilder to complete this
        QueryBuilder.executeQuery(
                tag,
                (Activity) context,
                MessageContract.CONTENT_URI, LOADER_ID,
                new IEntityCreator<Message>() {
                    @Override
                    public Message create(Cursor cursor) {
                        return null;
                    }
                },
                listener
        );
    }

    public void getMessagesByPeerAsync(Peer peer, IQueryListener<Message> listener) {
        // TODO use QueryBuilder to complete this
        // Remember to reset the loader!
        String name = peer.name;
        String selection="(" + MessageContract.SENDER + "='" + name + "')";;
        QueryBuilder.executeQuery(
                tag,
                (Activity) context,
                MessageContract.CONTENT_URI,
                null,
                selection,
                null,
                null,
                SINGLE_MSG_LOADER_ID,
                new IEntityCreator<Message>() {
                    public Message create(Cursor cursor) {
                        return null;
                    }
                },
                listener
        );
    }

    public void persistAsync(final Message message) {
        // TODO use AsyncContentResolver to complete this
        ContentValues values = new ContentValues();
        message.writeToProvider(values);
        AsyncContentResolver asyncResolver = getAsyncResolver();
        asyncResolver.insertAsync(CONTENT_URI,
                values,
                new IContinue<Uri>() {
                    public void kontinue(Uri uri) throws ParseException, UnknownHostException {
                        message.id = (MessageContract.getId(uri));
                        getSyncResolver().notifyChange(uri, null);
                    }
                });
    }

}
