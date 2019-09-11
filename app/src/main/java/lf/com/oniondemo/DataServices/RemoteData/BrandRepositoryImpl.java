package lf.com.oniondemo.DataServices.RemoteData;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class BrandRepositoryImpl implements MyDataSource<Brand> {
    @Override
    public Observable<List<Brand>> getAll() {
        return null;
    }

    @Override
    public Observable<Brand> getByID(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(@NonNull Brand newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(@NonNull Brand newItem) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<List<Brand>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }
}
