package com.example.myshop.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myshop.Model.Category;
import com.example.myshop.Model.Product;
import com.example.myshop.Remote.GetCategoryDeserializer;
import com.example.myshop.Remote.GetProductDeserializer;
import com.example.myshop.Remote.ProductService;
import com.example.myshop.Remote.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

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
    private Product mProductToShow;
    private Map<String,String> SortOrder;
    private Map<String,String> SortMethod;

    private ProductService mProductService;

    private static HashMap<Product, Integer> mProductsCart;
    private ProductService mCategoryService;

    Type typeCategory = new TypeToken<List<Category>>() {
    }.getType();
    Object categoryTypeAdapter = new GetCategoryDeserializer();
    private Retrofit mRetrofitCategory = RetrofitInstance.getInstance(typeCategory, categoryTypeAdapter, CATEGORIES_PATH);

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
        SortOrder= new HashMap<>();
        SortOrder.put("order","desc");
    }

    public void setSortOrder(Map<String, String> sortOrder) {
        SortOrder = sortOrder;
    }

    public void setSortMethod(Map<String, String> sortMethod) {
        SortMethod = sortMethod;
    }

    public void fetchProductListRecent() {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "date");
        OPTIONS.putAll(SortOrder);
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
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "popularity");
        OPTIONS.putAll(SortOrder);
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
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "rating");
        OPTIONS.putAll(SortOrder);
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
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("category", categoryID);
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("per_page", "20");
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
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);

        OPTIONS.put("per_page", "20");
        Call<List<Category>> call = mCategoryService.listCategories(OPTIONS);

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
        Log.d("repository", productToShow.getName());
    }

    public HashMap<Product, Integer> getProductsCart() {
        return mProductsCart;
    }

    public void updateProductToCart(Product product, int count) {
        mProductsCart.put(product, count);

    }


}
