package lf.com.oniondemo.UI.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.oniondemo.DeviceServices.SimpleScannerActivity;
import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Injectors.Components.AppComponent;
import lf.com.oniondemo.Injectors.Components.DaggerProductComponent;
import lf.com.oniondemo.Injectors.Modules.ProductModule;
import lf.com.oniondemo.Presenters.IPresenter;
import lf.com.oniondemo.Presenters.ProductPresenter;
import lf.com.oniondemo.R;
import lf.com.oniondemo.UI.Adapters.ProductListAdapter;
import lf.com.oniondemo.UI.BaseView;
import lf.com.oniondemo.Utils.Constants;

public class ProductActivity extends BaseView implements ProductView, View.OnClickListener, AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    @Inject
    ProductPresenter productPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleProductList)
    RecyclerView recycleProductList;

    @BindView(R.id.autoCompleteBrandName)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.btnProductSearch)
    FloatingActionButton btnProductSearch;

    @Inject
    ArrayAdapter adapter;

    @Inject
    ProductListAdapter productListAdapter;

    Hashtable<String, Brand> brands;
    private String currentSelectedBrandId;
    private EditText txtSearchProductId;
    private TextView txtSearchError;
    private AlertDialog dialog;
    private Button btnQrcodeScanner;
    private LinearLayout linearLayout;
    private LinearLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleProductList.setHasFixedSize(true);
        recycleProductList.setLayoutManager(layoutManager);
        recycleProductList.setAdapter(productListAdapter);

    }

    @Override
    protected void setupCompoment(AppComponent appComponent) {
        DaggerProductComponent.builder()
                .appComponent(appComponent)
                .productModule(new ProductModule(this))
                .build()
                .autoInjectTo(this);
    }

    @Override
    protected IPresenter getPresenter() {
        return this.productPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        productPresenter.fetchBrandList();
        if(currentSelectedBrandId != null && "".compareTo(currentSelectedBrandId) != 0){
            productPresenter.fetchProductList(currentSelectedBrandId);
        }else {
            productPresenter.fetchAllProduct();
        }
        autoCompleteTextView.setOnItemClickListener(this);
        autoCompleteTextView.setOnFocusChangeListener(this);
        btnProductSearch.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoCompleteTextView.setOnItemClickListener(null);
        btnProductSearch.setOnClickListener(null);
    }

    @Override
    public void updateBrandList(List<Brand> brands) {

        ArrayList<String> arrString = new ArrayList<>();
        this.brands = new Hashtable<>();
        for (Brand brand :
                brands) {
            arrString.add(brand.getName());
            this.brands.put(brand.getName(), brand);
        }
        autoCompleteTextView.setAdapter(adapter);
        adapter.clear();
        adapter.addAll(arrString);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateProductList(List<Product> productList) {
        productListAdapter.setItemList(productList);
        productListAdapter.setItemClickListener(this);
        productListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProductItemClick(Product item) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCTDETAIL_PRODUCT_ID, item.getId());
        intent.putExtra(Constants.PRODUCTDETAIL_BRAND_NAME, autoCompleteTextView.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        if(adapterView.getItemAtPosition(pos) != null && getString(R.string.all_brands).compareTo(adapterView.getItemAtPosition(pos).toString()) == 0)
        {
            currentSelectedBrandId = null;
            productPresenter.fetchAllProduct();
        }else{
            currentSelectedBrandId = brands.get(adapterView.getItemAtPosition(pos)).getId();
            productPresenter.fetchProductList(brands.get(adapterView.getItemAtPosition(pos)).getId());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProductSearch:
                showSearchProductDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.QRSCANNER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                if (data.hasExtra(Constants.QRSCANNER_RETURN_RESULT)){
                    if(txtSearchProductId != null) {
                        txtSearchProductId.setText(data.getStringExtra(Constants.QRSCANNER_RETURN_RESULT));
                    }
                }
            }
        }
    }

    private void showSearchProductDialog(){
        if(linearLayout == null) {
            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int paddingTB = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);
            linearLayout.setPadding(paddingTB, 0, paddingTB, 0);
        }
        if(content == null) {
            content = new LinearLayout(this);
            content.setOrientation(LinearLayout.HORIZONTAL);
            content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(content);
        }
        if(txtSearchProductId == null) {
            txtSearchProductId = new EditText(this);
            txtSearchProductId.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtSearchProductId.setHint(R.string.enter_product_id);
            content.addView(txtSearchProductId);
        }

        if(btnQrcodeScanner == null) {
            btnQrcodeScanner = new Button(this);
            btnQrcodeScanner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnQrcodeScanner.setText(R.string.qrcode_scanner);
            content.addView(btnQrcodeScanner);
            btnQrcodeScanner.setOnClickListener(view -> {
                checkAppPermission(Constants.ACCESS_CAMERA_REQUEST_PERMISSION_ID, Manifest.permission.CAMERA, new RequestPermissionCallback() {
                    @Override
                    public void onPermissionGrant(Context context) {
                        Intent intent = new Intent(context, SimpleScannerActivity.class);
                        startActivityForResult(intent, Constants.QRSCANNER_REQUEST_CODE);
                    }

                    @Override
                    public void onPermissionDenied(Context context) {
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.title_permission_required)
                                .setMessage(R.string.camera_permission_required_description)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }
                });

            });
        }



        if(txtSearchError == null) {
            txtSearchError = new TextView(this);
            txtSearchError.setVisibility(View.INVISIBLE);
            txtSearchError.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            txtSearchError.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(txtSearchError);
        }


        if(dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.search_product)
                    .setView(linearLayout)
                    .setMessage(R.string.search_product_explaint)
                    .setPositiveButton(android.R.string.yes, null)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_menu_search)
                    .create();
            dialog.setOnDismissListener(dialogInterface -> {
                if(txtSearchError != null){
                    txtSearchError.setVisibility(View.INVISIBLE);
                }
                if(txtSearchProductId != null){
                    txtSearchProductId.setText("");
                }
            });
            dialog.setOnShowListener(dialogInterface -> {
                Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setOnClickListener(view -> {
                    if (txtSearchProductId != null && "".compareTo(txtSearchProductId.getText().toString()) != 0) {
                        txtSearchError.setVisibility(View.INVISIBLE);
                        productPresenter.searchProduct(txtSearchProductId.getText().toString());
                    } else {
                        txtSearchError.setVisibility(View.VISIBLE);
                        txtSearchError.setText(R.string.enter_product_id);
                    }
                });
            });
        }

        dialog.show();
    }

    @Override
    protected void onMyPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, RequestPermissionCallback checkPermissionCallback) {
        switch (requestCode) {
            case Constants.ACCESS_CAMERA_REQUEST_PERMISSION_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(checkPermissionCallback != null){
                        checkPermissionCallback.onPermissionGrant(this);
                    }

                } else {

                    if(checkPermissionCallback != null){
                        checkPermissionCallback.onPermissionDenied(this);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void showProductNotExist(String productId) {
        txtSearchError.setVisibility(View.VISIBLE);
        txtSearchError.setText(getString(R.string.search_product_error) + productId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean focused) {
        if(view.getId() == R.id.autoCompleteBrandName && focused && adapter != null){
            autoCompleteTextView.showDropDown();
        }
    }
}
