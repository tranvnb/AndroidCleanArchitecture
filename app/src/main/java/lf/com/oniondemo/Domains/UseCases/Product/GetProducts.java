package lf.com.oniondemo.Domains.UseCases.Product;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetProducts extends UseCase<GetProducts.RequestValue, GetProducts.ResponseValue> {

    private final MyDataSource<Product> productRepository;

    public GetProducts( MyDataSource<Product> productRepository){
        super(Schedulers.io());
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(emitter -> {
            if (requestValue.getFilterType() == FilterType.LISTITEM){
                productRepository.getAll()
                .subscribe(products -> {
                    if (requestValue.getCallback() != null){
                        products = requestValue.getCallback().<Product, List<Product>>filter(products);
                    }
                    emitter.onNext(new ResponseValue(products));
                },
                        throwable -> emitter.onError(throwable));
            }else if (requestValue.getFilterType() == FilterType.QUERYABLE){
                //If using sql query then append filter criteria to query string then execute
                List<ConditionPair> filterOptions = requestValue.getCallback().<Product, List<ConditionPair>>filter(Collections.emptyList());
                productRepository.getAll(filterOptions)
                        .subscribe(products1 -> {emitter.onNext(new ResponseValue(products1));},
                                throwable -> emitter.onError(throwable));
            }

        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final FilterType filterType;
        private final FilterCallback callback;
        public RequestValue(FilterType filterType, FilterCallback callback){
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
        private final List<Product> productList;

        public ResponseValue(List<Product> productList) {
            this.productList = productList;
        }

        public List<Product> getProductList() {
            return productList;
        }
    }
}
