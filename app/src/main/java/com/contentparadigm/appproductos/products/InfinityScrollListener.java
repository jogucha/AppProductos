package com.contentparadigm.appproductos.products;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jorge.gutierrez on 30/04/2017.
 */

public abstract class InfinityScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;
    private final LinearLayoutManager mLayoutManager;
    private final DataLoading mDataLoading;

    public InfinityScrollListener(DataLoading mDataLoading, LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        this.mDataLoading = mDataLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if ( dy < 0  || mDataLoading.isLoadingData() || !mDataLoading.isThereMoreData()) return;
        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (( totalItemCount - visibleItemCount ) <= (firstVisibleItem + VISIBLE_THRESHOLD)) onLoadMore();

        //super.onScrolled(recyclerView, dx, dy);
    }

    public abstract void onLoadMore();
}
