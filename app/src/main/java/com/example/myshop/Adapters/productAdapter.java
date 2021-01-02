package com.example.myshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.View.Activity.ProductViewActivity;
import com.example.myshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ProductHolder> {
    List<Product> mProducts;
    private ProductRepository mProductRepository;
    Context mContext;

    public productAdapter(List<Product> products, Context context) {
        mContext = context.getApplicationContext();
        mProducts = products;
        mProductRepository = ProductRepository.getInstance(context);
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.product_row, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mProductImage;
        private TextView mProductName;
        private TextView mProductPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.productImageList);
            mProductName = itemView.findViewById(R.id.productNameList);
            mProductPrice = itemView.findViewById(R.id.ProductPriceList);
            //todo Implement the behaviour on click to show
            // the product's Page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductRepository.setProductToShow(mProduct);
                    Intent intent = ProductViewActivity.newIntent(mContext);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mProductName.setText(mProduct.getName());
            mProductPrice.setText(String.valueOf(mProduct.getPrice()));

            Picasso.get().load(mProduct.getPhotoUriList().get(0))
                    .placeholder(R.drawable.ic_product)
                    .into(mProductImage);
        }
    }
}
