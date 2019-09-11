package lf.com.oniondemo.Domains.Repositories.Product;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.BaseModel;
import lf.com.oniondemo.Domains.Models.Product;
import io.reactivex.Observable;

/**
 * Created by tranvonb on 3/9/2017.
 */

public interface MyDataSource<M extends BaseModel> {
    Observable<List<M>> getAll();
    Observable<M> getByID( String ID);
    Observable<Boolean> update( M newItem);
    Observable<Boolean> insert( M newItem);
    Observable<Boolean> delete( String ID);
}
