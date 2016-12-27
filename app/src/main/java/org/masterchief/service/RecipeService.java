package org.masterchief.service;


import org.masterchief.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("v1/recipes/category/{categoryId}")
    Call<List<Recipe>> getRecipesByCategoryId(@Path("categoryId") Long categoryId);

    @GET("v1/recipes/{recipeId}")
    Call<Recipe> getRecipeById(@Path("recipeId") String recipeId);
}
