package org.masterchief;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* DEMO*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner dishTypeSpinner = (Spinner) findViewById(R.id.dish_type_spinner);
        String[] items = new String[]{"Chai Latte", "Green Tea", "Black Tea"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dishTypeSpinner.setAdapter(adapter);

        dishTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        if (item != null)
            item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public String getToolbarTitle() {
        return "Пошук";
    }
}
