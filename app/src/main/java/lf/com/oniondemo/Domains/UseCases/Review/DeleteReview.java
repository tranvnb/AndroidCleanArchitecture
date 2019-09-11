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

public class DeleteReview extends UseCase<DeleteReview.RequestValue, DeleteReview.ResponseValue> {
    private MyDataSource<Review> reviewRepository;

    public DeleteReview( MyDataSource<Review> reviewRepository) {
        super(Schedulers.io());
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return null;
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
        private final Boolean result;

        public ResponseValue(Boolean result) {
            this.result = result;
        }

        public Boolean getResult() {
            return result;
        }
    }
}
