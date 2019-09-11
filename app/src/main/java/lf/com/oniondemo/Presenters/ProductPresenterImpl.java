package lf.com.oniondemo.Presenters;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lf.com.oniondemo.DataServices.LocalData.BrandRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.Domains.Models.BaseModel;
import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Models.ProductStatus;
import lf.com.oniondemo.Domains.UseCases.Brand.GetBrands;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.Product.GetProduct;
import lf.com.oniondemo.Domains.UseCases.Product.GetProducts;
import lf.com.oniondemo.UI.Activities.ProductView;

/**
 * Created by tranvonb on 3/14/2017.
 */

public class ProductPresenterImpl extends BasePresenter implements ProductPresenter {

    private ProductView view;
    private ProductRepositoryImpl productRepository;
    private BrandRepositoryImpl brandRepository;

    public ProductPresenterImpl(ProductView view, ProductRepositoryImpl productRepository, BrandRepositoryImpl brandRepository) {
        this.view = view;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }



    @Override
    public void fetchBrandList() {
        view.showLoading();
        GetBrands.RequestValue requestValue = new GetBrands.RequestValue(FilterType.QUERYABLE, new FilterCallback() {
            @Override
            public <M extends BaseModel, R extends Collection> R filter(Collection<M> list) {
                //return list, no need to filter, otherwise return a list of ConditionPair for local/remote datasource build query
                return (R) list;
            }
        }
        );

        GetBrands mGetBrands = new GetBrands(brandRepository);
        mGetBrands.run(requestValue)
                .subscribe(responseValue -> {
                    view.updateBrandList(responseValue.getBrandList());
                    view.hideLoading();
                }, throwable -> view.onError(throwable));
    }

    @Override
    public void fetchAllProduct() {
        view.showLoading();

        GetProducts.RequestValue requestValue = new GetProducts.RequestValue(FilterType.LISTITEM, null);

        GetProducts mGetProducts = new GetProducts(productRepository);
        mGetProducts.run(requestValue)
                .subscribe(responseValue -> {
                    view.hideLoading();
                    view.updateProductList(responseValue.getProductList());
                }, throwable -> view.onError(throwable));
    }

    @Override
    public void fetchProductList(String brandId) {
        view.showLoading();
        GetProducts.RequestValue requestValue = new GetProducts.RequestValue(FilterType.QUERYABLE, new FilterCallback() {
            @Override
            public <M extends BaseModel, R extends Collection> R filter(Collection<M> list) {
                ConditionPair conditionPair = new ConditionPair();
                conditionPair.setKey("brandId");
                conditionPair.setValue(brandId);
                conditionPair.setOperator("==");
                return (R) new ArrayList(Arrays.asList(conditionPair));
            }
        });

        GetProducts mGetProducts = new GetProducts(productRepository);
        getCompositeDisposable().add(mGetProducts.run(requestValue)
                .subscribe(responseValue -> {
                            view.hideLoading();
                            view.updateProductList(responseValue.getProductList());
                        },
                        throwable -> view.onError(throwable)));
    }

    @Override
    public void searchProduct(String productId) {
        view.showLoading();
        GetProduct.RequestValue requestValue = new GetProduct.RequestValue(productId);
        GetProduct mGetProduct = new GetProduct(productRepository);
        mGetProduct.run(requestValue)
                .subscribe(responseValue -> {
                            view.hideLoading();
                            view.onProductItemClick(responseValue.getProduct());
                        },
                        throwable -> {view.showProductNotExist(productId);view.hideLoading();});
    }

}
