package org.masterchief.food;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.masterchief.R;

import java.util.List;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategoryAdapter extends BaseAdapter {

    private Context mContext;


    private List<FoodCategory> categories;


    public FoodCategoryAdapter(Context context, List<FoodCategory> categories) {
        this.mContext = context;
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
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

        FoodCategory category = categories.get(position);

        TextView textView = (TextView) grid.findViewById(R.id.textpart);
        textView.setText(category.getName());

        ImageView imageView = (ImageView) grid.findViewById(R.id.imagepart);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length));


        return grid;
    }


}

