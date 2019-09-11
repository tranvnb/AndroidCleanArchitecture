package lf.com.oniondemo.Domains.UseCases.Product;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class InsertProduct extends UseCase<InsertProduct.RequestValue, InsertProduct.ResponseValue> {
    private final MyDataSource<Product> productRepository;

    public InsertProduct( MyDataSource<Product> productRepository) {
        super(Schedulers.io());
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(emitter -> {
            productRepository.insert(requestValue.getProduct())
                    .subscribe(aBoolean -> {emitter.onNext(new ResponseValue(aBoolean));},
                            throwable -> emitter.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final Product product;

        public RequestValue(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }
    public static final class ResponseValue implements UseCase.ResponseValue {

        private final String result;

        public ResponseValue(String aBoolean) {
            this.result = aBoolean;
        }

        public String getResult() {
            return result;
        }
    }
}
