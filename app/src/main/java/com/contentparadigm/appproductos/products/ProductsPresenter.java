package com.contentparadigm.appproductos.products;

import com.contentparadigm.appproductos.data.products.ProductsRepository;
import com.contentparadigm.appproductos.products.domain.critera.PagingProductCriteria;
import com.contentparadigm.appproductos.products.domain.critera.ProductCriteria;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 04/04/2017.
 */

public class ProductsPresenter implements ProductsMvp.Presenter {

    private final ProductsRepository mProductsRepository;
    private final ProductsMvp.View mProductsView;

    private boolean isFirstLoad = true;
    private int mCurrentPage = 1;
    public static final int PRODUCTS_LIMIT = 20;

    public ProductsPresenter(ProductsRepository productsRepository, ProductsMvp.View productsView) {
        mProductsRepository = productsRepository;
        mProductsView = productsView;
    }

    @Override
    public void loadProducts(boolean reload) {
        ProductCriteria criteria = new PagingProductCriteria(mCurrentPage, PRODUCTS_LIMIT);
        final boolean reallyReload = reload || isFirstLoad;
        if (reallyReload) {
            mProductsView.showLoadingState(true);
            mProductsRepository.refreshProducts();
            mCurrentPage = 1;
        } else {
            mProductsView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }
        mProductsRepository.getProducts(new ProductsRepository.GetProductsCallBack() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                mProductsView.showLoadingState(false);
                processProducts(products, reallyReload);
                isFirstLoad = false;
            }

            @Override
            public void onDataNotAvailable(String error) {
                mProductsView.showLoadingState(false);
                mProductsView.showLoadMoreIndicator(false);
                mProductsView.showProductError(error);
            }
        }, criteria);
    }

    private void processProducts(List<Product> products, boolean reload) {
        if (products.isEmpty()){
            if (reload) mProductsView.showEmptyState();
            else mProductsView.showLoadMoreIndicator(false);
            mProductsView.allowMoreData(false);

        }
        else {
            if (reload) mProductsView.showProducts(products);
            else {
                mProductsView.showLoadMoreIndicator(false);
                mProductsView.showProductPage(products);
            }
            mProductsView.allowMoreData(true);
        }
    }
}
