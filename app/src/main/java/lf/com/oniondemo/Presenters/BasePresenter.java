package lf.com.oniondemo.Presenters;

import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tranvonb on 3/14/2017.
 */

public abstract class BasePresenter implements IPresenter {
    private CompositeDisposable compositeDisposable;

    protected CompositeDisposable getCompositeDisposable(){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void onDestroy(){
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

}
