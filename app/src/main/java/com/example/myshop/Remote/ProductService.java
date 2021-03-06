package com.example.myshop.Remote;

import com.example.myshop.Model.Category;
import com.example.myshop.Model.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductService {
    @GET(".")
    Call<List<Product>> listProducts(@QueryMap Map<String, String> options);

    @GET(".")
    Call<List<Category>> listCategories(@QueryMap Map<String, String> options);
}
