package lf.com.oniondemo.UI;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import lf.com.oniondemo.Injectors.Components.AppComponent;
import lf.com.oniondemo.Presenters.IPresenter;
import lf.com.oniondemo.R;
import lf.com.oniondemo.Utils.AddReviewDialog;
import lf.com.oniondemo.OnionDemoApp;

/**
 * Created by tranvonb on 3/13/2017.
 */

public abstract class BaseView extends AppCompatActivity implements IView {

    protected RequestPermissionCallback checkPermissionCallback;
    @BindView(R.id.progressIndicator)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCompoment((AppComponent) OnionDemoApp.getInstance(this).getAppComponent());
    }

    protected abstract void setupCompoment(AppComponent appComponent);
    protected abstract IPresenter getPresenter();
    protected abstract void onMyPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, RequestPermissionCallback checkPermissionCallback);



    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    public void showLoading() {
        if(progressBar != null && progressBar.getVisibility() == View.INVISIBLE){
//            SystemClock.sleep(2000);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
//            SystemClock.sleep(2000);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AddReviewDialog.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
    }

    @Override
    public void checkAppPermission(int requestPermissionId, String permissionName, RequestPermissionCallback callback) {
        this.checkPermissionCallback = callback;

        if(ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)){
                callback.onPermissionDenied(this);
            }else {
                ActivityCompat.requestPermissions(this, new String[]{permissionName}, requestPermissionId);
            }
        }else {
            callback.onPermissionGrant(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.onMyPermissionResult(requestCode, permissions, grantResults, checkPermissionCallback);
    }

}
