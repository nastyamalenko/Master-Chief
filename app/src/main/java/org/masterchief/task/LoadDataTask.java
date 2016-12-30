package org.masterchief.task;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class LoadDataTask<T> extends AsyncTaskLoader<T> {

    private T t;

    public LoadDataTask(Context context) {
        super(context);
        setEmptyObject();
    }

    protected void setEmptyObject() {
        this.t = getEmptyObject();
    }

    public abstract T getEmptyObject();

    @Override
    protected void onStartLoading() {
        if (t != null && !t.equals(getEmptyObject())) {
//         Use cached data
            deliverResult(t);
        } else {
//         We have no data, so kick off loading it
            forceLoad();
        }
    }

    public abstract T doInBackground();

    @Override
    public T loadInBackground() {
        return doInBackground();
    }

    @Override
    public void deliverResult(T data) {
        // We’ll save the data for later retrieval
        t = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }
}
