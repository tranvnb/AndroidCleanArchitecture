package lf.com.oniondemo.Injectors.Modules;

import android.widget.ArrayAdapter;

import dagger.Module;
import dagger.Provides;
import lf.com.oniondemo.DataServices.LocalData.BrandRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.Presenters.ProductPresenter;
import lf.com.oniondemo.Presenters.ProductPresenterImpl;
import lf.com.oniondemo.R;
import lf.com.oniondemo.UI.Adapters.AutoCompleteAdapter;
import lf.com.oniondemo.UI.Adapters.ProductListAdapter;
import lf.com.oniondemo.UI.Activities.ProductView;

/**
 * Created by tranvonb on 3/14/2017.
 */

@Module(includes = {RealmDBModule.class})
public class ProductModule {

    private ProductView view;

    public ProductModule(ProductView view){
        this.view = view;
    }

    @Provides
    public ProductPresenter provideProductPresenter(ProductView view, ProductRepositoryImpl productRepository, BrandRepositoryImpl brandRepository){
        return new ProductPresenterImpl(view, productRepository, brandRepository);
    }

    @Provides
    public ProductView provideProductView(){
        return view;
    }

    @Provides
    public ArrayAdapter provideAutoCompleteAdapter(){
        return new AutoCompleteAdapter(view.getCurrentContext(), R.layout.brand_item_dropdown, R.id.autoCompleteItem);
    }

    @Provides
    public ProductListAdapter provideProductListAdapter(){
        return new ProductListAdapter();
    }
}
