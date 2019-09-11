package lf.com.oniondemo.Domains.UseCases.Brand;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class InsertBrand extends UseCase<InsertBrand.RequestValue, InsertBrand.ResponseValue> {
    private final MyDataSource<Brand> brandRepository;

    public InsertBrand(MyDataSource<Brand> brandRepository) {
        super(Schedulers.io());
        this.brandRepository = brandRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            brandRepository.insert(requestValue.getBrand())
                    .subscribe(aBoolean -> e.onNext(new ResponseValue(aBoolean)),
                            throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final Brand brand;

        public RequestValue(Brand brand) {
            this.brand = brand;
        }

        public Brand getBrand() {
            return brand;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final String result;

        public ResponseValue(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
