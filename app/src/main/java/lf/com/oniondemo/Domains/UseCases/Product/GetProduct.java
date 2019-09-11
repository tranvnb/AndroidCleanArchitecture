package lf.com.oniondemo.Domains.UseCases.Product;


import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetProduct extends UseCase<GetProduct.RequestValue, GetProduct.ResponseValue> {

    private final MyDataSource<Product> productRepository;

    public GetProduct( MyDataSource<Product> productRepository) {
        super(Schedulers.io());
        this.productRepository = productRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            productRepository.getByID(requestValue.getProductID())
                    .subscribe(product -> {e.onNext(new ResponseValue(product));},
                            throwable -> e.onError(throwable));
        });
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
        private final Product product;
        public ResponseValue(Product product){this.product = product; }
        public Product getProduct(){return product;}
    }
}
