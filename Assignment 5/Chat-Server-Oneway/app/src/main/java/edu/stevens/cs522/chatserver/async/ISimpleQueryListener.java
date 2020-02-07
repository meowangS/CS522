package edu.stevens.cs522.chatserver.async;

import java.util.List;

public interface ISimpleQueryListener<T> {

    public void handleResults(List<T> results);

}