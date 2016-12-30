package org.masterchief.model.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.masterchief.R;
import org.masterchief.model.FoodCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategoryAdapter extends BaseAdapter {

    private Context mContext;


    private List<FoodCategory> categories;


    public FoodCategoryAdapter(Context context) {
        this.mContext = context;
        this.categories = new ArrayList<>();
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

        TextView categoryIdTV = (TextView) grid.findViewById(R.id.category_id);
        categoryIdTV.setText(String.valueOf(category.getId()));

        TextView categoryNameTV = (TextView) grid.findViewById(R.id.category_name);
        categoryNameTV.setText(new String(category.getName()));


        ImageView imageView = (ImageView) grid.findViewById(R.id.category_image);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length));


        return grid;
    }


    public void setData(List<FoodCategory> categories) {
        this.categories = categories;
    }
}

