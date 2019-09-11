package lf.com.oniondemo.UI;

import android.content.Context;

/**
 * Created by tranvonb on 3/14/2017.
 */
public interface IView {
    Context getCurrentContext();
    void showLoading();
    void hideLoading();
    void onError(Throwable throwable);
    void checkAppPermission(int requestPermissionId, String permissionName, RequestPermissionCallback requestPermission);

    interface RequestPermissionCallback {
        void onPermissionGrant(Context context);
        void onPermissionDenied(Context context);
    }
}
