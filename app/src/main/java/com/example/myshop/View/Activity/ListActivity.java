package com.example.myshop.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myshop.R;
import com.example.myshop.View.Fragment.ProductListFragment;

public class ListActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setFragment();
    }

    private void setFragment() {
        Fragment fragment = getSupportFragmentManager().getFragments().get(0);
        if (fragment==null)
            getSupportFragmentManager()
                    .beginTransaction()
            .replace(R.id.listContainer, ProductListFragment.newInstance(),"productListFragment")
            .commit();
    }
}