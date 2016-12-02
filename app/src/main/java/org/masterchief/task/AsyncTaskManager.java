package org.masterchief.task;

//import android.widget.ProgressDialog;
import android.widget.ProgressBar;

public class AsyncTaskManager implements IProgressTracker {

    private final OnTaskCompleteListener mTaskCompleteListener;
    private final ProgressBar mProgressDialog;
    private LogonTask mAsyncTask;
    int i =0;

    public AsyncTaskManager(ProgressBar progressDialog, OnTaskCompleteListener taskCompleteListener) {
        // Save reference to complete listener (activity)
        mTaskCompleteListener = taskCompleteListener;
        // Setup progress dialog
        mProgressDialog = progressDialog;
    }

    public void setupTask(LogonTask asyncTask) {
        // Keep task
        mAsyncTask = asyncTask;
        // Wire task to tracker (this)
        mAsyncTask.setProgressTracker(this);
        // Start task
        mAsyncTask.execute();
    }

    @Override
    public void onProgress(String message) {
        mProgressDialog.setProgress(Integer.valueOf(message));
    }

    @Override
    public void onComplete() {
        mTaskCompleteListener.onTaskComplete(mAsyncTask);
        // Reset task
        mAsyncTask = null;
    }

    public Object retainTask() {
        // Detach task from tracker (this) before retain
        if (mAsyncTask != null) {
            mAsyncTask.setProgressTracker(null);
        }
        // Retain task
        return mAsyncTask;
    }

    public void handleRetainedTask(Object instance) {
        // Restore retained task and attach it to tracker (this)
        if (instance instanceof LogonTask) {
            mAsyncTask = (LogonTask) instance;
            mAsyncTask.setProgressTracker(this);
        }
    }

    public boolean isWorking() {
        // Track current status
        return mAsyncTask != null;
    }
}