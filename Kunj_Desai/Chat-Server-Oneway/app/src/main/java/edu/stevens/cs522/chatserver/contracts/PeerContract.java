package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.net.InetAddress;



public class PeerContract implements BaseColumns {

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
