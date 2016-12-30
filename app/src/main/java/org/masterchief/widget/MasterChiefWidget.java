package org.masterchief.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.masterchief.R;
import org.masterchief.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class MasterChiefWidget extends AppWidgetProvider {


    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";


    public static void updateWidget(Context context) {

        Intent intent = new Intent(context, MasterChiefWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MasterChiefWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);

    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        // Sets up the intent that points to the StackViewService that will
        // provide the views for this collection.
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.master_chief_widget);
        updateViews.setRemoteAdapter(R.id.favorite_recipes_list, intent);


        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.
        updateViews.setEmptyView(R.id.favorite_recipes_list, R.id.empty_view);

        // This section makes it possible for items to have individualized behavior.
        // It does this by setting up a pending intent template. Individuals items of a collection
        // cannot set up their own pending intents. Instead, the collection as a whole sets
        // up a pending intent template, and the individual items set a fillInIntent
        // to create unique behavior on an item-by-item basis.
        Intent toastIntent = new Intent(context, RecipeActivity.class);
//        // Set the action for the intent.
//        // When the user touches a particular view, it will have the effect of
//        // MasterChiefWidget

        PendingIntent toastPendingIntent = PendingIntent.getActivity(context, 0, toastIntent, 0);
        updateViews.setPendingIntentTemplate(R.id.favorite_recipes_list, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.favorite_recipes_list);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
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
//        if (intent.getAction().equals(TOAST_ACTION)) {
//            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
//        }

        super.onReceive(context, intent);
    }
}

