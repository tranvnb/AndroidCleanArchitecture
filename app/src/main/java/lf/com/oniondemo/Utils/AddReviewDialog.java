package lf.com.oniondemo.Utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.oniondemo.Domains.Models.Review;
import lf.com.oniondemo.R;

/**
 * Created by TranVo on 3/15/2017.
 */

public class AddReviewDialog extends DialogFragment {

    private static final String TAG = "add_review_dialog";
    private static AlertDialog alertDialog;
    private static FragmentManager fragmentManager;
    private static String mUsername;

    @BindView(R.id.addReview_RatingBar)
    RatingBar ratingBar;
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.btnSubmitReview)
    Button btnSubmitReview;
    @BindView(R.id.btnCancelReview)
    Button btnCancelReview;
    @BindView(R.id.txtErrMessage)
    TextView txtErrMessage;
    private static AddReviewDialogCallback mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.popup_add_review, (ViewGroup)getActivity().getWindow().getDecorView().getRootView(), false );

        ButterKnife.bind(this, v);

        builder.setView(v);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        if(getArguments() != null) {
            mUsername = getArguments().getString(Constants.REVIEW_DIALOG_USERNAME, getString(R.string.anonymous));
        }

        txtErrMessage.setVisibility(View.INVISIBLE);

        btnSubmitReview.setOnClickListener(view -> {
            if (edtComment != null && edtComment.getText().toString().isEmpty()){
                txtErrMessage.setText(R.string.enter_your_comment);
                txtErrMessage.setVisibility(View.VISIBLE);
            } else if(ratingBar != null && ratingBar.getRating() == 0){
                txtErrMessage.setText(R.string.should_chose_rating);
                txtErrMessage.setVisibility(View.VISIBLE);
            }
            else {
                Review review = new Review();
                review.setUserEmail(mUsername);
                review.setRating((int) ratingBar.getRating());
                review.setComment(edtComment.getText().toString());
                mCallback.onSubmitReviewClick(review);
                this.dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
            }
        });

        btnCancelReview.setOnClickListener(view -> {
            alertDialog.dismiss();
        });

        return alertDialog;
    }

    public static AddReviewDialog showAddReviewDialog(String username, FragmentManager activityFragmentManager, AddReviewDialogCallback callBack){
        mUsername = username;
        fragmentManager = activityFragmentManager;
        mCallback = callBack;
        AddReviewDialog dialog = new AddReviewDialog();
        Bundle args = new Bundle();
        args.putString(Constants.REVIEW_DIALOG_USERNAME, username);
        dialog.setArguments(args);
        dialog.show();
        return dialog;
    }

    public void show() {
        if(fragmentManager != null) {
            fragmentManager.beginTransaction().add(this, TAG).commitAllowingStateLoss();
        }
    }

    public static void hide(){
        if(alertDialog != null){
            alertDialog.dismiss();
        }
    }

    public interface AddReviewDialogCallback{
        void onSubmitReviewClick(Review review);
    }

}
