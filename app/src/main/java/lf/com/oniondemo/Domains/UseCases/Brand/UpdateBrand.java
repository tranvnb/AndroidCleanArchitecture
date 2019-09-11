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

public class UpdateBrand extends UseCase<UpdateBrand.RequestValue, UpdateBrand.ResponseValue> {
    private MyDataSource<Brand> brandRepository;

    public UpdateBrand( MyDataSource<Brand> brandRepository) {
        super(Schedulers.io());
        this.brandRepository = brandRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return null;
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
        private final Boolean result;

        public ResponseValue(Boolean result) {
            this.result = result;
        }

        public Boolean getResult() {
            return result;
        }
    }
}
