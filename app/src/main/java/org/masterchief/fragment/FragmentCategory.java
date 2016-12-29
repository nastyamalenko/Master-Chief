package org.masterchief.fragment;


import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCategory extends BaseFragment {


    // Required empty public constructor
    public FragmentCategory() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void loadData() {
        final GridView gridview = (GridView) getActivity().findViewById(R.id.category_grid_view);
        CategoryService demoService = RetrofitHelper.getCategoryService();
        final Call<List<FoodCategory>> categories = demoService.loadCategories();
        categories.enqueue(new Callback<List<FoodCategory>>() {
            @Override
            public void onResponse(Call<List<FoodCategory>> call, Response<List<FoodCategory>> response) {
                if (response.isSuccessful()) {
                    gridview.setAdapter(new FoodCategoryAdapter(getActivity(), response.body()));
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

            }

            @Override
            public void onFailure(Call<List<FoodCategory>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
