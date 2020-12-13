package com.example.myshop.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myshop.View.Fragment.ProductViewFragment;
import com.example.myshop.R;
import com.example.myshop.databinding.ActivityProductViewBinding;

public class ProductViewActivity extends AppCompatActivity {
    private ActivityProductViewBinding mBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.
                setContentView(this, R.layout.activity_product_view);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.productContainer, ProductViewFragment.newInstance(), "productViewFragment")
                .commit();
    }
}