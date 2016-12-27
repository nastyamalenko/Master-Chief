package org.masterchief.service;

import org.masterchief.model.FoodCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public interface CategoryService {

    @GET("v1/gson")
    Call<String> testGson();

    @GET("v1/text")
    Call<String> testText();

    @GET("v1/categories")
    Call<List<FoodCategory>> loadCategories();

    @GET("v1/categories")
    Call<List<Object>> loadObjects();
}
