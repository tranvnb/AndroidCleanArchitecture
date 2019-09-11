package lf.com.oniondemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.Random;

import lf.com.oniondemo.DataServices.LocalData.BrandRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.RealmDto.BrandRealm;
import lf.com.oniondemo.DataServices.LocalData.RealmDto.ProductRealm;
import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Models.ProductStatus;
import lf.com.oniondemo.Injectors.Components.AppComponent;
import lf.com.oniondemo.Injectors.Components.DaggerAppComponent;
import lf.com.oniondemo.Injectors.Modules.AppModule;
import lf.com.oniondemo.Utils.RealmHelper;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by tranvonb on 3/13/2017.
 */

public class OnionDemoApp extends Application {
    private static Context baseContext;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        baseContext = getBaseContext();
        setupDependencyGraph();
        initRealmConfiguration(this.getApplicationContext());
    }

    private void setupDependencyGraph(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.autoInjectTo(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public static OnionDemoApp getInstance(Context context){
        return (OnionDemoApp) context.getApplicationContext();
    }

    private void initRealmConfiguration(Context context){
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .initialData(realm -> {
                    initBrandAndProductList(realm);
                })
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

//        //TODO: this is for testing only
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<BrandRealm> results = realm1.where(BrandRealm.class).findAll();
            Iterator<BrandRealm> it = results.iterator();
            while (it.hasNext()){
                BrandRealm routerRealm = it.next();
                Log.d("BrandRealm", "routerRealm.getId(): " + routerRealm.getId());
                Log.d("BrandRealm", "routerRealm.getName(): " + routerRealm.getName());
                Log.d("BrandRealm", "routerRealm.getDescription(): " + routerRealm.getDescription());
                Log.d("BrandRealm", "routerRealm.getProducts(): " + routerRealm.getProducts());
            }
        });
    }

    private void initBrandAndProductList(Realm realm){
        realm.executeTransactionAsync(realm1 -> {
            Random random = new Random();
            String[] brandArr = new String[] {
                    "Nike","Adidas", "Gucci","DC","Geox","Reebok","Jara", "Uniqlo"
            };

            String[] productArr = new String[] {
                    "T-shirt", "Short","Glasses","Coat","Jewelry","Pull", "Trausers"
            };

            for (String item :
                    brandArr) {
                BrandRealm brand = new BrandRealm();
                brand.setProducts(new RealmList<ProductRealm>());
                brand.setName(item);
                brand.setBrandIconIndex(random.nextInt(9));
                brand.setDescription(getString(R.string.brand_description) + item);
                brand.setId(RealmHelper.generateUUID());

                for (String name :
                                            productArr) {
                    ProductRealm product = new ProductRealm();
                    product.setId(RealmHelper.generateUUID());
                    product.setName(name);
                    product.setAverageRating(2.5);
                    product.setDescription(getString(R.string.product_description) + name);
                    product.setBrandId(brand.getId());
                    product.setBrandName(brand.getName());
                    product.setProductIconIndex(random.nextInt(9));
                    product.setAvailableStatus(ProductStatus.INSTOCK);
                    brand.getProducts().add(product);
                }
                realm1.copyToRealmOrUpdate(brand);
            }
            BrandRealm brandRealm = realm1.where(BrandRealm.class).findFirst();
            if(brandRealm != null){
                ProductRealm product = new ProductRealm();
                product.setId(getString(R.string.qrcode_product_exist));
                product.setName("Shoes");
                product.setBrandName(brandRealm.getName());
                product.setAverageRating(2.5);
                product.setProductIconIndex(random.nextInt(9));
                product.setDescription(R.string.product_description + "Shoes");
                product.setBrandId(brandRealm.getId());
                product.setAvailableStatus(ProductStatus.INSTOCK);
                brandRealm.getProducts().add(product);
            }
        });

    }

    public static Context getContext() {
        return baseContext;
    }
}
