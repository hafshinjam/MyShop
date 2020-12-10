package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adapters.categoryAdapter;
import com.example.myshop.Model.Category;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {
    private categoryAdapter mCategoryAdapter;
    private RecyclerView mRecyclerView;
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
        mRepository = ProductRepository.getInstance();
        mCategoryListLive = mRepository.getCategoriesList();
        registerObservers();
        mRepository.fetchCategoriesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list);
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
        if (mCategoryAdapter == null) {
            mCategoryAdapter = new categoryAdapter(list, getActivity());
            mRecyclerView.setAdapter(mCategoryAdapter);
        }
            mCategoryAdapter.notifyDataSetChanged();
    }
}