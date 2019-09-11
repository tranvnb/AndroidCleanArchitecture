package lf.com.oniondemo.UI.Activities;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.UI.IView;

/**
 * Created by tranvonb on 3/14/2017.
 */
public interface ProductView extends IView {

    void updateBrandList(List<Brand> brands);

    void updateProductList(List<Product> productList);

    void onProductItemClick(Product item);

    void showProductNotExist(String productId);
}
