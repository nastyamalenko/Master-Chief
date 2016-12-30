package org.masterchief.model.adapter;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.masterchief.R;
import org.masterchief.model.Recipe;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private Context mContext;


    private List<Recipe> recipes;


    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.mContext = context;
        this.recipes = recipes;

    }


    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(recipes.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.recipe_item, parent, false);
        } else {
            grid = convertView;
        }

        Recipe recipe = recipes.get(position);

        if (recipe.getImage() != null) {
            ImageView imageView = (ImageView) grid.findViewById(R.id.recipe_item_image);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        }

        TextView recipeItemId = (TextView) grid.findViewById(R.id.recipe_item_id);
        recipeItemId.setText(recipe.getId());

        View layout = grid.findViewById(R.id.recipe_item_layout);

        TextView recipeItemName = (TextView) layout.findViewById(R.id.recipe_item_name);
        recipeItemName.setText(new String(recipe.getName()));


        View recipeItemTimeLayout = layout.findViewById(R.id.recipe_item_time_layout);
        TextView recipeItemTime = (TextView) recipeItemTimeLayout.findViewById(R.id.recipe_item_time);
        recipeItemTime.setText(String.valueOf(recipe.getCookingTimeInMinutes()));


        LinearLayout linearLayoutComplexity = (LinearLayout) layout.findViewById(R.id.recipe_item_stars_layout);
        linearLayoutComplexity.removeAllViews();
        for (int i = 0; i < recipe.getComplexity(); i++) {
            ImageView complexityStar = new ImageView(mContext);
            complexityStar.setImageResource(R.drawable.ic_star_grey_600_18dp);
            linearLayoutComplexity.addView(complexityStar);
        }
        return grid;
    }


    public List<Recipe> getRecipes() {
        return recipes;
    }
}
