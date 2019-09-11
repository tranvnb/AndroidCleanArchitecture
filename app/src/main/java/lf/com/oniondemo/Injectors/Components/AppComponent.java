package lf.com.oniondemo.Injectors.Components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import lf.com.oniondemo.Injectors.Modules.AppModule;
import lf.com.oniondemo.OnionDemoApp;

/**
 * Created by TranVoNB on 3/14/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void autoInjectTo(OnionDemoApp app);

    SharedPreferences getSharedPreferences();

}
