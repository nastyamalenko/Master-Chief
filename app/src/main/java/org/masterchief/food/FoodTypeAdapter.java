package org.masterchief.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.masterchief.R;

public class FoodTypeAdapter extends BaseAdapter {

    private Context mContext;
    private static final FoodType[] FOOD_TYPES = new FoodType[]{new FoodType("dessert", R.drawable.dessert), new FoodType("drink", R.drawable.drink),
            new FoodType("main_dish", R.drawable.main_dish), new FoodType("salad", R.drawable.salad), new FoodType("snack", R.drawable.snack), new FoodType("soup", R.drawable.soup)};


    public FoodTypeAdapter(Context context) {
        this.mContext = context;
    }


    private FoodType[] getFoodTypes() {
        return new FoodType[]{};
    }

    @Override
    public int getCount() {
        return FOOD_TYPES.length;
    }

    @Override
    public Object getItem(int position) {
        return FOOD_TYPES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.category_grid_cell, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imageView = (ImageView) grid.findViewById(R.id.imagepart);
        TextView textView = (TextView) grid.findViewById(R.id.textpart);
        imageView.setImageResource(FOOD_TYPES[position].getImageRef());
        textView.setText(FOOD_TYPES[position].getName());

        return grid;
    }


}
