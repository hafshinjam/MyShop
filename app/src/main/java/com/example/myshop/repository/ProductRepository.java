package com.example.myshop.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.Model.Category;
import com.example.myshop.Model.Product;
import com.example.myshop.Remote.GetCategoryDeserializer;
import com.example.myshop.Remote.GetProductDeserializer;
import com.example.myshop.Remote.ProductService;
import com.example.myshop.Remote.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myshop.Remote.NetworkParameters.CATEGORIES_PATH;
import static com.example.myshop.Remote.NetworkParameters.PRODUCTS_PATH;
import static com.example.myshop.Remote.RetrofitInstance.QUERY_OPTIONS;

public class ProductRepository {
    private static ProductRepository sProductRepository;
    private MutableLiveData<List<Product>> mProductList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoriesList = new MutableLiveData<>();
    private ProductService mProductService;
    private ProductService mCategoryService;

    public static ProductRepository getInstance() {
        if (sProductRepository == null) {
            sProductRepository = new ProductRepository();
        }
        return sProductRepository;
    }

    public ProductRepository() {
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        Object typeAdapter = new GetProductDeserializer();
        Retrofit retrofit = RetrofitInstance.getInstance(type, typeAdapter, PRODUCTS_PATH);
        mProductService = retrofit.create(ProductService.class);

        Type typeCategory = new TypeToken<List<Category>>() {
        }.getType();
        Object categoryTypeAdapter = new GetCategoryDeserializer();
        Retrofit categoryRetrofit = RetrofitInstance.getInstance(typeCategory, categoryTypeAdapter, CATEGORIES_PATH);
        mCategoryService = categoryRetrofit.create(ProductService.class);
    }

    public void setProductListRecent() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "date");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);
        Response<List<Product>> response;
        try {
            response = call.execute();
            mProductList.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setProductListPopularity() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "popularity");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);
        Response<List<Product>> response;
        try {
            response = call.execute();
            mProductList.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setProductListTopRated() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "rating");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);
        Response<List<Product>> response;
        try {
            response = call.execute();
            mProductList.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fetchCategoriesList() {
        Call<List<Category>> call = mCategoryService.listCategories(QUERY_OPTIONS);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mCategoriesList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public MutableLiveData<List<Category>> getCategoriesList() {
        return mCategoriesList;
    }

    public void setCategoryItemList(String categoryID) {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("category", categoryID);
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);
        Response<List<Product>> response;
        try {
            response = call.execute();
            mProductList.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
