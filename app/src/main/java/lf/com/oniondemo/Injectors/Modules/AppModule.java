package lf.com.oniondemo.Injectors.Modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lf.com.oniondemo.OnionDemoApp;
import io.realm.Realm;

/**
 * Created by TranVoNB on 3/14/2017.
 */
@Module
public class AppModule {

    private OnionDemoApp app;

    public AppModule(OnionDemoApp app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Application provideApplication(){return app;}

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    public Realm provideReam(){
        return Realm.getDefaultInstance();
    }

}
