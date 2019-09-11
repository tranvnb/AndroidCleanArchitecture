package lf.com.oniondemo.DataServices.RemoteData;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class ReviewRepositoryImpl implements MyDataSource<Review> {
    @Override
    public Observable<List<Review>> getAll() {
        return null;
    }

    @Override
    public Observable<Review> getByID(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(@NonNull Review newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(@NonNull Review newItem) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<List<Review>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }
}
