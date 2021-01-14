package com.example.myshop.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myshop.R;
import com.example.myshop.View.Fragment.ProductListFragment;
import com.example.myshop.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {
    private ActivityListBinding mBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.
                setContentView(this, R.layout.activity_list);
        setFragment();
    }

    private void setFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listContainer, ProductListFragment.newInstance(), "productListFragment")
                .commit();
    }
}