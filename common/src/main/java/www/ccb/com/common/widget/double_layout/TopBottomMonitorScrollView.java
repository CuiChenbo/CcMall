package www.ccb.com.common.widget.double_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ChenboCui
 */
public class TopBottomMonitorScrollView extends ScrollView {
    public TopBottomMonitorScrollView(Context context) {
        super(context);
    }

    public TopBottomMonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBottomMonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE){
            if (mScrollListener != null){

                int contentHeight=getChildAt(0).getHeight();
                int scrollHeight=getHeight();
                int scrollY=getScrollY();

                mScrollListener.onScroll(scrollY);
                if(scrollY==0){
                    mScrollListener.onScrollToTop();
                }

                if(scrollY+scrollHeight>=contentHeight||contentHeight<=scrollHeight){
                    mScrollListener.onScrollToBottom();
                }else {
                    mScrollListener.notBottom();
                }
            }
        }

        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);
        return result;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollYY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollYY, clampedX, clampedY);
        if (mScrollListener != null){

            int contentHeight=getChildAt(0).getHeight();
            int scrollHeight=getHeight();
            int scrollY=getScrollY();

            mScrollListener.onScroll(scrollY);
            if(scrollY==0){
                mScrollListener.onScrollToTop();
            }

            if(scrollY+scrollHeight>=contentHeight||contentHeight<=scrollHeight){
                mScrollListener.onScrollToBottom();
            }else {
                mScrollListener.notBottom();
            }
        }
    }

    public interface ScrollListener{
        void onScroll(int scrollY);
        void onScrollToBottom();
        void onScrollToTop();
        void notBottom();
    }
    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }
    private ScrollListener mScrollListener;
}