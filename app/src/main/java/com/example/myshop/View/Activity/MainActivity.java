package com.example.myshop.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.R;
import com.example.myshop.View.Fragment.SignUpSignInFragment;
import com.example.myshop.View.Fragment.CartFragment;
import com.example.myshop.View.Fragment.CategoryListFragment;
import com.example.myshop.View.Fragment.HomeFragment;
import com.example.myshop.databinding.ActivityMainBinding;
import com.example.myshop.viewModel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);
        mBinding.setViewModel(mViewModel);
        setListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.setSearchText(query);
                mViewModel.searchWithRecent();
                Intent intent = ListActivity.newIntent(getApplicationContext());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setListeners() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(
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
                                        replace(R.id.fragmentContainer, SignUpSignInFragment.newInstance(),
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
        mBinding.bottomNavigation.setSelectedItemId(R.id.home_fragment);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}