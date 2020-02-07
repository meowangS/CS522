package edu.stevens.cs522.chatserver.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;

import edu.stevens.cs522.chatserver.contracts.BaseContract;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;

public class ChatProvider extends ContentProvider {

    public ChatProvider() {
    }

    private static final String AUTHORITY = BaseContract.AUTHORITY;

    private static final String MESSAGE_CONTENT_PATH = MessageContract.CONTENT_PATH;

    private static final String MESSAGE_CONTENT_PATH_ITEM = MessageContract.CONTENT_PATH_ITEM;

    private static final String PEER_CONTENT_PATH = PeerContract.CONTENT_PATH;

    private static final String PEER_CONTENT_PATH_ITEM = PeerContract.CONTENT_PATH_ITEM;


    private static final String DATABASE_NAME = "chat.db";

    private static final int DATABASE_VERSION = 1;

    private static final String MESSAGES_TABLE = "messages";

    private static final String PEERS_TABLE = "peers";

    // Create the constants used to differentiate between the different URI  requests.
    private static final int MESSAGES_ALL_ROWS = 1;
    private static  final int MESSAGES_SINGLE_ROW = 2;
    private static final int PEERS_ALL_ROWS = 3;
    private static final int PEERS_SINGLE_ROW = 4;

    private SQLiteDatabase db;

    public static class DbHelper extends SQLiteOpenHelper {

        public final  String createTablePeer = " CREATE TABLE " + PEERS_TABLE + "( "+PeerContract.PEER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +PeerContract.PEER_NAME+" TEXT NOT NULL UNIQUE, "
                +PeerContract.PEER_TIMESTAMP + " DATE, "
                +PeerContract.PEER_ADDRESS+ " TEXT,"
                +PeerContract.PEER_PORT+ " TEXT )";


        public final String createTableMessage = "CREATE TABLE " + MESSAGES_TABLE + "( "+MessageContract.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MessageContract.MESSAGE_TEXT +" TEXT, "
                + MessageContract.TIMESTAMP + " DATE, "
                + MessageContract.SENDER +" TEXT, "
                + MessageContract.SENDER_ID + " INTEGER,"
                + " FOREIGN KEY ("+ MessageContract.SENDER_ID +") REFERENCES "+ PEERS_TABLE+ "("+ PeerContract.PEER_ID +
                ")ON DELETE CASCADE)";

        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO initialize database tables
            db.execSQL(createTablePeer);
            db.execSQL(createTableMessage);
            db.execSQL("PRAGMA	foreign_keys=ON;");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO upgrade database if necessary
            db.execSQL("DROP TABLE IF EXISTS "+MESSAGES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+PEERS_TABLE);
            onCreate(db);
        }
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        // Initialize your content provider on startup.
        dbHelper = new DbHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        return true;
    }

    // Used to dispatch operation based on URI
    private static final UriMatcher uriMatcher;

    // uriMatcher.addURI(AUTHORITY, CONTENT_PATH, OPCODE)
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MESSAGE_CONTENT_PATH, MESSAGES_ALL_ROWS);
        uriMatcher.addURI(AUTHORITY, MESSAGE_CONTENT_PATH_ITEM, MESSAGES_SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, PEER_CONTENT_PATH, PEERS_ALL_ROWS);
        uriMatcher.addURI(AUTHORITY, PEER_CONTENT_PATH_ITEM, PEERS_SINGLE_ROW);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                // TODO: Implement this to handle query of all messages.
            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle query of all peers.
            case MESSAGES_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific message.
                throw new UnsupportedOperationException("Not yet implemented");
            case PEERS_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific peer.
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                // TODO: Implement this to handle requests to insert a new message.
                // Make sure to notify any observers
                long res = db.insert(MESSAGES_TABLE,null,values);
                if(res>0){
                Uri uriMessage = MessageContract.CONTENT_URI(res);
                ContentResolver cr = getContext().getContentResolver();
                cr.notifyChange(uri,null);
                return uriMessage;
            }

            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle requests to insert a new peer.
                // Make sure to notify any observers
                long resPeer = db.insert(PEERS_TABLE,null,values);
                Uri uriPeer = PeerContract.CONTENT_URI(resPeer);
                ContentResolver cr = getContext().getContentResolver();
                cr.notifyChange(uri, null);
                return uriPeer;

            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
            db = dbHelper.getReadableDatabase();
            Cursor cursor;
            switch (uriMatcher.match(uri)){
                case MESSAGES_ALL_ROWS:
                    cursor = db.query(MESSAGES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                    cursor.setNotificationUri(getContext().getContentResolver(),uri);
                    return cursor;
                case PEERS_ALL_ROWS:
                    cursor = db.query(PEERS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                     cursor.setNotificationUri(getContext().getContentResolver(),uri);
                    return cursor;
                case MESSAGES_SINGLE_ROW:
                    // TODO: Implement this to handle query of a specific message.
                    cursor = db.query(MESSAGES_TABLE,null,selection,null,null,null,null);
                    cursor.setNotificationUri(getContext().getContentResolver(),uri);
                    return  cursor;

                case PEERS_SINGLE_ROW:
                    throw new IllegalStateException("insert: bad case");
                default:
                    throw new IllegalStateException("insert: bad case");
            }
        }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Implement this to handle requests to update one or more rows.
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case PEERS_ALL_ROWS:
                builder.setTables(PEERS_TABLE);
                break;
            case PEERS_SINGLE_ROW:
                builder.setTables(PEERS_TABLE);
                builder.appendWhere(PeerContract.PEER_ID + " = " + PeerContract.getId(uri));
                break;
            case MESSAGES_ALL_ROWS:
                builder.setTables(MESSAGES_TABLE);
                break;
            case MESSAGES_SINGLE_ROW:
                builder.setTables(MESSAGES_TABLE);
                builder.appendWhere(MessageContract.ID + " = " + MessageContract.getId(uri));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = builder.query(db, null, selection, selectionArgs, null, null, null);
        return cursor.getCount();
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Implement this to handle requests to delete one or more rows.

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case PEERS_ALL_ROWS:
                builder.setTables(PEERS_TABLE);
                rows = db.delete(PEERS_TABLE, selection, selectionArgs);
                break;
            case PEERS_SINGLE_ROW:
                builder.setTables(PEERS_TABLE);
                selection = PeerContract.PEER_ID + " = ?";
                selectionArgs = new String[]{
                        uri.getLastPathSegment()
                };
                rows = db.delete(PEERS_TABLE, selection, selectionArgs);
                 break;
            case MESSAGES_ALL_ROWS:
                builder.setTables(MESSAGES_TABLE);
                rows = db.delete(MESSAGES_TABLE, selection, selectionArgs);
                break;
            case MESSAGES_SINGLE_ROW:
                builder.setTables(MESSAGES_TABLE);
                selection = MessageContract.ID + " = ?";
                selectionArgs = new String[]{
                        uri.getLastPathSegment()
                };
                rows = db.delete(MESSAGES_TABLE, selection, selectionArgs);
               break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if(rows > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

}
