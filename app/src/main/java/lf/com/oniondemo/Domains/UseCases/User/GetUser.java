package lf.com.oniondemo.Domains.UseCases.User;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.User;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 * We dont need repository anymore, directly use datasource because we only have 1 datasource.
 */

public class GetUser extends UseCase<GetUser.RequestValue, GetUser.ResponseValue> {

    private final MyDataSource<User> userRepository;

    public GetUser( MyDataSource<User> userRepository) {
        super(Schedulers.io());
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(emitter -> {
            userRepository.getByID(requestValue.getUserID())
                    .subscribe(user -> {emitter.onNext(new ResponseValue(user));},
                            throwable -> emitter.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final String userID;

        public RequestValue(String userID) {
            this.userID = userID;
        }

        public String getUserID() {
            return userID;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final User user;

        public ResponseValue(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

}
