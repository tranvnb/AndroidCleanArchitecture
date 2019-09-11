package lf.com.oniondemo.Domains.UseCases.Brand;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetBrand extends UseCase<GetBrand.RequestValue, GetBrand.ResponseValue> {
    private final MyDataSource<Brand> brandRepository;

    public GetBrand(MyDataSource<Brand> brandRepository) {
        super(Schedulers.io());
        this.brandRepository = brandRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e ->  {
            brandRepository.getByID(requestValue.getBrandID())
                    .subscribe(brand -> e.onNext(new ResponseValue(brand))
                    ,throwable -> e.onError(throwable));
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
        private final Brand brand;

        public ResponseValue(Brand brand) {
            this.brand = brand;
        }

        public Brand getBrand() {
            return brand;
        }
    }
}
