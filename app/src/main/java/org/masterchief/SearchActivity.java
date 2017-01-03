package org.masterchief;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.Dictionary;
import org.masterchief.model.Recipe;
import org.masterchief.model.adapter.ExpAdapter;
import org.masterchief.service.DictionaryService;
import org.masterchief.service.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private static final String LOGGER_TAG = SearchActivity.class.getName();

    private ExpandableListView elvMain;
    private HashMap<Dictionary, List<Dictionary>> dictionaryListHashMap;
    private RecipeService recipeService;

    public SearchActivity() {
        dictionaryListHashMap = new HashMap<>();
        recipeService = RetrofitHelper.getRecipeService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* DEMO*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    @Override
    public void loadData() {
        if (dictionaryListHashMap.isEmpty()) {
            new GetDictionaries().execute();
        }
    }

    @Override
    public String getToolbarTitle() {
        return "Пошук";
    }

    private class GetDictionaries extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final DictionaryService dictionaryService = RetrofitHelper.getDictionaryService();
            Call<List<Dictionary>> dictionaries = dictionaryService.getAllDictionary();
            try {
                for (final Dictionary dictionary : dictionaries.execute().body()) {
                    Call<List<Dictionary>> dictionaryItems = dictionaryService.getAllDictionaryItems(String.valueOf(dictionary.getId()));
                    dictionaryListHashMap.put(dictionary, dictionaryItems.execute().body());
                }
            } catch (IOException e) {
                Log.e(LOGGER_TAG, e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            final ExpAdapter expAdapter = new ExpAdapter(context, dictionaryListHashMap);
            elvMain = (ExpandableListView) findViewById(R.id.elv);
            elvMain.setAdapter(expAdapter);

            Button button = (Button) findViewById(R.id.search_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = ((EditText) findViewById(R.id.search_hint)).getText().toString();
                    List<String> foodCategoryIds = new ArrayList<String>();
                    for (Dictionary dictionary : expAdapter.getFoodCategoryCheckedStates()) {
                        foodCategoryIds.add(String.valueOf(dictionary.getId()));
                    }
                    List<String> cookingMethodIds = new ArrayList<String>();
                    for (Dictionary dictionary : expAdapter.getCookingMethodCheckedStates()) {
                        cookingMethodIds.add(String.valueOf(dictionary.getId()));
                    }
                    List<String> cuisineIds = new ArrayList<String>();
                    for (Dictionary dictionary : expAdapter.getCuisineCheckedStates()) {
                        cuisineIds.add(String.valueOf(dictionary.getId()));
                    }
                    Call<List<String>> recipes = recipeService.getRecipes(name, foodCategoryIds, cookingMethodIds, cuisineIds);
                    recipes.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            String[] ids = new String[]{};
                            if (response.body() != null) {
                                ids = response.body().toArray(ids);
                            }

                            Intent intent = new Intent(context, RecipesActivity.class);
                            intent.putExtra("RECIPES_IDS", ids);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            Log.e(LOGGER_TAG, t.getMessage());
                        }
                    });
                }
            });


        }
    }
}


