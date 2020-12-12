package com.example.myshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Category;
import com.example.myshop.R;
import com.example.myshop.View.Activity.ListActivity;
import com.example.myshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryHolder> {
    List<Category> mCategories;
    Context mContext;
    private ProductRepository mProductRepository;

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public categoryAdapter(List<Category> categories, Context context) {
        mContext = context.getApplicationContext();
        mProductRepository = ProductRepository.getInstance();
        mCategories = new ArrayList<>();
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bindCategory(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        Category mCategory;
        private TextView mCategoryText;
        private ImageView mCategoryImage;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryText = itemView.findViewById(R.id.categoryText);
            mCategoryImage = itemView.findViewById(R.id.categoryImage);
            //todo Implement the behaviour on click to show
            // the list of products with specific category
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo there is bug here should find another way
                    mProductRepository.fetchCategoryItemList(mCategory.getCategoryID());
                    Log.d("cat_tag",mCategory.getCategoryID());
                    Intent intent = ListActivity.newIntent(mContext);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bindCategory(Category category) {
            mCategory = category;
            mCategoryText.setText(mCategory.getCategoryName());
            Picasso.get()
                    .load(mCategory.getPhotoUri())
                    .placeholder(R.drawable.ic_category)
                    .into(mCategoryImage);
        }
    }
}
