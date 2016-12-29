package org.masterchief.database;


import android.provider.BaseColumns;

public class MasterChiefRecipe {

    public MasterChiefRecipe() {
    }

    public abstract static class RecipeEntry implements BaseColumns {


        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_COMPLEXITY = "complexity";
        public static final String COLUMN_NAME_COOKING_TIME = "cooking_time";
        public static final String COLUMN_NAME_IMAGE = "image";


    }
}
