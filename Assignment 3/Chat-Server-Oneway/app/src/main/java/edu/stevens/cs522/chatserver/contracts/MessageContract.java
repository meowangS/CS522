package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Address;
import android.provider.BaseColumns;



public class MessageContract implements BaseColumns {


    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    public static final String ADDRESS = "address";
    // TODO remaining columns in Messages table

    public static  final String MESSAGE_ID = "_id";

    public static String SENDER_ID = "senderID";


    private static int messageTextColumn = -1;

    public static String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns

    public static String getId(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_ID);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putId(ContentValues out, String messageID) {
        out.put(MESSAGE_ID, messageID);
    }
    public static String getTimestamp(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putTimestamp(ContentValues out, String messageText) {
        out.put(TIMESTAMP, messageText);
    }
    public static String getSender(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putSender(ContentValues out, String messageSender) {
        out.put(SENDER, messageSender);
    }

    public static String getAddress(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(ADDRESS);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putAddress(ContentValues out, String messageAddress) {
        out.put(ADDRESS, messageAddress);
    }
    public static String getSenderId(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(SENDER_ID);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putSenderId(ContentValues out, String senderId) {
        out.put(SENDER_ID, senderId);
    }
}
