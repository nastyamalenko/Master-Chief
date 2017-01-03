package org.masterchief.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MasterChiefDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MasterChief.db";


    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String[] FULL_TABLE_PROJECTION = {
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME,
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE
    };

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MasterChiefRecipe.RecipeEntry.TABLE_NAME + " (" +
            MasterChiefRecipe.RecipeEntry._ID + INT_TYPE + " PRIMARY KEY," +
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_NAME + BLOB_TYPE + COMMA_SEP +
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COOKING_TIME + INT_TYPE + COMMA_SEP +
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_COMPLEXITY + INT_TYPE + COMMA_SEP +
            MasterChiefRecipe.RecipeEntry.COLUMN_NAME_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MasterChiefRecipe.RecipeEntry.TABLE_NAME;

    public MasterChiefDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}