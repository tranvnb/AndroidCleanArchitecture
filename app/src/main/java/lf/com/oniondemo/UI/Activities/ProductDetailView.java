package lf.com.oniondemo.UI.Activities;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.UI.IView;

/**
 * Created by TranVo on 3/15/2017.
 */
public interface ProductDetailView extends IView {
    void updateProductDetail(Product product);

    void updateReviewList(List<Review> reviewList);

    void updateReviewListWithNewReview(Review review);
}
