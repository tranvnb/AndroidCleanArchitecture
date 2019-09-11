package lf.com.oniondemo.Domains.UseCases.Brand;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class DeleteBrand extends UseCase<DeleteBrand.RequestValue, DeleteBrand.ResponseValue> {
    private final MyDataSource<Brand> brandRepository;

    public DeleteBrand( MyDataSource<Brand> brandRepository) {
        super(Schedulers.io());
        this.brandRepository = brandRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            brandRepository.delete(requestValue.getBrandID())
                    .subscribe(aBoolean -> e.onNext(new ResponseValue(aBoolean)),
                            throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final String brandID;

        public RequestValue(String brandID) {
            this.brandID = brandID;
        }

        public String getBrandID() {
            return brandID;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private Boolean result;

        public ResponseValue(Boolean result) {
            this.result = result;
        }

        public Boolean getResult() {
            return result;
        }
    }
}
