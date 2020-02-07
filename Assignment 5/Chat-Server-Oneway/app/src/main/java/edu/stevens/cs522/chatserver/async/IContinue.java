package edu.stevens.cs522.chatserver.async;

import android.net.Uri;

import java.net.UnknownHostException;
import java.text.ParseException;

import static android.R.attr.value;

/**
 * Created by dduggan.
 */

public interface IContinue<T> {
    public void kontinue(T value) throws ParseException, UnknownHostException;

}

