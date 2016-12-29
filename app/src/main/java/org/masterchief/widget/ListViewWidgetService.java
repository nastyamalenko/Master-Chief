package org.masterchief.widget;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.masterchief.R;
import org.masterchief.database.MasterChiefDBHelper;
import org.masterchief.database.MasterChiefRecipe;
import org.masterchief.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RecipeItemRemoteViewsService(this.getApplicationContext(), intent);
    }

}

class RecipeItemRemoteViewsService implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Recipe> recipes = new ArrayList<>();

    public RecipeItemRemoteViewsService(Context mContext, Intent intent) {
        this.mContext = mContext;

    }

    @Override
    public void onCreate() {
        MasterChiefDBHelper dbHelper = new MasterChiefDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID,
                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME,
                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY,
                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME,
                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE
        };

        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME + " DESC";

        try (Cursor c = db.query(MasterChiefRecipe.RecipeEntry.TABLE_NAME,  // The table to query
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


    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Recipe recipe = recipes.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_item);
        rv.setTextViewText(R.id.recipe_item_id, recipe.getId());
        rv.setTextViewText(R.id.recipe_item_name, new String(recipe.getName()));
        rv.setTextViewText(R.id.recipe_item_time, String.valueOf(recipe.getCookingTimeInMinutes()));

        rv.removeAllViews(R.id.recipe_item_stars_layout);

        for (int i = 0; i < recipe.getComplexity(); i++) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.star_image);
            rv.addView(R.id.recipe_item_stars_layout, remoteViews);

        }

        rv.setImageViewBitmap(R.id.recipe_item_image, BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(recipes.get(position).getId());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}