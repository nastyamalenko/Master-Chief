package org.masterchief.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.masterchief.R;
import org.masterchief.RecipeActivity;
import org.masterchief.database.MasterChiefDBHelper;
import org.masterchief.database.MasterChiefRecipe;
import org.masterchief.model.Recipe;
import org.masterchief.model.adapter.RecipeAdapter;
import org.masterchief.widget.MasterChiefWidget;

import java.util.ArrayList;
import java.util.List;

import static org.masterchief.RecipeActivity.EXTRA_RECIPE_ID;

public class FragmentFavoriteRecipes extends BaseFragment {


    private static final String[] projection = {
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE
    };

    private ListView recipesListView;
    private MasterChiefDBHelper dbHelper;

    private RecipeAdapter recipeAdapter;

    public FragmentFavoriteRecipes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new MasterChiefDBHelper(getContext());

        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    //TODO: implement LoadDataTask
    public void loadData() {
        List<Recipe> recipes = new ArrayList<>();
        recipesListView = (ListView) getActivity().findViewById(R.id.recipes_list);
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor c = db.query(MasterChiefRecipe.RecipeEntry.TABLE_NAME,  // The table to query
                     projection,                               // The columns to return
                     null,                                // The columns for the WHERE clause
                     null,                            // The values for the WHERE clause
                     null,                                     // don't group the rows
                     null,                                     // don't filter by row groups
                     null                                 // The sort order
             )) {
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    try {
                        Recipe recipe = new Recipe();
                        recipe.setId(c.getString(0));
                        recipe.setName(c.getBlob(1));
                        recipe.setComplexity(c.getInt(2));
                        recipe.setCookingTimeInMinutes(c.getInt(3));
                        recipe.setImage(c.getBlob(4));
                        recipes.add(recipe);
                    } catch (Exception e) {
                        Log.e("ERROR", e.toString());
                    }
                    c.moveToNext();
                }
            }
        }

        recipeAdapter = new RecipeAdapter(getContext(), recipes);
        recipesListView.setAdapter(recipeAdapter);
        recipesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_delete).setTitle("Видалення")
                        .setMessage("Дійсно бажаєте видалити?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(String.valueOf(recipeAdapter.getItemId(position)));
                                recipeAdapter.getRecipes().remove(position);
                                recipeAdapter.notifyDataSetChanged();
                                MasterChiefWidget.updateWidget(getContext());

                            }
                        }).setNegativeButton(R.string.no, null).show();

                return true;
            }
        });
        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), RecipeActivity.class);
                intent.putExtra(EXTRA_RECIPE_ID, String.valueOf(recipeAdapter.getItemId(position)));
                startActivity(intent);
            }
        });
    }

    private void deleteItem(String itemId) {
        String selection = MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID + " LIKE ?";
        // Specify arguments in placeholder order.


        String[] selectionArgs = {String.valueOf(itemId)};
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(MasterChiefRecipe.RecipeEntry.TABLE_NAME, selection, selectionArgs);
        }


    }
}
