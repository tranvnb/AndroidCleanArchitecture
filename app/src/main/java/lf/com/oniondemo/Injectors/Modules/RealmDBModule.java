package lf.com.oniondemo.Injectors.Modules;

import dagger.Module;
import dagger.Provides;
import lf.com.oniondemo.DataServices.LocalData.BrandRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ReviewRepositoryImpl;

/**
 * Created by tranvonb on 3/14/2017.
 */

@Module
public class RealmDBModule {

    @Provides
    public ProductRepositoryImpl provideProductRepositoryImpl(){
        return new ProductRepositoryImpl();
    }

    @Provides
    public BrandRepositoryImpl provideBrandRepositoryImpl(){
        return new BrandRepositoryImpl();
    }

    @Provides
    public ReviewRepositoryImpl provideReviewRepository(){return new ReviewRepositoryImpl();}


}
