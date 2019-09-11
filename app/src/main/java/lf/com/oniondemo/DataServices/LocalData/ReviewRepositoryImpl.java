package lf.com.oniondemo.DataServices.LocalData;

import java.util.ArrayList;
import java.util.List;

import lf.com.oniondemo.DataServices.LocalData.RealmDto.ProductRealm;
import lf.com.oniondemo.DataServices.LocalData.RealmDto.ReviewRealm;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.R;
import lf.com.oniondemo.Utils.RealmHelper;
import lf.com.oniondemo.OnionDemoApp;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class ReviewRepositoryImpl implements MyDataSource<Review> {

    @Override
    public Observable<List<Review>> getAll() {
        return null;
    }

    @Override
    public Observable<Review> getByID(String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(Review newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(Review newItem) {
        Realm realm = Realm.getDefaultInstance();

        return Observable.create(e -> {
            final String[] key = {""};
            realm.executeTransactionAsync(realm1 -> {
                ProductRealm productRealm = realm1.where(ProductRealm.class).equalTo("Id", (newItem.getProductId())).findFirst();
                if (productRealm != null) {
                    key[0] = RealmHelper.generateUUID();
                    ReviewRealm reviewRealm = mapToRealm(newItem);
                    reviewRealm.setId(key[0]);
                    if (productRealm.getReviews() == null) {
                        productRealm.setReviews(new RealmList<>());
                    }
                    //have to update average rating before add new review
                    long rating = (long)(productRealm.getReviews().size() * productRealm.getAverageRating() + reviewRealm.getRating());
                    if (rating > 0){
                        rating = rating/(productRealm.getReviews().size() + 1);
                    }
                    productRealm.setAverageRating(rating);
                    productRealm.getReviews().add(reviewRealm);
                    e.onNext(reviewRealm.getId());
                } else {
                    e.onError(new Throwable(OnionDemoApp.getContext().getString(R.string.no_review_belong_to_product)));
                }
            });
        });
    }

    @Override
    public Observable<Boolean> delete(String ID) {
        return null;
    }

    @Override
    public Observable<List<Review>> getAll(List<ConditionPair> filterOptions) {
        Realm realm = Realm.getDefaultInstance();
        List<Review> reviews = new ArrayList<>();
        return Observable.create(e -> {
            realm.executeTransactionAsync(realm1 -> {
                RealmQuery query = RealmHelper.generateQuery(realm1.where(ReviewRealm.class), filterOptions);
                RealmResults realmResults = query.findAll();
                List<ReviewRealm> list = realm1.copyFromRealm(realmResults);
                for (ReviewRealm item :
                        list) {
                    reviews.add(mapToDomain(item));
                }
                e.onNext(reviews);
            });
        });

    }

    private ReviewRealm mapToRealm(Review newItem) {
        ReviewRealm reviewRealm = new ReviewRealm();
        if (newItem.getId() != null && "".compareTo(newItem.getId()) != 0) {
            reviewRealm.setId((newItem.getId()));
        }
        reviewRealm.setComment(newItem.getComment());
        reviewRealm.setRating(newItem.getRating());
        if (newItem.getUserId() != null && "".compareTo(newItem.getUserId()) != 0) {
            reviewRealm.setUserId((newItem.getUserId()));
        }
        if (newItem.getProductId() != null && "".compareTo(newItem.getProductId()) != 0) {
            reviewRealm.setProductId((newItem.getProductId()));
        }
        reviewRealm.setUserEmail(newItem.getUserEmail());
        return reviewRealm;
    }

    private Review mapToDomain(ReviewRealm reviewRealm) {
        Review review = new Review();
        review.setId(reviewRealm.getId());
        review.setUserEmail(reviewRealm.getUserEmail());
        review.setRating(reviewRealm.getRating());
        review.setComment(reviewRealm.getComment());
        return review;
    }
}
