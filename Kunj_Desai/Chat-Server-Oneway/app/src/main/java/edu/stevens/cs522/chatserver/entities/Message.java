package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chatserver.contracts.MessageContract;


public class Message implements Parcelable, Persistable {

    public long id;

    public String messageText;

    public Date timestamp;

    public String sender;

    public long senderId;

    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public Message(String messageText, Date timestamp, String sender, long senderId) {
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.sender = sender;
        this.senderId = senderId;
    }

    public Message() {
    }

    public Message(Cursor cursor) throws ParseException {
        this.messageText = MessageContract.getMessageText(cursor);
        this.timestamp =  format.parse(MessageContract.getTimestamp(cursor));
        this.sender = MessageContract.getSender(cursor);
        this.senderId = Long.parseLong(MessageContract.getSenderId(cursor));
    }

    public Message(Parcel in) {
        // TODO
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        MessageContract.putMessageText(out,messageText);
        MessageContract.putTimestamp(out,timestamp.toString());
        MessageContract.putSender(out,sender);
        MessageContract.putSenderId(out,Long.toString(senderId));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO
        dest.writeString(messageText);
        DateUtils.writeDate(dest,timestamp);
        dest.writeString(sender);
        dest.writeString(Long.toString(senderId));
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {

        @Override
        public Message createFromParcel(Parcel source) {
            // TODO
            return null;
        }

        @Override
        public Message[] newArray(int size) {
            // TODO
            return null;
        }

    };

}

