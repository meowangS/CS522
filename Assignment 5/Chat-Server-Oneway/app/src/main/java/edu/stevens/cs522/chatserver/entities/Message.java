package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.chatserver.contracts.MessageContract;

public class Message implements Parcelable, Persistable {

    public long id;

    public String messageText;

    public Date timestamp;

    public String sender;

    public long senderId;

    public Message(Cursor cursor) {
    }
    public Message() {
    }


    public Message(Parcel in) {
        // TODO
        messageText = in.readString();
        timestamp = DateUtils.readDate(in);
        sender = in.readString();
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
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            // TODO
            return new Message[size];
        }

    };


}

