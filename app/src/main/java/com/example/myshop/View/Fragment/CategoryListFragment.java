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
import com.example.myshop.Model.Category;
import com.example.myshop.R;
import com.example.myshop.databinding.FragmentListBinding;
import com.example.myshop.repository.ProductRepository;

import java.util.List;

public class CategoryListFragment extends Fragment {
    private FragmentListBinding mBinding;
    private categoryAdapter mCategoryAdapter;
    private ProductRepository mRepository;
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
        mRepository = ProductRepository.getInstance(getContext());
        mCategoryListLive = mRepository.getCategoriesList();
        registerObservers();
        mRepository.fetchCategoriesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_list, container, false);
        //  findViews(view);
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.AscDscSpinner.setVisibility(View.GONE);
        mBinding.SortSpinner.setVisibility(View.GONE);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

  /*  private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list);
    }*/

    private void registerObservers() {
        mCategoryListLive.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                initAdapters(categories);
                mBinding.list.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initAdapters(List<Category> list) {
        if (mCategoryAdapter == null) {
            mCategoryAdapter = new categoryAdapter(list, getActivity());
            mBinding.list.setAdapter(mCategoryAdapter);
        }
        mCategoryAdapter.notifyDataSetChanged();
    }
}