package lf.com.oniondemo.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.R;
import lf.com.oniondemo.UI.Activities.ProductDetailView;

/**
 * Created by TranVo on 3/15/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private List<Review> reviewList = new ArrayList<Review>();
    private ProductDetailView mListenter;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(reviewList != null){
            Review review = reviewList.get(position);
            holder.updateItemContent(review);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public void setItemClickListener(ProductDetailView listener){
        this.mListenter = listener;
    }

    public void setItemList(List<Review> list) {
        if (reviewList != null){
            reviewList.clear();
        }
        this.reviewList = list;
    }

    public List<Review> getItemList(){
        return reviewList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtUserName)
        TextView txtUserName;
        @BindView(R.id.txtComment)
        TextView txtComment;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateItemContent(Review review) {
            txtUserName.setText(review.getUserEmail());
            txtComment.setText(review.getComment());
            ratingBar.setRating((float) review.getRating());
        }
    }
}
