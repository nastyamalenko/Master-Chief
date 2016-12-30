package org.masterchief.service;


import org.masterchief.model.Dictionary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryService {


    @GET("v1/recipes/dictionary")
    Call<List<Dictionary>> getAllDictionary();

    @GET("v1/recipes/dictionary/{dictionaryId}/items")
    Call<List<Dictionary>> getAllDictionaryItems(@Path("dictionaryId") String dictionaryId);
}
