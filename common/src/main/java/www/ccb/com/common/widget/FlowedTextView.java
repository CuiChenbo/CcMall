package www.ccb.com.common.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class FlowedTextView extends android.support.v7.widget.AppCompatTextView {
    public FlowedTextView(Context context) {
        super(context);
    }

    public FlowedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int getAvailableWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public boolean isOverFlowed() {
        Paint paint = getPaint();
        float width = paint.measureText(getText().toString());
        if (width > getAvailableWidth()) return true;
        return false;
    }
    public void setUnfold(boolean isUnfold){
        if (isUnfold){
            setMaxLines(199);
        }else {
            setMaxLines(15);

        }
    }
}
