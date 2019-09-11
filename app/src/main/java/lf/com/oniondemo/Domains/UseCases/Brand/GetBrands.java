package lf.com.oniondemo.Domains.UseCases.Brand;

import android.support.annotation.NonNull;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetBrands extends UseCase<GetBrands.RequestValue, GetBrands.ResponseValue> {
    private final MyDataSource<Brand> brandRepository;

    public GetBrands( MyDataSource<Brand> brandRepository) {
        super(Schedulers.io());
        this.brandRepository = brandRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            brandRepository.getAll()
                    .subscribe(brands -> {
                        if(requestValue.getCallback() != null){
                            brands = requestValue.getCallback().filter(brands);
                        }
                        e.onNext(new ResponseValue(brands));
                    }
                    ,throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
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

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final List<Brand> brandList;

        public ResponseValue(List<Brand> brandList) {
            this.brandList = brandList;
        }

        public List<Brand> getBrandList() {
            return brandList;
        }
    }
}
