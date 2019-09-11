package lf.com.oniondemo.Injectors.Modules;

import dagger.Module;
import dagger.Provides;
import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ReviewRepositoryImpl;
import lf.com.oniondemo.Presenters.ProductDetailPresenter;
import lf.com.oniondemo.Presenters.ProductDetailPresenterImpl;
import lf.com.oniondemo.UI.Activities.ProductDetailView;
import lf.com.oniondemo.UI.Adapters.ReviewListAdapter;

/**
 * Created by tranvonb on 3/14/2017.
 */
@Module(includes = RealmDBModule.class)
public class ProductDetailModule {

    private ProductDetailView view;

    public ProductDetailModule(ProductDetailView view){
        this.view = view;
    }

    @Provides
    public ProductDetailView provideProductDetailView(){
        return view;
    }

    @Provides
    public ProductDetailPresenter provideProductDetailPresenter(ProductDetailView view, ProductRepositoryImpl productRepository, ReviewRepositoryImpl reviewRepository){
        return new ProductDetailPresenterImpl(view, productRepository, reviewRepository);
    }

    @Provides
    public ReviewListAdapter provideReviewListAdapter(){
        return new ReviewListAdapter();
    }

}
