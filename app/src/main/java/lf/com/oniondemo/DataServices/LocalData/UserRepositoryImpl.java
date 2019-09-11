package lf.com.oniondemo.DataServices.LocalData;

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
    public Observable<User> getByID( String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update( User newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(User newItem) {
        return null;
    }

    @Override
    public Observable<Boolean> delete( String ID) {
        return null;
    }

    @Override
    public Observable<List<User>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }
}
