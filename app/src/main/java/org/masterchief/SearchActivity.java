package org.masterchief;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import org.masterchief.helper.RetrofitHelper;
import org.masterchief.model.Dictionary;
import org.masterchief.model.adapter.ExpAdapter;
import org.masterchief.service.DictionaryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private static final String LOGGER_TAG = SearchActivity.class.getName();


    // названия компаний (групп)
    String[] groups = new String[]{"HTC", "Samsung", "LG"};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[]{"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[]{"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[]{"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список атрибутов группы или элемента
    Map<String, String> m;

    ExpandableListView elvMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* DEMO*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }


    private void initialize(HashMap<Dictionary, List<Dictionary>> dictionaryListHashMap) {
        final ExpAdapter expAdapter = new ExpAdapter(this, dictionaryListHashMap);
        elvMain = (ExpandableListView) findViewById(R.id.elv);
        elvMain.setAdapter(expAdapter);

        Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void loadData() {
        final HashMap<Dictionary, List<Dictionary>> dictionaryListHashMap = new HashMap<>();
        final DictionaryService dictionaryService = RetrofitHelper.getDictionaryService();
        Call<List<Dictionary>> dictionaries = dictionaryService.getAllDictionary();
        dictionaries.enqueue(new Callback<List<Dictionary>>() {
            @Override
            public void onResponse(Call<List<Dictionary>> call, Response<List<Dictionary>> response) {
                if (response.body() != null) {
                    for (final Dictionary dictionary : response.body()) {
                        Call<List<Dictionary>> dictionaryItems = dictionaryService.getAllDictionaryItems(String.valueOf(dictionary.getId()));
                        dictionaryItems.enqueue(new Callback<List<Dictionary>>() {
                            @Override
                            public void onResponse(Call<List<Dictionary>> call, Response<List<Dictionary>> response) {

                                dictionaryListHashMap.put(dictionary, response.body());
                                initialize(dictionaryListHashMap);
                            }

                            @Override
                            public void onFailure(Call<List<Dictionary>> call, Throwable t) {
                                Log.e(LOGGER_TAG, t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Dictionary>> call, Throwable t) {
                Log.e(LOGGER_TAG, t.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        if (item != null)
            item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public String getToolbarTitle() {
        return "Пошук";
    }
}
