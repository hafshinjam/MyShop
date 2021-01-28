package com.example.myshop.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.R;
import com.example.myshop.View.Fragment.ProductListFragment;
import com.example.myshop.databinding.ActivityListBinding;
import com.example.myshop.viewModel.ListActivityViewModel;

public class ListActivity extends AppCompatActivity {
    private ActivityListBinding mBinding;
    ListActivityViewModel mViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.
                setContentView(this, R.layout.activity_list);
        mViewModel = new ViewModelProvider(this)
                .get(ListActivityViewModel.class);
        mBinding.setViewModel(mViewModel);
        setFragment();
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
                mViewModel.searchByMethod();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listContainer, ProductListFragment.newInstance(), "productListFragment")
                .commit();
    }
}