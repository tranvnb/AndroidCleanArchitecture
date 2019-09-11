package lf.com.oniondemo.Domains.UseCases.User;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.User;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetUsers extends UseCase<GetUsers.RequestValue, GetUsers.ResponseValue> {
    private final MyDataSource<User> userRepository;

    public GetUsers( MyDataSource<User> userRepository) {
        super(Schedulers.io());
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(emitter -> {
            if (requestValue.getFilterType() == FilterType.LISTITEM) {
                userRepository.getAll()
                        .subscribe(users -> {
                                    if (requestValue.getCallback() != null) {
                                        users = requestValue.getCallback().filter(users);
                                    }
                                    emitter.onNext(new ResponseValue(users));
                                },
                                throwable -> emitter.onError(throwable));
            }
        });
    }

    public static final class RequestValue implements UseCase.RequestValue {
        private final FilterType filterType;
        private final FilterCallback callback;

        public RequestValue(FilterType filterType, FilterCallback callback) {
            this.filterType = filterType;
            this.callback = callback;
        }

        public FilterType getFilterType() {
            return filterType;
        }

        public FilterCallback getCallback() {
            return callback;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<User> userList;

        public ResponseValue(List<User> userList) {
            this.userList = userList;
        }

        public List<User> getUserList() {
            return userList;
        }
    }
}
