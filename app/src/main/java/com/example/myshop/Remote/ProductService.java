package com.example.myshop.Remote;

import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.Model.Customer.Customer;
import com.example.myshop.Data.Model.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductService {
    @GET("products/")
    Call<List<Product>> listProducts(@QueryMap Map<String, String> options);

    @GET("products/{id}/")
    Call<Product> getProduct(@Path("id") String id, @QueryMap Map<String, String> options);

    @GET("products/categories/")
    Call<List<Category>> listCategories(@QueryMap Map<String, String> options);

    @GET("customers/")
    Call<List<Customer>> getCustomerByEmail(@QueryMap Map<String, String> options);

    @POST("customers/")
    Call<Customer> registerCustomer(@QueryMap Map<String, String> options);

    @PUT("customers/{id}/")
    Call<Customer> updateCustomerInfo(@Body Integer id, @QueryMap Map<String, String> options);
}
