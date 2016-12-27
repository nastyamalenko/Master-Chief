package org.masterchief.model.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.masterchief.model.CookingStep;
import org.masterchief.model.Ingredient;

import java.util.List;

public class CookingStepAdapter extends BaseAdapter {

    private Context mContext;

    private List<CookingStep> cookingSteps;

    public CookingStepAdapter(Context context, List<CookingStep> cookingSteps) {
        this.mContext = context;
        this.cookingSteps = cookingSteps;

    }

    @Override
    public int getCount() {
        return cookingSteps.size();
    }

    @Override
    public Object getItem(int position) {
        return cookingSteps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cookingSteps.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        tv.setText(new String(cookingSteps.get(position).getName()));
        return tv;
    }
}
