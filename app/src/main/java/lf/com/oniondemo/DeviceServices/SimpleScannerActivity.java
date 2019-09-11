package lf.com.oniondemo.DeviceServices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import lf.com.oniondemo.Utils.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by TranVo on 3/19/2017.
 */

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private static final String TAG = SimpleScannerActivity.class.getSimpleName();
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
        mScannerView.setResultHandler(null);
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Bundle bundle = new Bundle();
        bundle.putString(Constants.QRSCANNER_RETURN_RESULT, rawResult.getText());
        Intent intent = getIntent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
    }
}
