package lf.com.oniondemo.UI.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Injectors.Components.AppComponent;
import lf.com.oniondemo.Injectors.Components.DaggerProductDetailComponent;
import lf.com.oniondemo.Injectors.Modules.ProductDetailModule;
import lf.com.oniondemo.Presenters.IPresenter;
import lf.com.oniondemo.Presenters.ProductDetailPresenter;
import lf.com.oniondemo.R;
import lf.com.oniondemo.UI.Adapters.ReviewListAdapter;
import lf.com.oniondemo.UI.BaseView;
import lf.com.oniondemo.Utils.AddReviewDialog;
import lf.com.oniondemo.Utils.Constants;
import lf.com.oniondemo.Utils.RealmHelper;

public class ProductDetailActivity extends BaseView implements ProductDetailView {

    private String productId = "";

    @BindView(R.id.pdicon)
    ImageView pdIcon;
    @BindView(R.id.pdRatingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtpdBrandName)
    TextView txtBrandName;
    @BindView(R.id.txtpdProductDes)
    TextView txtProductDes;
    @BindView(R.id.txtpdProductName)
    TextView txtProductName;
    @BindView(R.id.detailToolbar)
    Toolbar toolbar;
    @BindView(R.id.reviewList)
    RecyclerView recyclerViewList;


    @Inject
    ProductDetailPresenter presenter;
    @Inject
    ReviewListAdapter reviewListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(layoutManager);
        reviewListAdapter = new ReviewListAdapter();
        recyclerViewList.setAdapter(reviewListAdapter);

    }

    @Override
    protected void setupCompoment(AppComponent appComponent) {
        DaggerProductDetailComponent.builder()
                .appComponent(appComponent)
                .productDetailModule(new ProductDetailModule(this))
                .build()
                .autoInjectTo(this);
    }

    @Override
    protected IPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadProductDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.menu_add_review:
                AddReviewDialog.showAddReviewDialog(getString(R.string.current_user_text), getFragmentManager(), new AddReviewDialog.AddReviewDialogCallback() {
                    @Override
                    public void onSubmitReviewClick(Review review) {
                        presenter.addNewReview(productId, review);
                    }


                });
                break;
        }

        return result;
    }

    private void reloadProductDetail() {
        //Only refresh data 1 time
        if(productId != null && "".compareTo(productId) == 0 && getIntent().hasExtra(Constants.PRODUCTDETAIL_PRODUCT_ID)) {
            productId = getIntent().getStringExtra(Constants.PRODUCTDETAIL_PRODUCT_ID);
            presenter.fetchProductDetail(productId);
            presenter.fetchUserReview(productId);
        }
    }

    @Override
    public void updateProductDetail(Product product) {
        pdIcon.setImageResource(RealmHelper.getIconResource(product.getProductIconIndex()));
        txtBrandName.setText(product.getBrandName());
        txtProductName.setText(product.getName());
        txtProductDes.setText(product.getDescription());
        ratingBar.setRating((float)product.getAverageRating());
    }

    @Override
    public void updateReviewList(List<Review> reviewList) {
        reviewListAdapter.setItemList(reviewList);
        reviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateReviewListWithNewReview(Review review) {
        reviewListAdapter.getItemList().add(0, review);
        reviewListAdapter.notifyDataSetChanged();
        presenter.fetchProductDetail(productId);
    }

    @Override
    protected void onMyPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, RequestPermissionCallback checkPermissionCallback) {
        return;
    }

}

