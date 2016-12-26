package org.masterchief.service;


import org.masterchief.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("v1/receipes/category/{categoryId}")
    Call<List<Recipe>> getRecipesByCategoryId(@Path("categoryId") Long categoryId);

    @GET("v1/receipes/{receipeId}")
    Call<Recipe> getReceipeById(@Path("receipeId") Long receipeId);
}
