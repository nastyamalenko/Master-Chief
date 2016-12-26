package org.masterchief;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.Recipe;
import org.masterchief.model.adapter.RecipeAdapter;
import org.masterchief.service.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private static final String LOGGER_TAG = RecipeActivity.class.getSimpleName();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        context = this;
        Intent intent = getIntent();

        String categoryName = intent.getStringExtra("CATEGORY_NAME");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.demo_toolbar);
        myToolbar.setTitle(categoryName);
        setSupportActionBar(myToolbar);

        String categoryId = intent.getStringExtra("CATEGORY_ID");
        final ListView recipesListView = (ListView) findViewById(R.id.recipes_list);

        RecipeService recipeService = RetrofitHelper.getRecipeService();
        Call<List<Recipe>> recipesByCategoryId = recipeService.getRecipesByCategoryId(Long.valueOf(categoryId));
        recipesByCategoryId.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {

                    recipesListView.setAdapter(new RecipeAdapter(context, response.body()));
                    recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //TODO: Recipe Detail Activity

//                            View childView = recipesListView.getChildAt(position);
//                            Intent intent = new Intent(context, RecipeActivity.class);
//                            intent.putExtra("CATEGORY_ID", ((TextView) childView.findViewById(R.id.category_id)).getText());
//                            intent.putExtra("CATEGORY_NAME", ((TextView) childView.findViewById(R.id.category_name)).getText());
//                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(LOGGER_TAG, t.getMessage());
            }
        });
    }
}
