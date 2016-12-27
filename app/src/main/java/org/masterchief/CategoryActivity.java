package org.masterchief;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.FoodCategory;
import org.masterchief.model.adapter.FoodCategoryAdapter;
import org.masterchief.service.CategoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Hide")
                .setMessage("Hide application. Are you sure?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(R.string.no, null).show();
    }


    @Override
    public void loadData() {
        final GridView gridview = (GridView) findViewById(R.id.category_grid_view);
        CategoryService demoService = RetrofitHelper.getCategoryService();
        final Call<List<FoodCategory>> categories = demoService.loadCategories();
        categories.enqueue(new Callback<List<FoodCategory>>() {
            @Override
            public void onResponse(Call<List<FoodCategory>> call, Response<List<FoodCategory>> response) {
                if (response.isSuccessful()) {
                    gridview.setAdapter(new FoodCategoryAdapter(context, response.body()));
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            View childView = gridview.getChildAt(position);
                            Intent intent = new Intent(context, RecipeActivity.class);
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

    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.main_page);
    }

    @Override
    protected boolean displayHomeButton() {
        return false;
    }

}
