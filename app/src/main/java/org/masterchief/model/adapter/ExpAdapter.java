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
import java.util.HashMap;
import java.util.List;


public class ExpAdapter extends BaseExpandableListAdapter {

    private List<Dict> dicts;
    private Context mContext;


    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    // Hashmap for keeping track of our checkbox check states
    private HashMap<Integer, boolean[]> mChildCheckStates;

    public ExpAdapter(Context context, HashMap<Dictionary, List<Dictionary>> dictionaryHashMap) {
        this.mContext = context;
        dicts = new ArrayList<>(dictionaryHashMap.size());
        for (Dictionary dictionary : dictionaryHashMap.keySet()) {
            dicts.add(new Dict(dictionary, dictionaryHashMap.get(dictionary)));
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
    public Object getGroup(int groupPosition) {
        return dicts.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
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
        return false;
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
        groupViewHolder.mGroupText.setText(new String(((Dict) getGroup(groupPosition)).getD().getName()));

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

        childViewHolder.mChildText.setText(new String(((Dictionary)getChild(groupPosition, childPosition)).getName()));

		/*
         * You have to set the onCheckChangedListener to null
		 * before restoring check states because each call to
		 * "setChecked" is accompanied by a call to the
		 * onCheckChangedListener
		*/
        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

        if (mChildCheckStates.containsKey(mGroupPosition)) {
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

        } else {

			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group
			*/
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mCheckBox.setChecked(false);
        }

        childViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);

                } else {

                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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
    }

}
