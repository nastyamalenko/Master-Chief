package org.masterchief;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.masterchief.database.MasterChiefDBHelper;
import org.masterchief.database.MasterChiefRecipe;
import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.CookingStep;
import org.masterchief.model.Ingredient;
import org.masterchief.model.Recipe;
import org.masterchief.service.RecipeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends BaseActivity {

    private static final String LOGGER_TAG = RecipeActivity.class.getSimpleName();
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    @Override
    public void loadData() {

        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_recipe_inner);

//        final ImageView imageView = (ImageView) layout.findViewById(R.id.recipe_detail_image);
//        final ListView ingredientsLV = (ListView) layout.findViewById(R.id.recipe_detail_ingredients_list);
//        final ListView cookingStepsLV = (ListView) layout.findViewById(R.id.recipe_detail_cooking_steps_list);

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("RECIPE_ID");

        if (recipeId != null) {
            RecipeService recipeService = RetrofitHelper.getRecipeService();
            Call<Recipe> recipeCall = recipeService.getRecipeById(recipeId);
            recipeCall.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    recipe = response.body();
                    getSupportActionBar().setTitle(new String(recipe.getName()));
                    ImageView imageView = new ImageView(context);
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
                    imageView.setAdjustViewBounds(true);
                    layout.addView(imageView);

                    TextView iLabel = new TextView(context);
                    iLabel.setText(R.string.ingredients);
                    iLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    iLabel.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    iLabel.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
                    layout.addView(iLabel);

                    for (Ingredient ingredient : recipe.getIngredients()) {
                        TextView tv = new TextView(context);
                        tv.setText(new String(ingredient.getName()));
                        layout.addView(tv);
                    }


                    TextView csLabel = new TextView(context);
                    csLabel.setText(R.string.cooking_steps);
                    csLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    csLabel.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    iLabel.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
                    layout.addView(csLabel);

                    for (CookingStep cookingStep : recipe.getCookingSteps()) {
                        TextView tv = new TextView(context);
                        tv.setText(new String(cookingStep.getName()));
                        layout.addView(tv);
                    }
//                    ingredientsLV.setAdapter(new IngredientAdapter(context, recipe.getIngredients()));
//                    cookingStepsLV.setAdapter(new CookingStepAdapter(context, recipe.getCookingSteps()));

                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {
                    Log.e(LOGGER_TAG, t.getMessage());
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean onCreateOptionsMenu = super.onCreateOptionsMenu(menu);
        MenuItem addToFavorite = menu.findItem(R.id.action_add_favorite);
        if (addToFavorite != null) {
            addToFavorite.setVisible(true);
        }
        return onCreateOptionsMenu && true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_favorite:
                saveRecipeAsFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void saveRecipeAsFavorite() {
        MasterChiefDBHelper dbHelper = new MasterChiefDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID, recipe.getId());
        values.put(MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY, recipe.getComplexity());
        values.put(MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME, recipe.getCookingTimeInMinutes());
        values.put(MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE, recipe.getImage());

// Insert the new row, returning the primary key value of the new row
        db.insert(MasterChiefRecipe.RecipeEntry.TABLE_NAME, null, values);
    }
}
