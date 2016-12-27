package org.masterchief.model.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.masterchief.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {

    private Context mContext;

    private List<Ingredient> ingredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.mContext = context;
        this.ingredients = ingredients;

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ingredients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        tv.setText(new String(ingredients.get(position).getName()));
        return tv;
    }
}
