package org.masterchief.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.RemoteViews;

import org.masterchief.R;
import org.masterchief.database.MasterChiefDBHelper;
import org.masterchief.database.MasterChiefRecipe;
import org.masterchief.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class MasterChiefWidget extends AppWidgetProvider {

    private static MasterChiefDBHelper dbHelper;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        for (Recipe recipe : recipes) {
//            System.out.println(new String(recipe.getName()));
//        }
        // Sets up the intent that points to the StackViewService that will
        // provide the views for this collection.
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.master_chief_widget);
        updateViews.setRemoteAdapter(R.id.favorite_recipes_list, intent);


        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.
        updateViews.setEmptyView(R.id.favorite_recipes_list, R.id.empty_view);


//        final ListView recipesListView = (ListView) findViewById(R.id.recipes_list);


        appWidgetManager.updateAppWidget(appWidgetId, updateViews);


//        Intent toastIntent = new Intent(context, MasterChiefWidget.class);


    }

    private static MasterChiefDBHelper getDbHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new MasterChiefDBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
//        List<Recipe> recipes = new ArrayList<>();
//
//        // Define a projection that specifies which columns from the database
//        String[] projection = {
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID,
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME,
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY,
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME,
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE
//        };
//
//        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME + " DESC";
//
//        Cursor c = db.query(
//                MasterChiefRecipe.RecipeEntry.TABLE_NAME,  // The table to query
//                projection,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        );
//        if (c.moveToFirst()) {
//            while (!c.isAfterLast()) {
//                Recipe recipe = new Recipe();
//                recipe.setId(c.getString(0));
//                recipe.setName(c.getBlob(1));
//                recipe.setComplexity(c.getInt(2));
//                recipe.setCookingTimeInMinutes(c.getInt(3));
//                recipe.setImage(c.getBlob(4));
//                recipes.add(recipe);
//                c.moveToNext();
//            }
//        }



        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            System.out.println("appWidgetId: " + appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


//        for (int appWidgetId : appWidgetIds) {
//            RemoteViews remoteViews = updateWidgetListView(context,
//                    appWidgetId);
//            appWidgetManager.updateAppWidget(appWidgetId,
//                    remoteViews);
//        }

    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.master_chief_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, ListViewWidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.favorite_recipes_list, R.id.empty_view);
        return remoteViews;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
//        for (int appWidgetId : appWidgetIds) {
//            MasterChiefWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//        if (intent.getAction().equals(TOAST_ACTION)) {
//            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
//        }
        super.onReceive(context, intent);

    }
}

