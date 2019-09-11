package lf.com.oniondemo.Presenters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lf.com.oniondemo.DataServices.LocalData.ProductRepositoryImpl;
import lf.com.oniondemo.DataServices.LocalData.ReviewRepositoryImpl;
import lf.com.oniondemo.Domains.Models.BaseModel;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.Product.GetProduct;
import lf.com.oniondemo.Domains.UseCases.Review.GetReviews;
import lf.com.oniondemo.Domains.UseCases.Review.InsertReview;
import lf.com.oniondemo.UI.Activities.ProductDetailView;

/**
 * Created by tranvonb on 3/14/2017.
 */
public class ProductDetailPresenterImpl extends BasePresenter implements ProductDetailPresenter {

    private ProductDetailView view;
    private ProductRepositoryImpl productRepository;
    private ReviewRepositoryImpl reviewRepository;

    public ProductDetailPresenterImpl(ProductDetailView view, ProductRepositoryImpl productRepository, ReviewRepositoryImpl reviewRepository) {
        this.view = view;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void fetchProductDetail(String productId) {
        view.showLoading();
        GetProduct.RequestValue requestValue = new GetProduct.RequestValue(productId);
        GetProduct mGetProduct = new GetProduct(productRepository);
        mGetProduct.run(requestValue)
                .subscribe(responseValue -> {
                    view.hideLoading();
                    view.updateProductDetail(responseValue.getProduct());
                },
                        throwable -> view.onError(throwable));
    }

    @Override
    public void fetchUserReview(String productId) {
        view.showLoading();
        GetReviews.RequestValue requestValue = new GetReviews.RequestValue(FilterType.QUERYABLE, new FilterCallback() {
            @Override
            public <M extends BaseModel, R extends Collection> R filter(Collection<M> list) {
                ConditionPair conditionPair = new ConditionPair();
                conditionPair.setKey("productId");
                conditionPair.setValue(productId);
                conditionPair.setOperator("==");
                return (R) new ArrayList(Arrays.asList(conditionPair));
            }
        });

        GetReviews mGetReview = new GetReviews(reviewRepository);
        mGetReview.run(requestValue)
                .subscribe(responseValue -> {
                    view.hideLoading();
                    view.updateReviewList(responseValue.getReviewList());
                },
                        throwable -> view.onError(throwable));
    }

    @Override
    public void addNewReview(String productId, Review review) {
        view.showLoading();
        review.setProductId(productId);
        InsertReview.RequestValue requestValue = new InsertReview.RequestValue(review);

        InsertReview mInsertReview = new InsertReview(reviewRepository);
        mInsertReview.run(requestValue)
                .subscribe(responseValue -> {
                    view.hideLoading();
                    if("".compareTo(responseValue.getResult()) != 0) {
                        view.updateReviewListWithNewReview(review);
                    }
                },
                        throwable -> view.onError(throwable));
    }
}
