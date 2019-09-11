package lf.com.oniondemo.Domains.UseCases.Review;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterCallback;
import lf.com.oniondemo.Domains.UseCases.Filter.FilterType;
import lf.com.oniondemo.Domains.UseCases.UseCase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class GetReviews extends UseCase<GetReviews.RequestValue, GetReviews.ResponseValue> {
    private final MyDataSource<Review> reviewRepository;

    public GetReviews(MyDataSource<Review> reviewRepository) {
        super(Schedulers.io());
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected Observable<ResponseValue> executeUseCase(RequestValue requestValue) {
        return Observable.create(e -> {
            if (requestValue.getFilterType() == FilterType.LISTITEM) {
                reviewRepository.getAll()
                        .subscribe(reviews -> {
                                    if (requestValue.getCallback() != null) {
                                        reviews = requestValue.getCallback().filter(reviews);
                                    }
                                    e.onNext(new ResponseValue(reviews));
                                },
                                throwable -> e.onError(throwable));
            } else if (requestValue.getFilterType() == FilterType.QUERYABLE) {
                //If using sql query then append filter criteria to query string then execute
                List<ConditionPair> filterOptions = requestValue.getCallback().<Review, List<ConditionPair>>filter(Collections.emptyList());
                reviewRepository.getAll(filterOptions)
                        .subscribe(reviews -> {
                                    e.onNext(new ResponseValue(reviews));
                                },
                                throwable -> e.onError(throwable));
            }
        });
    }

    public static final class RequestValue implements UseCase.RequestValue {
        private final FilterType filterType;
        private final FilterCallback callback;

        public RequestValue(FilterType filterType, FilterCallback callback) {
            this.filterType = filterType;
            this.callback = callback;
        }

        public FilterType getFilterType() {
            return filterType;
        }

        public FilterCallback getCallback() {
            return callback;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Review> reviewList;

        public ResponseValue(List<Review> reviewList) {
            this.reviewList = reviewList;
        }

        public List<Review> getReviewList() {
            return reviewList;
        }
    }
}
