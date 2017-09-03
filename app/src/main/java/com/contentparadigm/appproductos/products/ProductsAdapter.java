package com.contentparadigm.appproductos.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.contentparadigm.appproductos.R;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 27/03/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>
        implements DataLoading {

    private List<Product> mProducts;
    private ProductItemListener mItemListener;
    private boolean mLoading = false;
    private boolean mMoreData = false;
    private final static int TYPE_PRODUCT = 1;
    private final static int TYPE_LOADING_MORE = 2;

    @Override
    public int getItemViewType(int position) {
        if ( position < getDataItemCount() && getDataItemCount() > 0) return TYPE_PRODUCT;
        return TYPE_LOADING_MORE;
    }

    @Override
    public boolean isLoadingData() {
        return mLoading;
    }

    @Override
    public boolean isThereMoreData() {
        return mMoreData;
    }

    public ProductsAdapter(List<Product> mProducts, ProductItemListener mItemListener) {
        setList(mProducts);
        this.mItemListener = mItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == TYPE_LOADING_MORE) {
            view = inflater.inflate(R.layout.item_loading_footer, parent, false);
            return new LoadingMoreHolder(view);
        }
        view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductsHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_PRODUCT:
                Product product = mProducts.get(position);
                ProductsHolder productsHolder = (ProductsHolder) holder;
                productsHolder.price.setText(product.getFormatedPrice());
                productsHolder.name.setText(product.getName());
                Glide.with(holder.itemView.getContext())
                        .load(product.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(productsHolder.featuredImage);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }
    }

    private void bindLoadingViewHolder (LoadingMoreHolder viewHolder, int position) {
        viewHolder.progress.setVisibility((position > 0 && mLoading && mMoreData) ? View.VISIBLE : View.INVISIBLE);
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

    private int getLoadingMoreItemPosition() {
        return mLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void dataStatedLoading (){
        if (mLoading) return;
        mLoading = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    public void dataFinishedLoading () {
        if (!mLoading) return;
        mLoading = false;
        notifyItemRemoved(getLoadingMoreItemPosition());
    }

    public void setmMoreData (boolean more) {
        mMoreData = more;
    }

    public interface ProductItemListener {
        void onProductClick(Product clickedNote);

    }
    private class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;
        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progress = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }
}

