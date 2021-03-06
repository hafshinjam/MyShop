package com.example.myshop.repository;

import android.content.Intent;
import android.util.Log;

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

import java.util.HashMap;
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
    private HashMap<Product, Integer> mProductsCart;

    private ProductService mCategoryService;
    Type typeCategory = new TypeToken<List<Category>>() {
    }.getType();
    Object categoryTypeAdapter = new GetCategoryDeserializer();
    private Retrofit mRetrofitCategory = RetrofitInstance.getInstance(typeCategory, categoryTypeAdapter, CATEGORIES_PATH);

    private Product mProductToShow;

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
    }

    public void fetchProductListRecent() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "date");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_recent_fetched", t.toString(), t);
            }
        });


    }

    public void fetchProductListPopularity() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "popularity");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_popular_fetched", t.toString(), t);
            }
        });


    }

    public void fetchProductListTopRated() {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("orderby", "rating");
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_topRated_fetched", t.toString(), t);
            }
        });
    }

    public void fetchCategoryItemList(String categoryID) {
        Map<String, String> OPTIONS = QUERY_OPTIONS;
        OPTIONS.put("category", categoryID);
        Call<List<Product>> call = mProductService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("category_product_fetched", t.toString(), t);
            }
        });
    }

    public void fetchCategoriesList() {
        /*  Retrofit categoryRetrofit = RetrofitInstance.getInstance(typeCategory, categoryTypeAdapter, CATEGORIES_PATH);*/
        mCategoryService = mRetrofitCategory.create(ProductService.class);
        Call<List<Category>> call = mCategoryService.listCategories(QUERY_OPTIONS);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mCategoriesList.setValue(response.body());
                Log.d("category_fetched", response.toString());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("category_fetched", t.toString(), t);
            }
        });
    }

    public MutableLiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public MutableLiveData<List<Category>> getCategoriesList() {
        return mCategoriesList;
    }

    public Product getProductToShow() {
        return mProductToShow;
    }

    public void setProductToShow(Product productToShow) {
        mProductToShow = productToShow;
    }

    public HashMap<Product, Integer> getProductsCart() {
        return mProductsCart;
    }

    public void addProductToCart(Product product) {
        if (mProductsCart.get(product) != null)
            mProductsCart.put(product, mProductsCart.get(product) + 1);
        else
            mProductsCart.put(product, 1);
    }

    public void removeProductToCart(Product product) {
        if (mProductsCart.containsKey(product))
            if (mProductsCart.get(product) > 1)
                mProductsCart.put(product, mProductsCart.get(product) - 1);
            else mProductsCart.remove(product);

    }

    public void clearProductCart() {
        mProductsCart.clear();
    }


}
