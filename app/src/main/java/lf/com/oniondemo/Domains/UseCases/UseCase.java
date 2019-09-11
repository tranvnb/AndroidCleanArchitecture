package lf.com.oniondemo.Domains.UseCases;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by TranVoNB on 3/9/2017.
 */

public abstract class UseCase<RQ extends UseCase.RequestValue, RP extends UseCase.ResponseValue> {

    private final Scheduler backgroundScheduler;

    protected UseCase(Scheduler backgroundScheduler){
        this.backgroundScheduler = backgroundScheduler;
    }

    public Observable<RP> run(RQ requestValue){
        return  executeUseCase(requestValue)
                .subscribeOn(backgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected abstract Observable<RP> executeUseCase(RQ requestValue);

    public interface RequestValue {
    }

    public interface ResponseValue {
    }
}
