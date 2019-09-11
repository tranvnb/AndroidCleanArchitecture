package lf.com.oniondemo.UI.CustomWidgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by tranvonb on 3/21/2017.
 */

public class CustomAutoCompleteTextView  extends AutoCompleteTextView {

    private int myThreshold;

    public CustomAutoCompleteTextView  (Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomAutoCompleteTextView  (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

//    @Override
//    protected void onFocusChanged(boolean focused, int direction,
//                                  Rect previouslyFocusedRect) {
//        super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        if (focused && getAdapter() != null && getAdapter().getCount() > 0) {
//            setText("");
//            showDropDown();
//        }
//    }

    @Override
    public void dismissDropDown() {
        super.dismissDropDown();
        clearFocus();
    }
}
