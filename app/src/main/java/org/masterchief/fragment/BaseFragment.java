package org.masterchief.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.masterchief.IDataLoad;

public abstract class BaseFragment extends Fragment implements IDataLoad {
    private FragmentActivity activity;

    public BaseFragment() {
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }
}
