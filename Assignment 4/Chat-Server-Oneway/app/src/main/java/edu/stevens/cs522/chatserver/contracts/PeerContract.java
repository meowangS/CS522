package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.InetAddress;

import static edu.stevens.cs522.chatserver.contracts.BaseContract.withExtendedPath;

public class PeerContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Peer");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    // TODO define column names, getters for cursors, setters for contentvalues
    public  static final String PEER_ID = "_id";
    public static  final String PEER_NAME = "peer_name";
    public static  final String PEER_TIMESTAMP = "peer_timestamp";
    public static final  String PEER_ADDRESS = "peer_address";
    public static  final String PEER_PORT = "port";
    private static int messageTextColumn = -1;

    public static String getPeerName(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_NAME);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putPeerName(ContentValues out, String peerName) {
        out.put(PEER_NAME, peerName);
    }
    public static String getPeerTimestamp(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_TIMESTAMP);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putPeerTimestamp(ContentValues out, String peerTimeStamp) {
        out.put(PEER_TIMESTAMP, peerTimeStamp);
    }

    public static String getPeerAddress(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_ADDRESS);
        }
        return (cursor.getString(messageTextColumn)).substring(1);
    }

    public static void putPeerAddress(ContentValues out, String peerAddress) {
        out.put(PEER_ADDRESS, peerAddress);
    }

}
