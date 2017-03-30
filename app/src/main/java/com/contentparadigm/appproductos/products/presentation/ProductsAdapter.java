package com.contentparadigm.appproductos.products.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 27/03/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Product> mProducts;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public ImageView featuredImage;
        public TextView unitsInStock;

        private ProductItemListener mItemListener;

        public ProductsHolder(View itemView, ProductItemListener listener) {
            super(itemView);
            mItemListener = listener;
            name = (TextView) itemView.findViewById();
            price = (TextView) itemView.findViewById();
            unitsInStock = (TextView) itemView.findViewById();
            featuredImage = (ImageView) itemView.findViewById();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Product product = getItem(position);

        }
    }

    public interface ProductItemListener {
        void onProductClick(Product clickedNote);

    }
}
