package com.example.myshop.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myshop.View.Fragment.CartFragment;
import com.example.myshop.View.Fragment.AccountManagementFragment;
import com.example.myshop.View.Fragment.CategoryListFragment;
import com.example.myshop.View.Fragment.HomeFragment;
import com.example.myshop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListeners();
    }

    private void initView() {
        mNavigationView = findViewById(R.id.bottom_navigation);
        mNavigationView.setSelectedItemId(R.id.home_fragment);
    }

    private void setListeners() {
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            //todo implement fragment on click
                            case R.id.home_fragment:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.fragmentContainer, HomeFragment.newInstance(),
                                                "home").commit();
                                break;
                            case R.id.cart_fragmernt:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.fragmentContainer, CartFragment.newInstance(),
                                                "shopping_cart").commit();
                                break;
                            case R.id.account_management_fragment:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.fragmentContainer, AccountManagementFragment.newInstance(),
                                                "account_management").commit();
                                break;
                            case R.id.categories:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.fragmentContainer,
                                                CategoryListFragment.newInstance(),
                                                "most_viewed").commit();

                                break;
                        }
                        return true;
                    }
                }
        );
    }

}