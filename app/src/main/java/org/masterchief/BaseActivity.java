package org.masterchief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.masterchief.network.NetworkStateReceiver;
import org.masterchief.util.NetworkUtil;


public abstract class BaseActivity extends AppCompatActivity implements IDataLoad {

    protected Context context;
    private NetworkStateReceiver networkStateReceiver;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkStateReceiver = new NetworkStateReceiver();
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        configureToolbar(displayHomeButton());
        if (NetworkUtil.isConnected(this)) {
            loadData();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStateReceiver);
    }


    protected boolean useToolbar() {
        return true;
    }

    protected boolean displayHomeButton() {
        return true;
    }

    protected String getToolbarTitle() {
        return "Toolbar title may be here";
    }


    private void configureToolbar(boolean displayHomeButton) {
        toolbar = (Toolbar) findViewById(R.id.demo_toolbar);
        if (toolbar != null) {
            if (useToolbar()) {
                toolbar.setTitle(getToolbarTitle());
                setSupportActionBar(toolbar);
                if (displayHomeButton) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

            } else {
                toolbar.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
