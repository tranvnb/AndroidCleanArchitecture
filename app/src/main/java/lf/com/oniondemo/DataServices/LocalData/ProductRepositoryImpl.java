package lf.com.oniondemo.DataServices.LocalData;


import java.util.ArrayList;
import java.util.List;

import lf.com.oniondemo.DataServices.LocalData.RealmDto.BrandRealm;
import lf.com.oniondemo.DataServices.LocalData.RealmDto.ProductRealm;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.R;
import lf.com.oniondemo.Utils.RealmHelper;
import lf.com.oniondemo.OnionDemoApp;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class ProductRepositoryImpl implements MyDataSource<Product> {

    @Override
    public Observable<List<Product>> getAll() {
        Realm realm = Realm.getDefaultInstance();

        return Observable.create(e -> {
            realm.executeTransactionAsync(realm1 -> {
                RealmResults<ProductRealm> realmResults = realm1.where(ProductRealm.class).findAll();
                if(realmResults != null){
                    List<Product> list = new ArrayList<Product>();
                    for (ProductRealm productRealm :
                            realmResults) {
                        list.add(mapToDomain(productRealm));
                    }
                    e.onNext(list);
                }else {
                    e.onError(new Throwable("There is no product in database"));
                }
            });
        });
    }

    @Override
    public Observable<Product> getByID(String ID) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.create(e -> {
            realm.executeTransactionAsync(realm1 -> {
                ProductRealm productRealm = realm1.where(ProductRealm.class).equalTo("Id",(ID)).findFirst();
                if (productRealm != null){
                    Product product = mapToDomain(productRealm);
                    e.onNext(product);
                }else {
                    e.onError(new Throwable(OnionDemoApp.getContext().getString(R.string.no_product_with_id) + ID));
                }
            });
        });

    }

    @Override
    public Observable<Boolean> update( Product newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(Product newItem) {
        return Observable.create(e -> {
            final String[] key = {""};
            final Realm[] realm = {Realm.getDefaultInstance()};
            realm[0].executeTransactionAsync(realm1 -> {
                BrandRealm brandRealm = realm1.where(BrandRealm.class).equalTo("Id", (newItem.getBrandId())).findFirst();
                if(brandRealm != null) {
                    key[0] = RealmHelper.generateUUID();
                    ProductRealm productRealm = mapToRealm(newItem);
                    productRealm.setId(key[0]);
                    if(brandRealm.getProducts() == null){
                        brandRealm.setProducts(new RealmList<>());
                    }
                    brandRealm.getProducts().add(productRealm);
                    e.onNext(productRealm.getId());
                }else {
                    e.onError(new Throwable(OnionDemoApp.getContext().getString(R.string.no_brand_for_product) + newItem.getName()));
                }
            });
        });
    }

    @Override
    public Observable<Boolean> delete( String ID) {
        return null;
    }

    @Override
    public Observable<List<Product>> getAll(List<ConditionPair> filterOptions) {
        Realm realm = Realm.getDefaultInstance();

        return Observable.create(e -> {
            List<Product> result = new ArrayList<Product>();
            realm.executeTransactionAsync(realm1 -> {
                RealmQuery query = realm1.where(ProductRealm.class);
                for (ConditionPair condition :
                        filterOptions) {
                    switch (condition.getOperator()){
                        case "==":
                            query = query.equalTo("brandId", (condition.getValue()));
//                            query = query.equalTo(condition.getKey(), condition.getValue());
                            break;
//                case "<=":
//                    query = query.lessThan(condition.getKey(), ((condition.getaClass())condition.getValue());
//                    break;
                    }
                }
                RealmResults<ProductRealm> productRealms = query.findAll();
                List<ProductRealm> list = realm1.copyFromRealm(productRealms);
                if(list != null) {
                    for (ProductRealm item :
                            list) {
                        result.add(mapToDomain(item));
                    }
                    e.onNext(result);
                }else {
                    e.onError(new Throwable(OnionDemoApp.getContext().getString(R.string.no_product_match_criteria)));
                }
            });
        });
    }

    private ProductRealm mapToRealm(Product product){
        ProductRealm productRealm = new ProductRealm();
        productRealm.setName(product.getName());
        productRealm.setDescription(product.getDescription());
        productRealm.setAvailableStatus(product.getAvailableStatus());
        productRealm.setAverageRating(product.getAverageRating());
        if(product.getBrandId() != null && "".compareTo(product.getBrandId() )!= 0) {
            productRealm.setBrandId((product.getBrandId()));
        }
        productRealm.setBrandName(product.getBrandName());

        productRealm.setProductIconIndex(product.getProductIconIndex());

        return productRealm;
    }

    private  Product mapToDomain(ProductRealm productRealm){
        Product product = new Product();
        product.setId(productRealm.getId());
        product.setName(productRealm.getName());
        product.setDescription(productRealm.getDescription());
        product.setAverageRating(productRealm.getAverageRating());
        product.setBrandId(productRealm.getBrandId());
        product.setBrandName(productRealm.getBrandName());

        product.setProductIconIndex(productRealm.getProductIconIndex());

        return product;
    }
}
