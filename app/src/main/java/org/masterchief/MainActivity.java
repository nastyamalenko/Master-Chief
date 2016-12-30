package org.masterchief;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.masterchief.fragment.FragmentAbout;
import org.masterchief.fragment.FragmentCategory;
import org.masterchief.fragment.FragmentFavoriteRecipes;
import org.masterchief.helper.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.masterchief.RecipeActivity.EXTRA_RECIPE_ID;

public class MainActivity extends BaseActivity {

    private static final String LOGGER_TAG = MainActivity.class.getName();
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private FragmentManager fragmentManager;
    private CharSequence toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.demo_toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.flContent, new FragmentCategory()).commit();
        }
        setSupportActionBar(toolbar);
        setupDrawerContent(nvDrawer);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.home_fragment:
                fragmentClass = FragmentCategory.class;
                break;
            case R.id.favorite_fragment:
                fragmentClass = FragmentFavoriteRecipes.class;
                break;
            case R.id.random_recipe:
                mDrawer.closeDrawers();
                randomRecipe();
                return;
            case R.id.about:
                fragmentClass = FragmentAbout.class;
                break;
            default:
                fragmentClass = FragmentCategory.class;
        }
        menuItem.setChecked(true);
        toolbarTitle = menuItem.getTitle();
        getSupportActionBar().setTitle(toolbarTitle);

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void randomRecipe() {


        final Call<Long> randomRecipeId = RetrofitHelper.getRecipeService().getRandomRecipeId();
        randomRecipeId.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.body() != null) {
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra(EXTRA_RECIPE_ID, String.valueOf(response.body()));
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e(LOGGER_TAG, t.getMessage());
            }
        });

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
    }

    @Override
    protected String getToolbarTitle() {
        if (toolbarTitle == null) {
            toolbarTitle = getResources().getString(R.string.main_page);
        }
        return toolbarTitle.toString();
    }

    @Override
    protected boolean displayHomeButton() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = super.onCreateOptionsMenu(menu);
        MenuItem actionSearch = menu.findItem(R.id.action_search);
        if (actionSearch != null) {
            actionSearch.setVisible(true);
        }
        return b && true;
    }
}
