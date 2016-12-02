package org.masterchief;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.masterchief.food.FoodTypeAdapter;
import org.masterchief.task.AsyncTaskManager;
import org.masterchief.task.LogonTask;
import org.masterchief.task.OnTaskCompleteListener;

public class CategoryActivity extends AppCompatActivity {

//    private AsyncTaskManager mAsyncTaskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.demo_toolbar);
        myToolbar.setTitle(R.string.main_page);
        setSupportActionBar(myToolbar);

        GridView gridview = (GridView) findViewById(R.id.category_grid_view);
        gridview.setAdapter(new FoodTypeAdapter(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_activity_menu, menu);
        return true;
    }
}
