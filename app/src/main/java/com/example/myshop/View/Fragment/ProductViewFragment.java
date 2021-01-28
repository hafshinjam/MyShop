package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentProductViewBinding;
import com.example.myshop.viewModel.ProductViewModel;


public class ProductViewFragment extends Fragment {

    private FragmentProductViewBinding mProductViewBinding;
    public ProductViewModel mViewModel;


    public ProductViewFragment() {
        // Required empty public constructor
    }


    public static ProductViewFragment newInstance() {
        return new ProductViewFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mProductViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_product_view, container, false);
        mProductViewBinding.setViewModel(mViewModel);
        mViewModel.setProductPicture(mProductViewBinding.productImageView);
        mProductViewBinding.productCount
                .setText(String.valueOf(mViewModel.getProductCount()));
        setListeners();
        return mProductViewBinding.getRoot();
    }

    private void setListeners() {
        mProductViewBinding.minusButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.decrementCount();
                        mProductViewBinding.productCount
                                .setText(String.valueOf(mViewModel.getProductCount()));
                    }
                });
        mProductViewBinding.plusButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.incrementCount();
                        mProductViewBinding.productCount
                                .setText(String.valueOf(mViewModel.getProductCount()));
                    }
                });
        mProductViewBinding.deleteFromCartButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.deleteFromCart();
                        mProductViewBinding.productCount
                                .setText(String.valueOf(mViewModel.getProductCount()));
                    }
                });
    }
}