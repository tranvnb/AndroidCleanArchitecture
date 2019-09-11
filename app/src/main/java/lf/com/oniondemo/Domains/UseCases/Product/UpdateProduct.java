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

public class UpdateProduct extends UseCase<UpdateProduct.RequestValue, UpdateProduct.ResponseValue> {

    private final MyDataSource<Product> productRepository;

    public UpdateProduct( MyDataSource<Product> productRepository) {
        super(Schedulers.io());
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(emitter -> {
            productRepository.update(requestValue.getProduct())
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

        private final Boolean result;

        public ResponseValue(Boolean aBoolean) {
            this.result = aBoolean;
        }

        public Boolean getResult() {
            return result;
        }
    }

}
