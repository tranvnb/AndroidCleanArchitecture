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

public class InsertReview extends UseCase<InsertReview.RequestValue, InsertReview.ResponseValue> {
    private final MyDataSource<Review> reviewRepository;

    public InsertReview( MyDataSource<Review> reviewRepository) {
        super(Schedulers.io());
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            reviewRepository.insert(requestValue.getReview())
                    .subscribe(aBoolean -> e.onNext(new ResponseValue(aBoolean)),
                            throwable -> e.onError(throwable));
        });
    }

    public static final class RequestValue implements UseCase.RequestValue{
        private final Review review;

        public RequestValue(Review review) {
            this.review = review;
        }

        public Review getReview() {
            return review;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final String result;

        public ResponseValue(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
