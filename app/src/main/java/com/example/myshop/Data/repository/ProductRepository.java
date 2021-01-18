package com.example.myshop.Data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.Model.Product;
import com.example.myshop.Remote.GetCategoryDeserializer;
import com.example.myshop.Remote.GetProductDeserializer;
import com.example.myshop.Remote.GetProductsDeserializer;
import com.example.myshop.Remote.ProductService;
import com.example.myshop.Remote.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    //liveData
    private MutableLiveData<List<Product>> mProductList = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListSpecial = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoriesList = new MutableLiveData<>();
    public MutableLiveData<List<Product>> mProductsCart = new MutableLiveData<>();
    //services
    private ProductService mProductsService;
    private ProductService mSingleProductService;
    private ProductService mCategoryService;
    //repositories
    private static ProductRepository sProductRepository;
    private CategoryDBRepository mCategoryDBRepository;

    private Product mProductToShow;
    private String categoryID;
    private String searchText;
    private Map<String, String> SortOrder;
    private Map<Category, List<Category>> mCategoryListMap;

    private SharedPreferences mPreferences;
    private Context mContext;


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
        mCategoryDBRepository = CategoryDBRepository.getInstance(mContext);
        mPreferences = mContext.
                getSharedPreferences("com.example.myshop.Cart", Context.MODE_PRIVATE);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        Object typeAdapter = new GetProductsDeserializer();
        Retrofit retrofit = RetrofitInstance.getInstance(type, typeAdapter, BASE_PATH);
        mProductsService = retrofit.create(ProductService.class);

        Type productType = Product.class;
        Object typeAdapterProduct = new GetProductDeserializer();
        Retrofit retrofitProduct = RetrofitInstance.getInstance(productType, typeAdapterProduct, BASE_PATH);
        mSingleProductService = retrofitProduct.create(ProductService.class);

        SortOrder = new HashMap<>();
        SortOrder.put("order", "desc");

    }

    public void setSortOrder(Map<String, String> sortOrder) {
        SortOrder = sortOrder;
    }

    public void fetchProductListRecent(int pageNumber) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "date");
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(pageNumber));
        if (categoryID != null) {
            OPTIONS.put("category", categoryID);
        }
        if (searchText != null) {
            OPTIONS.put("search", searchText);
        }
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
                Log.d("product_recent_fetched", "recent");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_recent_fetched", t.toString(), t);
            }
        });
    }

    public void fetchProductListPopularity(int pageNumber) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "popularity");
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(pageNumber));
        if (categoryID != null) {
            OPTIONS.put("category", categoryID);
        }
        if (searchText != null) {
            OPTIONS.put("search", searchText);
        }
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
                Log.d("product_recent_fetched", "popular");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_popular_fetched", t.toString(), t);
            }
        });


    }

    public void fetchProductListTopRated(int pageNumber) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "rating");
        OPTIONS.put("page", String.valueOf(pageNumber));
        OPTIONS.putAll(SortOrder);
        if (categoryID != null) {
            OPTIONS.put("category", categoryID);
        }
        if (searchText != null) {
            OPTIONS.put("search", searchText);
        }
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductList.setValue(response.body());
                Log.d("product_recent_fetched", "top");

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_topRated_fetched", t.toString(), t);
            }
        });
    }

    public void fetchProductsListByPrice(int pageNumber) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("orderby", "price");
        OPTIONS.put("page", String.valueOf(pageNumber));
        OPTIONS.putAll(SortOrder);
        if (categoryID != null) {
            OPTIONS.put("category", categoryID);
        }
        if (searchText != null) {
            OPTIONS.put("search", searchText);
        }
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);

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

    public void fetchSpecialProductsList(int pageNumber) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("page", String.valueOf(pageNumber));
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("tag", "48");
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductListSpecial.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("product_topRated_fetched", t.toString(), t);
            }
        });
    }

    public void fetchCategoryProductList(String categoryID, int page) {
        Map<String, String> OPTIONS = new HashMap<>(QUERY_OPTIONS);
        OPTIONS.put("category", categoryID);
        OPTIONS.putAll(SortOrder);
        OPTIONS.put("page", String.valueOf(page));
        Call<List<Product>> call = mProductsService.listProducts(OPTIONS);
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

        Call<List<Category>> call = mCategoryService.listCategories(OPTIONS);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mCategoriesList.setValue(response.body());
                mCategoryDBRepository.clear();
                mCategoryDBRepository.insertList(response.body());
                ClusterCategoryList();
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

    public MutableLiveData<List<Product>> getProductListSpecial() {
        return mProductListSpecial;
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

    public void fetchCartItem(String id) {
        Map<String, String> options = new HashMap<>(QUERY_OPTIONS);
        Call<Product> productCall = mSingleProductService.getProduct(id, options);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.d("Fetch_product", String.valueOf(response.body().getName()));
                if (response.body().getDescription() != null) {
                    List<Product> products = new ArrayList<>();
                    if (mProductsCart.getValue() != null)
                        products = mProductsCart.getValue();
                    products.add(response.body());
                    mProductsCart.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(mContext, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateProductCart(Product product, int count) {

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
            return mPreferences.getInt(product.getId(), 0);
        } else
            return 0;
    }

    public MutableLiveData<List<Product>> getProductsCart() {
        return mProductsCart;
    }

    public void setProductsCartValues(List<Product> productsCart) {
        mProductsCart.setValue(productsCart);
    }

    public int getProductCartSize() {
        if (mProductsCart.getValue() != null)
            return mProductsCart.getValue().size();
        else return 0;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void ClusterCategoryList() {
        mCategoryListMap = new HashMap<>();
        for (Category cat : mCategoryDBRepository.getParentCategories()) {
            List<Category> categories = new ArrayList<>();
            categories.add(cat);
            categories.addAll(mCategoryDBRepository.getCategoryByParentCategory(cat.getCategoryID()));
            mCategoryListMap.put(cat, categories);
        }
    }

    public Map<Category, List<Category>> getCategoryListMap() {
        return mCategoryListMap;
    }
    public Category getCategory(String name){
        return mCategoryDBRepository.getCategoryByName(name);
    }
}
