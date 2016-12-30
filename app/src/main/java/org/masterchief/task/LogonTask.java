package org.masterchief.task;

import android.content.res.Resources;
import android.os.AsyncTask;

public class LogonTask extends AsyncTask<Void, String, Boolean> {

//    protected final Resources mResources;

    private Boolean mResult;
    private String mProgressMessage;
    private IProgressTracker mProgressTracker;

    /* UI Thread */
    public LogonTask(Resources resources) {
        mProgressMessage = "0";
    }

    /* UI Thread */
    public void setProgressTracker(IProgressTracker progressTracker) {
        // Attach to progress tracker
        mProgressTracker = progressTracker;
        // Initialise progress tracker with current task state
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
            if (mResult != null) {
                mProgressTracker.onComplete();
            }
        }
    }

    /* UI Thread */
    @Override
    protected void onCancelled() {
        // Detach from progress tracker
        mProgressTracker = null;
    }

    /* UI Thread */
    @Override
    protected void onProgressUpdate(String... values) {
        // Update progress message
        mProgressMessage = values[0];
        // And send it to progress tracker
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
        }
    }

    /* UI Thread */
    @Override
    protected void onPostExecute(Boolean result) {
        // Update result
        mResult = result;
        // And send it to progress tracker
        if (mProgressTracker != null) {
            mProgressTracker.onComplete();
        }
        // Detach from progress tracker
        mProgressTracker = null;
    }

    /* Separate Thread */
    @Override
    protected Boolean doInBackground(Void... arg0) {
        // Working in separate thread
        for (int i = 0; i <100; )
        {
            i=i+20;
            // Check if task is cancelled
            if (isCancelled()) {
                // This return causes onPostExecute call on UI thread
                return false;
            }

            try {
                // This call causes onProgressUpdate call on UI thread
                publishProgress(""+i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // This return causes onPostExecute call on UI thread
                return false;
            }
        }
        // This return causes onPostExecute call on UI thread
        return true;
    }
}
