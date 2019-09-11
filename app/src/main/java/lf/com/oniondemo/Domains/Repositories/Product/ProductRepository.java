package lf.com.oniondemo.Domains.Repositories.Product;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;


/**
 * Created by TranVoNB on 3/9/2017.
 * @Purpose: query Product's data using productDataSource which might be initialized and passed from Presentation layer.
 * The reason why we use repository call to DataSource is we intend to use 2 data sources at a same time, eg local & remote.
 * So, in more complex case, we will check cache, call local data source, if fail then use remote data source.
 * In case we only need 1 data source, so we can remove Repository classes and inject directly DataSource to UseCase constructor from Presenter layer
 */

public class ProductRepository implements MyDataSource<Product> {

    private final MyDataSource<Product> productDataSource;

    public ProductRepository( MyDataSource<Product> productDataSource){
        this.productDataSource = productDataSource;
    }

    @Override
    public Observable<Boolean> delete( String productID) {
        return productDataSource.delete(productID);
    }

    @Override
    public Observable<String> insert(Product newProduct) {
        return productDataSource.insert(newProduct);
    }

    @Override
    public Observable<Boolean> update( Product newProduct) {
        return productDataSource.update(newProduct);
    }

    @Override
    public Observable<Product> getByID( String productID) {
        return productDataSource.getByID(productID);
    }

    @Override
    public Observable<List<Product>> getAll() {
        return productDataSource.getAll();
    }

    @Override
    public Observable<List<Product>> getAll(List<ConditionPair> filterOptions) {
        return productDataSource.getAll(filterOptions);
    }
}
