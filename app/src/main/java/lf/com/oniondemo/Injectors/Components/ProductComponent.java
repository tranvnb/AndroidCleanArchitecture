package lf.com.oniondemo.Injectors.Components;

import dagger.Component;
import lf.com.oniondemo.Injectors.ActivityScope;
import lf.com.oniondemo.Injectors.Modules.ProductModule;
import lf.com.oniondemo.UI.Activities.ProductActivity;

/**
 * Created by tranvonb on 3/14/2017.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = {ProductModule.class} )
public interface ProductComponent {
    void autoInjectTo(ProductActivity activity);
}
