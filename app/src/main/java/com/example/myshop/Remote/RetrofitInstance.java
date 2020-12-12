package com.example.myshop.Remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PATCH;

import static com.example.myshop.Remote.NetworkParameters.BASE_PATH;
import static com.example.myshop.Remote.NetworkParameters.CONSUMER_KEY;
import static com.example.myshop.Remote.NetworkParameters.CONSUMER_SECRET;
import static com.example.myshop.Remote.NetworkParameters.PRODUCTS_PATH;

public class RetrofitInstance {
    public static Map<String, String> QUERY_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static Retrofit getInstance(Type type, Object typeAdapter, String path) {

        return new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(createGsonFactory(type, typeAdapter))
                .build();
    }

    public static Converter.Factory createGsonFactory(Type type, Object typeAdapter) {
        GsonBuilder builder = new GsonBuilder().registerTypeAdapter(type, typeAdapter);
        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }
}

