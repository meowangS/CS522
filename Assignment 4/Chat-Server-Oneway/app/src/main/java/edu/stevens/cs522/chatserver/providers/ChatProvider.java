package edu.stevens.cs522.chatserver.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import edu.stevens.cs522.chatserver.contracts.BaseContract;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;

public class ChatProvider extends ContentProvider {



    private static final String AUTHORITY = BaseContract.AUTHORITY;

    private static final String MESSAGE_CONTENT_PATH = MessageContract.CONTENT_PATH;

    private static final String MESSAGE_CONTENT_PATH_ITEM = MessageContract.CONTENT_PATH_ITEM;

    private static final String PEER_CONTENT_PATH = PeerContract.CONTENT_PATH;

    private static final String PEER_CONTENT_PATH_ITEM = PeerContract.CONTENT_PATH_ITEM;


    private static final String DATABASE_NAME = "chat.db";

    private static final int DATABASE_VERSION = 1;

    private static final String MESSAGES_TABLE = "messages";

    private static final String PEERS_TABLE = "peers";
    private DbHelper dbHelper;

    private SQLiteDatabase db;

    public Context context;
    // Create the constants used to differentiate between the different URI requests.
    public static final int MESSAGES_ALL_ROWS = 1;
    public static final int MESSAGES_SINGLE_ROW = 2;
    public static final int PEERS_ALL_ROWS = 3;
    public static final int PEERS_SINGLE_ROW = 4;
    public ChatProvider(){}

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
                + " FOREIGN KEY ("+ MessageContract.SENDER_ID +") REFERENCES "+ PEERS_TABLE+ "("+ PeerContract.PEER_NAME +
                "))";

        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO initialize database tables
            db.execSQL(createTablePeer);
            db.execSQL(createTableMessage);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO upgrade database if necessary
        }

    }

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
                db.insert(MESSAGES_TABLE,null,values);
                return uri;


            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle requests to insert a new peer.
                // Make sure to notify any observers
               db.insert(PEERS_TABLE,null,values);
               return uri;
            default:
                throw new IllegalStateException("insert: bad case");

        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
       db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                // TODO: Implement this to handle query of all messages.
                return db.query(MESSAGES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle query of all peers.
                return db.query(PEERS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
            case MESSAGES_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific message.
                Cursor  cursor = db.query(MESSAGES_TABLE,null,selection,null,null,null,null);
                return cursor;
            case PEERS_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific peer.
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
