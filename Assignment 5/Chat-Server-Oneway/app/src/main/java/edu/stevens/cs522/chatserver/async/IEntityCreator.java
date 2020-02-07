package edu.stevens.cs522.chatserver.async;

import android.database.Cursor;

import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Created by dduggan.
 */

public interface IEntityCreator<T> {

    public T create(Cursor cursor) throws ParseException, UnknownHostException;

}

