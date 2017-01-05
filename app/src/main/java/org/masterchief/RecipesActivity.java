package org.masterchief;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.Recipe;
import org.masterchief.model.adapter.RecipeAdapter;
import org.masterchief.service.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static org.masterchief.RecipeActivity.EXTRA_RECIPE_ID;

public class RecipesActivity extends BaseActivity {

    private static final String LOGGER_TAG = RecipesActivity.class.getSimpleName();
    private List<Recipe> recipes;
    String categoryId;
    String[] recipesIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("CATEGORY_ID");
        recipesIds = intent.getStringArrayExtra("RECIPES_IDS");
        this.recipes = new ArrayList<>();

    }

    @Override
    public void loadData() {
        recipes.clear();
        new GetRecipes().execute();
    }

    private void setupListView(final RecipeAdapter recipeAdapter) {
        final ListView recipesListView = (ListView) findViewById(R.id.recipes_list);
        recipesListView.setAdapter(recipeAdapter);
        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(EXTRA_RECIPE_ID, String.valueOf(recipeAdapter.getItemId(position)));
                startActivity(intent);
            }
        });
    }

    @Override
    protected String getToolbarTitle() {
        Intent intent = getIntent();
        String toolbarTitle = intent.getStringExtra("TOOLBAR_TITLE");
        if (toolbarTitle != null) {
            return toolbarTitle;
        }
        return "";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = super.onCreateOptionsMenu(menu);
        MenuItem actionSearch = menu.findItem(R.id.action_search);
        if (actionSearch != null) {
            actionSearch.setVisible(true);
        }
        return b && true;
    }


    private class GetRecipes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                load();
            } catch (IOException e) {
                Log.e(LOGGER_TAG, e.getMessage());
            }
            return null;
        }

        protected void load() throws IOException {
            RecipeService recipeService = RetrofitHelper.getRecipeService();

            if (categoryId != null) {
                final Call<List<Recipe>> recipesCall = recipeService.getRecipesByCategoryId(Long.valueOf(categoryId));
                recipes = recipesCall.execute().body();
            } else if (recipesIds != null) {
                for (String recipeId : recipesIds) {
                    Call<Recipe> recipeCall = recipeService.getRecipeById(recipeId);
                    recipes.add(recipeCall.execute().body());
                }
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setupListView(new RecipeAdapter(context, recipes));
        }
    }
}
