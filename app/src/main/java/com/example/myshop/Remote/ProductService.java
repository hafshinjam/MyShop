package com.example.myshop.Remote;

import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.Model.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductService {
    @GET("products/")
    Call<List<Product>> listProducts(@QueryMap Map<String, String> options);

    @GET("products/{id}/")
    Call<Product> getProduct(@Path("id") String id, @QueryMap Map<String, String> options);

    @GET("products/categories/")
    Call<List<Category>> listCategories(@QueryMap Map<String, String> options);
}
