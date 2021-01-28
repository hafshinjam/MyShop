package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentSigninSignupBinding;
import com.example.myshop.viewModel.SignInSignUpViewModel;

public class SignUpSignInFragment extends Fragment {
    SignInSignUpViewModel mSignInSignUpViewModel;
    FragmentSigninSignupBinding mBinding;

    public SignUpSignInFragment() {
        // Required empty public constructor
    }


    public static SignUpSignInFragment newInstance() {
        SignUpSignInFragment fragment = new SignUpSignInFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInSignUpViewModel = new ViewModelProvider(this)
                .get(SignInSignUpViewModel.class);
        registerObservers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_signin_signup,
                container,
                false);
        mBinding.setViewModelSignIn(mSignInSignUpViewModel);
        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mBinding.editTextTextEmailAddress.getText().toString();
                mSignInSignUpViewModel.isAvailable(email);
            }
        });
        mBinding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.editTextFirstNameSignUp.getText() != null
                        && mBinding.editTextLastNameSignUp.getText() != null
                        && mBinding.editTextTextEmailAddress.getText() != null) {
                    String firstName = mBinding.editTextFirstNameSignUp.getText().toString();
                    String lastName = mBinding.editTextLastNameSignUp.getText().toString();
                    String email = mBinding.editTextTextEmailAddress.getText().toString();
                    mSignInSignUpViewModel.registerCustomer(firstName, lastName, email);
                }
            }
        });
    }

    private void registerObservers() {
        mSignInSignUpViewModel.getIsAvailable()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean available) {
                        if (!available)
                            Toast.makeText(getContext(), "این ایمیل قبلا استفاده شده است", Toast.LENGTH_LONG).show();
                    }
                });
    }
}