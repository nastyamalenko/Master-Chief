package org.masterchief.model.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.masterchief.R;
import org.masterchief.model.Dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ExpAdapter extends BaseExpandableListAdapter {

    private List<Dict> dicts;
    private Context mContext;


    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    // Hashmap for keeping track of our checkbox check states
    private HashMap<Dictionary, List<Dictionary>> checkedStates;
//    private boolean[][] checkedState;

    public ExpAdapter(Context context, HashMap<Dictionary, List<Dictionary>> dictionaryHashMap) {
        this.mContext = context;
        dicts = new ArrayList<>(dictionaryHashMap.size());
        checkedStates = new HashMap<>(dictionaryHashMap.size());

        for (Dictionary dictionary : dictionaryHashMap.keySet()) {
            System.out.println(dictionary);
            dicts.add(new Dict(dictionary, dictionaryHashMap.get(dictionary)));
            checkedStates.put(dictionary, new ArrayList<Dictionary>());

        }
    }


    @Override
    public int getGroupCount() {
        return dicts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dicts.get(groupPosition).getItems().size();
    }

    @Override
    public Dictionary getGroup(int groupPosition) {
        return dicts.get(groupPosition).getD();
    }

    @Override
    public Dictionary getChild(int groupPosition, int childPosition) {
        return dicts.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return dicts.get(groupPosition).getD().getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return dicts.get(groupPosition).getItems().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.text_layout, null);

//             Initialize the GroupViewHolder defined at the bottom of this document
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.ddd);
            convertView.setTag(groupViewHolder);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.mGroupText.setText(new String(getGroup(groupPosition).getName()));
        groupViewHolder = null;
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_row, null);

            childViewHolder = new ChildViewHolder();


            childViewHolder.mChildText = (TextView) convertView
                    .findViewById(R.id.childname);

            childViewHolder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.check1);


            convertView.setTag(R.layout.expandable_row, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView
                    .getTag(R.layout.expandable_row);
        }

        childViewHolder.mChildText.setText(new String(getChild(mGroupPosition, mChildPosition).getName()));

		/*
         * You have to set the onCheckChangedListener to null
		 * before restoring check states because each call to
		 * "setChecked" is accompanied by a call to the
		 * onCheckChangedListener
		*/
        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

        if (checkedStates.get(getGroup(mGroupPosition)).contains(getChild(mGroupPosition, mChildPosition))) {
            childViewHolder.mCheckBox.setChecked(true);
        } else {
            childViewHolder.mCheckBox.setChecked(false);
        }


        childViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    checkedStates.get(getGroup(mGroupPosition)).add(getChild(mGroupPosition, mChildPosition));
                } else {
                    checkedStates.get(getGroup(mGroupPosition)).remove(getChild(mGroupPosition, mChildPosition));
                }
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<Dictionary> getFoodCategoryCheckedStates() {
        for (Dictionary dictionary : checkedStates.keySet()) {
            if (Dictionary.FOOD_CATEGORY.equals(dictionary.getId())) {
                return checkedStates.get(dictionary);
            }

        }
        return Collections.EMPTY_LIST;
    }

    public List<Dictionary> getCookingMethodCheckedStates() {
        for (Dictionary dictionary : checkedStates.keySet()) {
            if (Dictionary.COOKING_METHOD.equals(dictionary.getId())) {
                return checkedStates.get(dictionary);
            }

        }
        return Collections.EMPTY_LIST;
    }

    public List<Dictionary> getCuisineCheckedStates() {
        for (Dictionary dictionary : checkedStates.keySet()) {
            if (Dictionary.CUISINE.equals(dictionary.getId())) {
                return checkedStates.get(dictionary);
            }

        }
        return Collections.EMPTY_LIST;
    }

    public HashMap<Dictionary, List<Dictionary>> getCheckedStates() {
        return checkedStates;
    }

    public final class GroupViewHolder {

        TextView mGroupText;
    }

    public final class ChildViewHolder {

        TextView mChildText;
        CheckBox mCheckBox;
    }

    private class Dict {

        Dictionary d;
        List<Dictionary> items;

        public Dict(Dictionary d, List<Dictionary> items) {
            this.d = d;
            this.items = items;
        }

        public Dictionary getD() {
            return d;
        }

        public void setD(Dictionary d) {
            this.d = d;
        }

        public List<Dictionary> getItems() {
            return items;
        }

        public void setItems(List<Dictionary> items) {
            this.items = items;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(d);
            hash = 97 * hash + Objects.hashCode(items);
            return hash;
        }
    }
}
