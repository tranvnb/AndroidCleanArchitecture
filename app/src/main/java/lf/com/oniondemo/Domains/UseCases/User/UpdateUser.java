package lf.com.oniondemo.Domains.UseCases.User;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.User;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class UpdateUser extends UseCase<UpdateUser.RequestValue, UpdateUser.ResponseValue> {
    private final MyDataSource<User> userRepository;

    public UpdateUser( MyDataSource<User> userRepository) {
        super(Schedulers.io());
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            userRepository.update(requestValue.getUser())
                    .subscribe(aBoolean -> {e.onNext(new ResponseValue(aBoolean));}
                    , throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final User user;

        public RequestValue(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final Boolean result;

        public ResponseValue(Boolean result) {
            this.result = result;
        }

        public Boolean getResult() {
            return result;
        }
    }
}
