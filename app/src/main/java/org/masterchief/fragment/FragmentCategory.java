package org.masterchief.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.masterchief.R;
import org.masterchief.RecipesActivity;
import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.FoodCategory;
import org.masterchief.model.adapter.FoodCategoryAdapter;
import org.masterchief.service.CategoryService;
import org.masterchief.task.LoadDataTask;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class FragmentCategory extends BaseFragment {

    public static final String TAG = FragmentCategory.class.getName();
    private FoodCategoryAdapter categoryAdapter;
    private LoaderManager.LoaderCallbacks<List<FoodCategory>> mLoaderCallbacks;
    private GridView gridview;


    // Required empty public constructor
    public FragmentCategory() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new FoodCategoryAdapter(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (gridview != null) {
            /* save first visible position */
            outState.putInt("firstViewPosition", gridview.getFirstVisiblePosition());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        gridview = (GridView) view.findViewById(R.id.category_grid_view);

        mLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<FoodCategory>>() {
            @Override
            public Loader<List<FoodCategory>> onCreateLoader(int id, Bundle args) {
                return new LoadDataTask<List<FoodCategory>>(getActivity()) {
                    @Override
                    public List<FoodCategory> getEmptyObject() {
                        return Collections.EMPTY_LIST;
                    }

                    @Override
                    public List<FoodCategory> doInBackground() {
                        CategoryService demoService = RetrofitHelper.getCategoryService();
                        final Call<List<FoodCategory>> categories = demoService.loadCategories();
                        try {
                            return categories.execute().body();
                        } catch (IOException e) {
                            Log.e(TAG, e.toString());
                        }
                        return getEmptyObject();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<FoodCategory>> loader, List<FoodCategory> data) {
                // Display our data, for instance updating our adapter
                categoryAdapter.setData(data);
                gridview.setAdapter(categoryAdapter);
                if (savedInstanceState!=null){
                    int lastPosition = savedInstanceState.getInt("firstViewPosition");
                    if (data.size()>=lastPosition){
                        gridview.setSelection(lastPosition);
                    }

                }
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        View childView = gridview.getChildAt(position);
                        Intent intent = new Intent(getActivity(), RecipesActivity.class);
                        intent.putExtra("CATEGORY_ID", ((TextView) childView.findViewById(R.id.category_id)).getText());
                        intent.putExtra("TOOLBAR_TITLE", ((TextView) childView.findViewById(R.id.category_name)).getText());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onLoaderReset(Loader<List<FoodCategory>> loader) {
                // Loader reset, throw away our data,
                // unregister any listeners, etc.
                categoryAdapter.setData(null);
                // Of course, unless you use destroyLoader(),
                // this is called when everything is already dying
                // so a completely empty onLoaderReset() is
                // totally acceptable
            }
        };
        getActivity().getSupportLoaderManager().initLoader(0, null, mLoaderCallbacks);
        return view;
    }

}
