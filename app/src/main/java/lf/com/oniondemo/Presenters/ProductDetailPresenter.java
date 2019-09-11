package lf.com.oniondemo.Presenters;

import lf.com.oniondemo.Domains.Models.Review;

/**
 * Created by tranvonb on 3/14/2017.
 */
public interface ProductDetailPresenter extends IPresenter {
    void fetchProductDetail(String productId);

    void fetchUserReview(String productId);

    void addNewReview(String productId, Review review);
}
