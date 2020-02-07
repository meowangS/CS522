package edu.stevens.cs522.chatserver.async;

import edu.stevens.cs522.chatserver.managers.TypedCursor;

public interface IQueryListener<T> {

    public void handleResults(TypedCursor<T> results);

    public void closeResults();

}
