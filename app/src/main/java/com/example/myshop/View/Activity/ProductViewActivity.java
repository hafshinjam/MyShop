package com.example.myshop.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myshop.View.Fragment.ProductViewFragment;
import com.example.myshop.R;

public class ProductViewActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, ProductViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.productContainer, ProductViewFragment.newInstance(), "productViewFragment")
                .commit();
    }
}