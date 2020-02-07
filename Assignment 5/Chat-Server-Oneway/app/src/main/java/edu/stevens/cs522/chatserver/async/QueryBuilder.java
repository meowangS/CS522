package edu.stevens.cs522.chatserver.async;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.net.URI;

import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.managers.TypedCursor;


public class QueryBuilder<T> implements LoaderManager.LoaderCallbacks<Cursor> {
    private final Context context;
    private  final Uri uri;
    private final int loaderID;

    // TODO complete the implementation of this

    private String tag;

    private String[] columns;

    private String select;

    private String[] selectArgs;

    private String order;

    private IEntityCreator<T> creator;

    private IQueryListener<T> listener;

    private QueryBuilder(String tag,
                         Context context,
                         Uri uri,
                         String[] columns,
                         String select,
                         String[] selectArgs,
                         String order,
                         int loaderID,
                         IEntityCreator<T> creator,
                         IQueryListener<T> listener) {
        // TODO
        this.tag = tag;
        this.context = context;
        this.uri = uri;
        this.columns = columns;
        this.select = select;
        this.selectArgs= selectArgs;
        this.order = order;
        this.loaderID = loaderID;
        this.creator=creator;
        this.listener = listener;
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        String[] columns,
                                        String select,
                                        String[] selectArgs,
                                        String order,
                                        int loaderID,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, columns, select, selectArgs, order,
                loaderID, creator, listener);

        LoaderManager lm = context.getLoaderManager();

        lm.initLoader(loaderID, null, qb);
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        int loaderID,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {

        executeQuery(tag, context, uri, null, null, null, null, loaderID, creator, listener);
    }

    public static <T> void reexecuteQuery(String tag,
                                          Activity context,
                                          Uri uri,
                                          String[] columns,
                                          String select,
                                          String[] selectArgs,
                                          String order,
                                          int loaderID,
                                          IEntityCreator<T> creator,
                                          IQueryListener<T> listener) {

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, columns, select, selectArgs, order,
                loaderID, creator, listener);

        LoaderManager lm = context.getLoaderManager();

        lm.restartLoader(loaderID, null, qb);
    }

    public static <T> void reexecuteQuery(String tag,
                                          Activity context,
                                          Uri uri,
                                          int loaderID,
                                          IEntityCreator<T> creator,
                                          IQueryListener<T> listener) {

        reexecuteQuery(tag, context, uri, null, null, null, null, loaderID, creator, listener);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == loaderID) {
            String[] projection = null;
            switch (id){
                case PeerContract.CURSOR_LOADER_ID:
                    projection = new String[]{
                            PeerContract.PEER_ID,
                            PeerContract.PEER_NAME,
                            PeerContract.PEER_TIMESTAMP,
                            PeerContract.PEER_ADDRESS,
                            PeerContract.PEER_PORT
                    };
                    break;
                case MessageContract.CURSOR_LOADER_ID:
                    projection = new String[]{
                            MessageContract.ID,
                            MessageContract.MESSAGE_TEXT,
                            MessageContract.TIMESTAMP,
                            MessageContract.SENDER,
                    };
                    break;
                    case MessageContract.SINGLE_MSG_LOADER_ID:
                        projection = new String[]{
                                MessageContract.ID,
                                MessageContract.MESSAGE_TEXT,
                                MessageContract.TIMESTAMP,
                                MessageContract.SENDER,
                        };
                        return new CursorLoader(context,
                                uri,
                                projection,
                                select,
                                null,
                                null);
                default:
                    throw new IllegalArgumentException("Unexpected loader id: " + id);
            }
            return new CursorLoader(context,
                    uri,
                    projection,
                    null,
                    null,
                    null);
        }
        throw new IllegalArgumentException("Unexpected loader id: " + id);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO
        //data.setNotificationUri(context.getContentResolver(), uri);
        if (loader.getId() == loaderID) {
            listener.handleResults(new TypedCursor<T>(data, creator));
        } else {
            throw new IllegalStateException("Unexpected loader callback");
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO
        if (loader.getId() == loaderID) {
            listener.closeResults();
        } else {
            throw new IllegalStateException("Unexpected loader callback");
        }

    }
}
