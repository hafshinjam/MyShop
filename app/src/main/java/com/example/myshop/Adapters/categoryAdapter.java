package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Category;
import com.example.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryHolder> {
    List<Category> mCategories;
    Context mContext;

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public categoryAdapter(List<Category> categories, Context context) {
        mContext = context.getApplicationContext();
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
