package lf.com.oniondemo.Injectors.Components;

import dagger.Component;
import lf.com.oniondemo.Injectors.ActivityScope;
import lf.com.oniondemo.Injectors.Modules.ProductDetailModule;
import lf.com.oniondemo.UI.Activities.ProductDetailActivity;

/**
 * Created by TranVo on 3/15/2017.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = {ProductDetailModule.class} )
public interface ProductDetailComponent {
    void autoInjectTo(ProductDetailActivity activity);
}
