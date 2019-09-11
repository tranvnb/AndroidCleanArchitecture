package lf.com.oniondemo.DataServices.RemoteData;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class ProductRepositoryImpl implements MyDataSource<Product> {

    private ProductApi productApi;

    public ProductRepositoryImpl() {
        this.productApi = RetrofitClient.createService(ProductApi.class);
    }

    @Override
    public Observable<List<Product>> getAll() {
        return null;
    }

    @Override
    public Observable<Product> getByID(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(@NonNull Product newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(@NonNull Product newItem) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<List<Product>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }
}
