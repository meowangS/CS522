package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import edu.stevens.cs522.chatserver.async.IEntityCreator;
import edu.stevens.cs522.chatserver.entities.Message;

import static android.drm.DrmStore.DrmObjectType.CONTENT;

public class MessageContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Message");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    public static final String ID = _ID;

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    // TODO remaining columns in Messages table
    public  static final int CURSOR_LOADER_ID =1;
    public  static  final int SINGLE_MSG_LOADER_ID = 10;
    private static int messageTextColumn = -1;
    public static String SENDER_ID = "senderID";

    public static final IEntityCreator<Message> DEFAULT_ENTITY_CREATOR = new IEntityCreator<Message>() {
        @Override
        public Message create(Cursor cursor) {
            return new Message(cursor);
        }
    };
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
            messageTextColumn = cursor.getColumnIndexOrThrow(ID);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putId(ContentValues out, String messageID) {
        out.put(ID, messageID);
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

    public static String getSenderId(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageTextColumn);
    }
    public static void putSenderId(ContentValues out, String senderId) {
        out.put(SENDER_ID, senderId);
    }
}
