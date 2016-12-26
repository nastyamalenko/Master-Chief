package org.masterchief.helper;

import org.masterchief.service.CategoryService;
import org.masterchief.service.RecipeService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class RetrofitHelper {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final OkHttpClient.Builder builder = CLIENT.newBuilder();


    static {
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
    }


    public static CategoryService getCategoryService() {
        return getRetrofit().create(CategoryService.class);
    }

    public static RecipeService getRecipeService(){
        return getRetrofit().create(RecipeService.class);
    }


    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://195.234.215.175:8080/http-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
    }
}
