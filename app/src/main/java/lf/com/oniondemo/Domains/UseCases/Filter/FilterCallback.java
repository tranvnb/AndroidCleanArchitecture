package lf.com.oniondemo.Domains.UseCases.Filter;

import java.util.Collection;
import java.util.List;

import lf.com.oniondemo.Domains.Models.BaseModel;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public interface FilterCallback{
    //RealmResult is also implement Collection
    <M extends BaseModel, R extends Collection> R filter(Collection<M> list);
}
