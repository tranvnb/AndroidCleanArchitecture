package lf.com.oniondemo.Domains.UseCases.Review;

import android.support.annotation.NonNull;

import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetReview extends UseCase<GetReview.RequestValue, GetReview.ResponseValue> {
    private final MyDataSource<Review> reviewRepository;

    public GetReview( MyDataSource<Review> reviewRepository) {
        super(Schedulers.io());
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            reviewRepository.getByID(requestValue.getReviewID())
                    .subscribe(review -> {e.onNext(new ResponseValue(review));},
                            throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final String reviewID;

        public RequestValue(String reviewID) {
            this.reviewID = reviewID;
        }

        public String getReviewID() {
            return reviewID;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final Review review;

        public ResponseValue(Review review) {
            this.review = review;
        }

        public Review getReview() {
            return review;
        }
    }
}
