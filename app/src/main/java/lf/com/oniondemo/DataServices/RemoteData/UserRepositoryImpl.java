package lf.com.oniondemo.DataServices.RemoteData;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.User;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class UserRepositoryImpl implements MyDataSource<User> {
    @Override
    public Observable<List<User>> getAll() {
        return null;
    }

    @Override
    public Observable<User> getByID(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(@NonNull User newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(@NonNull User newItem) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(@NonNull String ID) {
        return null;
    }

    @Override
    public Observable<List<User>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }
}
