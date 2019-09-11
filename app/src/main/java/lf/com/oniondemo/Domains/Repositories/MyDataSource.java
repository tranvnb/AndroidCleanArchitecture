package lf.com.oniondemo.Domains.Repositories;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import lf.com.oniondemo.Domains.Models.BaseModel;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import io.reactivex.Observable;

/**
 * Created by TranVoNB on 3/9/2017.
 */

public interface MyDataSource<M extends BaseModel> {
    Observable<List<M>> getAll();
    Observable<M> getByID( String ID);
    Observable<Boolean> update( M newItem);
    Observable<String> insert(M newItem);
    Observable<Boolean> delete( String ID);
    Observable<List<M>> getAll(List<ConditionPair> filterOptions);
}
