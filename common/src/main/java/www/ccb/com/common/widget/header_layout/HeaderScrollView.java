package www.ccb.com.common.widget.header_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class HeaderScrollView extends ScrollView implements HeaderScrollHelper.ScrollableContainer{
    public HeaderScrollView(Context context) {
        super(context);
    }

    public HeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getScrollableView() {
        return this;
    }
}
