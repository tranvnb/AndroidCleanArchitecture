package lf.com.oniondemo.Domains.UseCases.Product;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by TranVoNB on 3/9/2017.
 * @Purpose: this Delete Product use case will be init with non-null product repository(ProductRepository), which is used to query product's data
 * This class (executeUseCase in specific) implement logic of UseCase
 */

public class DeleteProduct extends UseCase<DeleteProduct.RequestValue, DeleteProduct.ResponseValue> {

    private final MyDataSource<Product> productRepository;

    public DeleteProduct( MyDataSource<Product> productRepository) {
        super(Schedulers.io());
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(final RequestValue requestValue) {
//        return Observable.create(new ObservableOnSubscribe<ResponseValue>() {
//            @Override
//            public void subscribe(ObservableEmitter<ResponseValue> emitter) throws Exception {
//                productRepository.delete(requestValue.getProduct()
//                        .subscribe(new Consumer<Boolean>() {
//                            @Override
//                            public void accept(Boolean aBoolean) throws Exception {
//                                emitter.onNext(new ResponseValue(aBoolean));
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                emitter.onError(throwable);
//                            }
//                        })
//            }
//        });
        return Observable.create(emitter -> productRepository.delete(requestValue.getProductID())
                .subscribe(aBoolean -> {
                    emitter.onNext(new ResponseValue(aBoolean));
                }, throwable -> emitter.onError(throwable)));
    }

    public static final class RequestValue implements UseCase.RequestValue{

        private final String productID;

        public RequestValue( String productID){
            this.productID = productID;
        }

        public String getProductID(){
            return productID;
        }


    }

    public static final class ResponseValue implements UseCase.ResponseValue{

        private final boolean result;
        public ResponseValue(boolean result){
            this.result = result;
        }

        public boolean getResult(){
            return result;
        }
    }

}
