package com.example.myshop.Data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.myshop.Data.Model.Customer.Customer;
import com.example.myshop.Remote.ProductService;
import com.example.myshop.Remote.RetrofitInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myshop.Remote.RetrofitInstance.QUERY_OPTIONS;

public class CustomerRepository {
    private MutableLiveData<Boolean> isEmailAvailable = new MutableLiveData<>(true);
    private ProductService mService = RetrofitInstance.getCustomerRetrofitInstance()
            .create(ProductService.class);

    private static CustomerRepository sCustomerRepository;
    private Context mContext;
    private MutableLiveData<Customer> mCustomer = new MutableLiveData<>();

    public static CustomerRepository getInstance(Context context) {
        if (sCustomerRepository == null)
            sCustomerRepository = new CustomerRepository(context);
        return sCustomerRepository;
    }

    public CustomerRepository(Context context) {
        mContext = context.getApplicationContext();
    }

    public MutableLiveData<Boolean> getIsEmailAvailable() {
        return isEmailAvailable;
    }

    public void setIsEmailAvailable(MutableLiveData<Boolean> isEmailAvailable) {
        this.isEmailAvailable = isEmailAvailable;
    }

    public ProductService getService() {
        return mService;
    }

    public void setService(ProductService service) {
        mService = service;
    }

    public void CheckEmailExists(String email) {
        Map<String, String> options = new HashMap<>(QUERY_OPTIONS);
        options.put("email", email);
        Call<List<Customer>> call = mService.getCustomerByEmail(options);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.body() != null)
                    isEmailAvailable.setValue(response.body().isEmpty());
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public void registerCustomer(String firstName, String lastName, String email) {
        Map<String, String> options = new HashMap<>(QUERY_OPTIONS);
        options.put("email", email);

        Call<Customer> call = mService.registerCustomer(options);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                options.put("first_name", firstName);
                options.put("last_name", lastName);
                Call<Customer> updateCall = mService.updateCustomerInfo(response.body().getId(), options);
                updateCall.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        mCustomer.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }
}
