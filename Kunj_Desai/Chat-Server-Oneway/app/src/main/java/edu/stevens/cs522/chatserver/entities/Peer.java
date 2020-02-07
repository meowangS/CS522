package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;


public class Peer implements Parcelable, Persistable {

    // Will be database key
    public long peer_id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;

    public int port;

    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public Peer(String name, Date timestamp, InetAddress address, int port) {
        super();
        this.name = name;
        this.timestamp = timestamp;
        this.address = address;
        this.port = port;
    }

    public Peer() {
    }


    public Peer(Cursor cursor) throws ParseException, UnknownHostException {
        // TODO
        this.peer_id = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(PeerContract.PEER_ID)));
        this.name = PeerContract.getPeerName(cursor);
        this.timestamp = format.parse(cursor.getString(cursor.getColumnIndexOrThrow(PeerContract.PEER_TIMESTAMP)));
        this.address = InetAddress.getByName(cursor.getString(cursor.getColumnIndexOrThrow(PeerContract.PEER_ADDRESS)).substring(1));
    }

    public Peer(Parcel in) {
        // TODO
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        PeerContract.putPeerName(out,name);
        PeerContract.putPeerTimestamp(out,timestamp.toString());
        PeerContract.putPeerAddress(out,address.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
        out.writeString(name);
        DateUtils.writeDate(out,timestamp);
        InetAddressUtils.writeAddress(out,address);
        out.writeInt(port);
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            return null;
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            return null;
        }

    };
}
