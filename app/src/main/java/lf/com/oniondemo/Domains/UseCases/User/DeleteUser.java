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

public class DeleteUser extends UseCase<DeleteUser.RequestValue, DeleteUser.ResponseValue> {

    private final MyDataSource<User> userRepository;

    public DeleteUser( MyDataSource<User> userRepository) {
        super(Schedulers.io());
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            userRepository.delete(requestValue.getUserID())
                    .subscribe(aBoolean -> e.onNext(new ResponseValue(aBoolean))
                    ,throwable -> e.onError(throwable));
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
        private final Boolean result;

        public ResponseValue(Boolean result) {
            this.result = result;
        }

        public Boolean getResult() {
            return result;
        }
    }
}
