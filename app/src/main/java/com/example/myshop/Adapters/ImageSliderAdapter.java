package com.example.myshop.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.databinding.ImageSliderItemBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder> {
    List<String> mImageList;

    public ImageSliderAdapter(List<String> imageList) {
        mImageList = imageList;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageSliderItemBinding itemBinding = ImageSliderItemBinding.inflate(
                inflater,
                parent,
                false);
        return new ImageSliderViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, int position) {
        String url = mImageList.get(position);
        viewHolder.binding(url);
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageSliderItemBinding mBinding;
        String imageUrl;

        public ImageSliderViewHolder(ImageSliderItemBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void binding(String imageUrl) {
            this.imageUrl = imageUrl;
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_product)
                    .into(mBinding.imageItem);
            mBinding.executePendingBindings();
        }
    }
}
