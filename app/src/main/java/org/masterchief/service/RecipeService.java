package org.masterchief.service;


import org.masterchief.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {

    @GET("v1/recipes/category/{categoryId}")
    Call<List<Recipe>> getRecipesByCategoryId(@Path("categoryId") Long categoryId);

    @GET("v1/recipes/{recipeId}")
    Call<Recipe> getRecipeById(@Path("recipeId") String recipeId);

    @GET("v1/recipes/random")
    Call<Long> getRandomRecipeId();

    @GET("v1/recipes/search")
    Call<List<String>> getRecipes(@Query("name") String name,
                                  @Query("foodCategoryIds") List<String> foodCategoryIds,
                                  @Query("cookingMethodIds") List<String> cookingMethodIds,
                                  @Query("cuisineIds") List<String> cuisineIds);
}
