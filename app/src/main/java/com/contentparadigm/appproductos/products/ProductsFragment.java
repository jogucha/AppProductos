package com.contentparadigm.appproductos.products;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.contentparadigm.appproductos.R;
import com.contentparadigm.appproductos.di.DependencyProvider;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment implements ProductsMvp.View {


    private String mParam1;
    private String mParam2;

    private ProductsAdapter mProductsAdapter;
    private ProductsPresenter mProductsPresenter;
    private RecyclerView mProductList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private ProductsAdapter.ProductItemListener mProductItemListener = new ProductsAdapter.ProductItemListener() {
        @Override
        public void onProductClick(Product clickedNote) {

        }
    };

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Product>(0), mProductItemListener);
        mProductsPresenter = new ProductsPresenter(DependencyProvider.provideProductsRepository(getActivity()), this);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        mProductList = (RecyclerView) root.findViewById(R.id.products_list);
        mEmptyView = root.findViewById(R.id.noProducts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        SetUpProductList();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) mProductsPresenter.loadProducts(false);
    }


    private void SetUpProductList(){
        mProductList.setAdapter(mProductsAdapter);
        final LinearLayoutManager layoutManager = (LinearLayoutManager) mProductList.getLayoutManager();
        mProductList.addOnScrollListener(new InfinityScrollListener(mProductsAdapter, layoutManager) {
            @Override
            public void onLoadMore() {
                mProductsPresenter.loadProducts(false);
            }
        });
        mProductList.setHasFixedSize(true);
    }

    private void SetUpRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                    }
                }
        );
    }

    @Override
    public void showProducts(List<Product> products) {
        mProductsAdapter.replaceData(products);
        mProductList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if (getView() == null) return;
        mSwipeRefreshLayout.post(new Runnable(){
            public void run() {
                mSwipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showEmptyState() {
        mProductList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProductError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProductPage(List<Product> products) {
        mProductsAdapter.addData(products);
    }

    @Override
    public void showLoadMoreIndicator(boolean show) {
        if (!show) mProductsAdapter.dataFinishedLoading();
        else mProductsAdapter.dataStatedLoading();
    }

    @Override
    public void allowMoreData(boolean allow) {
        mProductsAdapter.setmMoreData(allow);

    }
}
