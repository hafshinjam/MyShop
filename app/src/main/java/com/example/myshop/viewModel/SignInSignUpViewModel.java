package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Data.repository.CustomerRepository;

public class SignInSignUpViewModel extends AndroidViewModel {
    private CustomerRepository mCustomerRepository;
    private LiveData<Boolean> mIsAvailable;

    public SignInSignUpViewModel(@NonNull Application application) {
        super(application);
        mCustomerRepository = CustomerRepository.getInstance(getApplication());
        mIsAvailable = mCustomerRepository.getIsEmailAvailable();
    }

    public LiveData<Boolean> getIsAvailable() {
        return mIsAvailable;
    }

    public void registerCustomer(String firstName, String lastNAme, String email) {
        mCustomerRepository.registerCustomer(firstName, lastNAme, email);
    }

    public void isAvailable(String email) {
        mCustomerRepository.CheckEmailExists(email);
    }
}
