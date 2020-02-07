package edu.stevens.cs522.chatserver.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;

import static edu.stevens.cs522.chatserver.contracts.MessageContract.putMessageText;
import static edu.stevens.cs522.chatserver.contracts.MessageContract.putSender;
import static edu.stevens.cs522.chatserver.contracts.MessageContract.putTimestamp;


public class ChatDbAdapter {

    private static final String DATABASE_NAME = "messages.db";

    private static final String MESSAGE_TABLE = "messages";

    private static final String PEER_TABLE = "peers";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public Context context;



    public static class DatabaseHelper extends SQLiteOpenHelper {

        public final String createTablePeer = " CREATE TABLE " + PEER_TABLE + "( "+PeerContract.PEER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +PeerContract.PEER_NAME+" TEXT NOT NULL UNIQUE, "
                +PeerContract.PEER_TIMESTAMP + " DATE, "
                +PeerContract.PEER_ADDRESS+ " TEXT,"
                +PeerContract.PEER_PORT+ " TEXT )";


        public final String createTableMessage = "CREATE TABLE " + MESSAGE_TABLE + "( "+MessageContract.MESSAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MessageContract.MESSAGE_TEXT +" TEXT, "
                + MessageContract.TIMESTAMP + " DATE, "
                + MessageContract.SENDER +" TEXT, "
                + MessageContract.SENDER_ID + " INTEGER,"
                + " FOREIGN KEY ("+ MessageContract.SENDER_ID +") REFERENCES "+ PEER_TABLE+ "("+ PeerContract.PEER_NAME +
                "))";


        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO
            db.execSQL(createTablePeer);
            db.execSQL(createTableMessage);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO
            
        }
    }


    public ChatDbAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        // TODO
        db = dbHelper.getWritableDatabase();
    }

    public void addMessage(Message message)
    {
        ContentValues mcontentValues = new ContentValues();
        putMessageText(mcontentValues,message.messageText);
        putTimestamp(mcontentValues,message.timestamp.toString());
        putSender(mcontentValues,message.sender);
        //putAddress(mcontentValues,Long.toString(message.senderId));

        long result = db.insert(MESSAGE_TABLE,null,mcontentValues);


    }
    public void addPeer(Peer peer)
    {
        ContentValues pcontentvalues = new ContentValues();
        PeerContract.putPeerName(pcontentvalues,peer.name);
        PeerContract.putPeerTimestamp(pcontentvalues,peer.timestamp.toString());
        PeerContract.putPeerAddress(pcontentvalues,peer.address.toString());

        long result = db.insert(PEER_TABLE,null,pcontentvalues);
        if(result == -1)
        {
            return;
        }
    }
    public Cursor fetchAllMessages() {
        // TODO
        return db.query(MESSAGE_TABLE,null,null,null,null,null,null);

    }

    public Cursor fetchAllPeers() {
        // TODO
        return db.query(PEER_TABLE,null,null,null,null,null,null);
    }

    public Peer fetchPeer(long peerId) throws ParseException, UnknownHostException {
        // TODO
         Cursor c= db.rawQuery("select * from "+ PEER_TABLE +" where _id = "+peerId,null);
         c.moveToFirst();
         Peer peer = new Peer(c);
         return peer;
    }

    public Cursor fetchMessagesFromPeer(Peer peer) throws ParseException {
        // TODO
        String peerName = peer.name;
        Cursor c= db.rawQuery("select * from "+ MESSAGE_TABLE +" where "+MessageContract.SENDER+" = '"+peerName+"'",null);
        return c;
    }

    public long persist(Message message) throws SQLException {
        // TODO
        throw new IllegalStateException("Unimplemented: persist message");
    }

    /**
     * Add a peer record if it does not already exist; update information if it is already defined.
     */
    public long persist(Peer peer) throws SQLException {
        // TODO
        throw new IllegalStateException("Unimplemented: persist peer");
    }

    public void close() {
        // TODO
        db.close();
    }
}