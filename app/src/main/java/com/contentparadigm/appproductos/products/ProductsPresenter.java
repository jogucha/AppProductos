package com.contentparadigm.appproductos.products;

import com.contentparadigm.appproductos.data.products.ProductsRepository;

/**
 * Created by jorge.gutierrez on 04/04/2017.
 */

public class ProductsPresenter implements ProductsMvp.Presenter {

    private final ProductsRepository mProductsRepository;
    private final ProductsMvp.View mProductsView;

    private boolean isFirstLoad = true;
    private int mCurrentPage = 1;

    public ProductsPresenter(ProductsRepository productsRepository, ProductsMvp.View productsView) {
        mProductsRepository = productsRepository;
        mProductsView = productsView;
    }

    @Override
    public void loadProducts(boolean reload) {
        final boolean reallyReload = reload || isFirstLoad;
        if (reallyReload) {
            mProductsView.showLoadingState(true);
            mProductsRepository.refreshProducts();
            mCurrentPage = 1;
        } else {
            mProductsView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }
        mProductsRepository.getProducts();


    }
}
