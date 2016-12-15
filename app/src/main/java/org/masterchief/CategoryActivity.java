package org.masterchief;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridView;

import org.masterchief.food.FoodCategory;
import org.masterchief.food.FoodCategoryAdapter;
import org.masterchief.helper.RetrofitHelper;
import org.masterchief.service.CategoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

//    private AsyncTaskManager mAsyncTaskManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        context = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.demo_toolbar);
        myToolbar.setTitle(R.string.main_page);
        setSupportActionBar(myToolbar);

        final GridView gridview = (GridView) findViewById(R.id.category_grid_view);
//        gridview.setAdapter(new FoodTypeAdapter(this));

        CategoryService demoService = RetrofitHelper.getDemoService();
        final Call<List<FoodCategory>> categories = demoService.loadCategories();
        categories.enqueue(new Callback<List<FoodCategory>>() {
            @Override
            public void onResponse(Call<List<FoodCategory>> call, Response<List<FoodCategory>> response) {
                if (response.isSuccessful()) {
                    gridview.setAdapter(new FoodCategoryAdapter(context, response.body()));
                }

            }

            @Override
            public void onFailure(Call<List<FoodCategory>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_activity_menu, menu);
        return true;
    }
}
