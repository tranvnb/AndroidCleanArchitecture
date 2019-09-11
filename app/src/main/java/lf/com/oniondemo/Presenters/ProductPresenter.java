package lf.com.oniondemo.Presenters;

/**
 * Created by tranvonb on 3/14/2017.
 */
public interface ProductPresenter extends IPresenter{
    void fetchBrandList();

    void fetchProductList(String brandId);

    void searchProduct(String productId);

    void fetchAllProduct();
}
