package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartItemViewHolder> {
    List<Product> mCartList;
    Context mContext;
    ProductRepository mRepository;

    public cartAdapter(List<Product> cartList, Context context) {
        mContext = context.getApplicationContext();
        mCartList = cartList;
        mRepository = ProductRepository.getInstance(mContext);
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_item_row, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        holder.bindItem(mCartList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        Product mProduct;
        TextView ProductName;
        TextView Price;
        Button MinusButton;
        Button PlusButton;
        TextView ItemCount;
        ImageView ItemImage;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.cartItemName);
            Price = itemView.findViewById(R.id.cartItemPrice);
            ItemImage = itemView.findViewById(R.id.cartItemPhoto);
            MinusButton = itemView.findViewById(R.id.minusButtonCartItem);
            PlusButton = itemView.findViewById(R.id.cartItemPlus);
            ItemCount = itemView.findViewById(R.id.cartItemCount);
            //todo set using the function in repository
            PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepository.
                            updateProductToCart(mProduct, (mRepository.getProductCartCount(mProduct) + 1));
                }
            });
            MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepository.
                            updateProductToCart(mProduct, (mRepository.getProductCartCount(mProduct) - 1));
                }
            });
        }

        public void bindItem(Product product) {
            mProduct = product;
            ProductName.setText(mProduct.getName());
            Price.setText(String.valueOf(mProduct.getPrice()));
            Picasso.get().load(mProduct.getPhotoUriList().get(0))
                    .placeholder(R.drawable.ic_product)
                    .into(ItemImage);
            ItemCount.setText(mRepository.getProductCartCount(mProduct));

        }
    }
}
