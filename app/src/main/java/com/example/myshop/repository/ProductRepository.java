package com.example.myshop.repository;

import android.content.Context;
import android.content.SharedPreferences;
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

import static com.example.myshop.Remote.NetworkParameters.BASE_PATH;
import static com.example.myshop.Remote.RetrofitInstance.QUERY_OPTIONS;

public class ProductRepository {
    private static ProductRepository sProductRepository;
    private MutableLiveData<List<Product>> mProductList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoriesList = new MutableLiveData<>();
    private Product mProductToShow;
    private Map<String, String> SortOrder;
    private Map<String, String> SortMethod;

    private ProductService mProductService;
    private SharedPreferences mPreferences;
    private Context mContext;

    public static HashMap<Product, Integer> mProductsCart;
    private ProductService mCategoryService;

    Type typeCategory = new TypeToken<List<Category>>() {
    }.getType();
    Object categoryTypeAdapter = new GetCategoryDeserializer();
    private Retrofit mRetrofitCategory = RetrofitInstance.getInstance(typeCategory, categoryTypeAdapter, BASE_PATH);

    public static ProductRepository getInstance(Context context) {
        if (sProductRepository == null) {
            sProductRepository = new ProductRepository(context);
        }
        return sProductRepository;
    }

    public ProductRepository(Context context) {
        mContext = context.getApplicationContext();
        mPreferences = mContext.
                getSharedPreferences("com.example.myshop.Cart", Context.MODE_PRIVATE);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        Object typeAdapter = new GetProductDeserializer();
        Retrofit retrofit = RetrofitInstance.getInstance(type, typeAdapter, BASE_PATH);
        mProductService = retrofit.create(ProductService.class);
        if (mProductsCart == null)
            mProductsCart = new HashMap<>();
        SortMethod = new HashMap<>();
        SortOrder = new HashMap<>();
        SortOrder.put("order", "desc");
    }

    public void setSortOrder(Map<String, String> sortOrder) {
        SortOrder = sortOrder;
    }

    public void setSortMethod(Map<String, String> sortMethod) {
        SortMethod = sortMethod;
    }

    public void fetchProductListRecent(int page) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "date");
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(page));
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

    public void fetchProductListPopularity(int page) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "popularity");
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(page));
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

    public void fetchProductListTopRated(int page) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "rating");
        OPTIONS.put("page", String.valueOf(page));
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

    public void fetchCategoryItemList(String categoryID, int page) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("category", categoryID);
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(page));
        /*  OPTIONS.put("per_page", "20");*/
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
        mCategoryService = mRetrofitCategory.create(ProductService.class);
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);

        /*OPTIONS.put("per_page", "20");*/
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

        SharedPreferences.Editor editor = mPreferences.edit();
        if (count != 0)
            editor.putInt(product.getId(), count);
        else {
            if (mPreferences.contains(product.getId()))
                editor.remove(product.getId());
        }
        editor.apply();
    }

    public int getProductCartCount(Product product) {
        if (mPreferences.contains(product.getId())) {
             return mPreferences.getInt(product.getId(),0);
        } else
            return 0;
    }

}
