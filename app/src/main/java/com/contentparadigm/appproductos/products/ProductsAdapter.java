package com.contentparadigm.appproductos.products;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contentparadigm.appproductos.R;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 27/03/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Product> mProducts;
    private ProductItemListener mItemListener;

    public ProductsAdapter(List<Product> mProducts, ProductItemListener mItemListener) {
        setList(mProducts);
        this.mItemListener = mItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
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
            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
            unitsInStock = (TextView) itemView.findViewById(R.id.units_in_stock);
            featuredImage = (ImageView) itemView.findViewById(R.id.product_featured_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Product product = getItem(position);
            mItemListener.onProductClick(product);

        }

    }
    private void setList(List<Product> notes){
        mProducts = notes;
    }

    public int getDataItemCount(){
        return mProducts.size();
    }

    public Product getItem(int position){
        return mProducts.get(position);
    }

    public void replaceData(List<Product> notes){
        setList(notes);
        notifyDataSetChanged();
    }

    public void addData(List<Product> products) {
        mProducts.addAll(products);
    }

    public interface ProductItemListener {
        void onProductClick(Product clickedNote);

    }
}
