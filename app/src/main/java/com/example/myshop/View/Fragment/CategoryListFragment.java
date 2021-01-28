package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myshop.Adapters.categoryAdapter;
import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.repository.Repository;
import com.example.myshop.R;
import com.example.myshop.databinding.FragmentCategoryListBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

//todo implement ViewModel for this fragment
public class CategoryListFragment extends Fragment {
    private FragmentCategoryListBinding mCategoryListBinding;

    private categoryAdapter mCategoryAdapter;
    private Repository mRepository;
    private LiveData<List<Category>> mCategoryListLive;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    public static CategoryListFragment newInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = Repository.getInstance(getContext());
        mCategoryListLive = mRepository.getCategoriesList();
  /*      registerObservers();
        mRepository.fetchCategoriesList();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCategoryListBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_category_list, container, false);
        initializeView();
        Category category = mRepository.getCategoryListMap().entrySet().iterator().next().getKey();
        initAdapters(mRepository.getCategoryListMap().get(category));
        return mCategoryListBinding.getRoot();
    }

    private void initializeView() {
        mCategoryListBinding.categoryList
                .setLayoutManager(new LinearLayoutManager(getActivity()));
        for (Category category : mRepository.getCategoryListMap().keySet()) {
            mCategoryListBinding.categoryTabLayout
                    .addTab(mCategoryListBinding.categoryTabLayout.newTab()
                            .setText(category.getCategoryName()));
        }
        mCategoryListBinding.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Category category = mRepository.getCategory(tab.getText().toString());
                List<Category> categories = mRepository.getCategoryListMap().get(category);
                initAdapters(categories);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void registerObservers() {
        mCategoryListLive.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                initAdapters(categories);
            }
        });
    }

    private void initAdapters(List<Category> list) {
        mCategoryAdapter = new categoryAdapter(list, getActivity());
        mCategoryListBinding.categoryList.setAdapter(mCategoryAdapter);

        mCategoryAdapter.notifyDataSetChanged();
    }
}