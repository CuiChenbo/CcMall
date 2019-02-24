package www.ccb.com.common.widget.double_layout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * ChenboCui 上下层两个ScrollView
 */
public class DoubleScrollViewLayout extends ViewGroup {
    public static String TAG = DoubleScrollViewLayout.class.getName();

    TopBottomMonitorScrollView topScrollView, bottomScrollView;
    VelocityTracker velocityTracker = VelocityTracker.obtain();
    Scroller scroller = new Scroller(getContext());

    int currPosition = 0;
    int position1Y;
    int lastY,lastX;
    public int scaledTouchSlop;//最小滑动距离
    private final int speed = 200;  //触发自动滚动的滑动速度
    private final int TriggerDistance = 211;  //触发自动滚动的距离；
    boolean isIntercept;

    public boolean bottomScrollVIewIsInTop = false;
    public boolean topScrollViewIsBottom = false;

    public DoubleScrollViewLayout(Context context) {
        super(context);
        init();
    }

    public DoubleScrollViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleScrollViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        post(new Runnable() {
            @Override
            public void run() {
                //在第一个scrollview套一个刷新控件,然后在去拿DoubleScrollView；
                if (getChildAt(0) instanceof TopBottomMonitorScrollView) {
                    topScrollView = (TopBottomMonitorScrollView) getChildAt(0);
                }else if (getChildAt(0) instanceof SwipeRefreshLayout){
                    SwipeRefreshLayout swipeRefreshLayout  = (SwipeRefreshLayout)getChildAt(0);
                    for (int i = 0; i < swipeRefreshLayout.getChildCount(); i++) {
                        if (swipeRefreshLayout.getChildAt(i) instanceof TopBottomMonitorScrollView){
                            topScrollView = (TopBottomMonitorScrollView) swipeRefreshLayout.getChildAt(i);
                            break;
                        }
                    }
                }else{
                    ViewGroup vg  = (ViewGroup) getChildAt(0);
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        if (vg.getChildAt(i) instanceof TopBottomMonitorScrollView){
                            topScrollView = (TopBottomMonitorScrollView) vg.getChildAt(i);
                            break;
                        }
                    }
                }
                //第二个scrollview也顺便处理一下,然后在去拿DoubleScrollView；
                if (getChildAt(1) instanceof TopBottomMonitorScrollView) {
                    bottomScrollView = (TopBottomMonitorScrollView) getChildAt(1);
                }else if (getChildAt(1) instanceof SwipeRefreshLayout){
                    SwipeRefreshLayout swipeRefreshLayout  = (SwipeRefreshLayout)getChildAt(1);
                    for (int i = 0; i < swipeRefreshLayout.getChildCount(); i++) {
                        if (swipeRefreshLayout.getChildAt(i) instanceof TopBottomMonitorScrollView){
                            bottomScrollView = (TopBottomMonitorScrollView) swipeRefreshLayout.getChildAt(i);
                            break;
                        }
                    }
                }else{
                    ViewGroup vg  = (ViewGroup) getChildAt(1);
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        if (vg.getChildAt(i) instanceof TopBottomMonitorScrollView){
                            bottomScrollView = (TopBottomMonitorScrollView) vg.getChildAt(i);
                            break;
                        }
                    }
                }
                topScrollView.setScrollListener(new TopBottomMonitorScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        topScrollViewIsBottom = true;
                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (scrollY == 0) {
                            if (scrollTopListener != null) scrollTopListener.isTop(true);
                        } else {
                            if (scrollTopListener != null) scrollTopListener.isTop(false);
                        }
                    }

                    @Override
                    public void notBottom() {
                        topScrollViewIsBottom = false;
                    }

                });

                bottomScrollView.setScrollListener(new TopBottomMonitorScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {

                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (scrollY == 0) {
                            bottomScrollVIewIsInTop = true;
                            if (scrollTopListener != null) scrollTopListener.isTop(true);
                        } else {
                            bottomScrollVIewIsInTop = false;
                            if (scrollTopListener != null) scrollTopListener.isTop(false);
                        }
                    }

                    @Override
                    public void notBottom() {

                    }
                });

                position1Y = topScrollView.getBottom();

                scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //防止子View禁止父view拦截事件
        this.requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int x = (int) ev.getX();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:

                if (topScrollViewIsBottom) {
                    //判断是否已经滚动到了底部
                    int dy1 = lastY - y;
                    int dx1 = lastX - x;
                    //判断是否是向上滑动&&并且上下滑动的值大于左右滑动的值&&和是否在第一屏
                    if (dy1 > 0 && Math.abs(dy1) > Math.abs(dx1) && currPosition == 0) {
                        if (dy1 >= scaledTouchSlop) {
                            isIntercept = true;//拦截事件
                            lastY = y;
                        }
                    }
                }

                Log.i("Ccb", "bottomScrollVIewIsInTop: "+bottomScrollVIewIsInTop+"-----");
                if (bottomScrollVIewIsInTop) {
                    int dy = lastY - y;
                    int dx = lastX - x;
                    //判断是否是向下滑动&&并且上下滑动的值大于左右滑动的值&&是否在第二屏
                    if (dy < 0 && Math.abs(dy) > Math.abs(dx) && currPosition == 1) {
                        if (Math.abs(dy) >= scaledTouchSlop) {
                            isIntercept = true;
                            lastY = y; //拦截事件后，重新给lastY赋值Y的位置
                        }
                    }
                }
                break;
        }
        return isIntercept;
    }


    private boolean isRecordDown = false; //是否去记录按下事件，false是记录；
    private int downY; //按下的Y位置
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isRecordDown){
                    downY = (int) event.getY();
                    isRecordDown = true;
                }

                int dy = lastY - y;
                if (getScrollY() + dy < 0) {
                    dy = getScrollY() + dy + Math.abs(getScrollY() + dy);
                }
                int fatherHeight = bottomScrollView.getBottom() == getHeight() ? (bottomScrollView.getBottom()*2) : bottomScrollView.getBottom();
//                   if (bottomScrollView.getChildAt(0).getHeight() == getHeight()){
//                       fatherHeight = (bottomScrollView.getChildAt(0).getHeight())*2;
//                   }else{
//                       fatherHeight = bottomScrollView.getChildAt(0).getHeight();
//                   }
                if (getScrollY() + dy + getHeight() > fatherHeight) {
                    dy = dy - (getScrollY() + dy - (fatherHeight - getHeight()));
                }
                scrollBy(0, dy);
                Log.i("ccccc", "getChildAt: "+dy+"-------"+bottomScrollView.getChildAt(0).getHeight());
                Log.i("ccccc", "getBottom: "+dy+"-------"+bottomScrollView.getBottom());
                Log.i("ccccc", "getScrollY: "+dy+"-------"+getScaleY());
                Log.i("ccccc", "getHeight: "+dy+"-------"+getHeight());
                break;
            case MotionEvent.ACTION_UP:
                isRecordDown = false;
                int differY = downY - y;

                isIntercept = false;

                velocityTracker.computeCurrentVelocity(1000);
                float yVelocity = velocityTracker.getYVelocity();

                //滑动速度或滑动距离达到触发点，就进行翻页；
                if (currPosition == 0) {
                    if ((yVelocity < 0 && yVelocity < -speed) || differY > TriggerDistance) {
                        smoothScroll(position1Y);
                        currPosition = 1;
                        if (currPositionChangeListener != null) currPositionChangeListener.currPosition(currPosition);
                    } else {
                        smoothScroll(0);
                    }
                } else {
                    if ((yVelocity > 0 && yVelocity > speed) || differY < -TriggerDistance) {
                        smoothScroll(0);
                        currPosition = 0;
                        if (currPositionChangeListener != null) currPositionChangeListener.currPosition(currPosition);
                    } else {
                        smoothScroll(position1Y);
                    }
                }
                Log.i(TAG, "currPosition: "+currPosition);
                break;
        }
        lastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childTop = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }

    //通过Scroller实现弹性滑动
    private void smoothScroll(int tartY) {
        int dy = tartY - getScrollY();
        scroller.startScroll(getScrollX(), getScrollY(), 0, dy,500);
        invalidate();
    }


    //滚动到顶部
    public void scrollToTop(){
        smoothScroll(0);
        currPosition=0;
        topScrollView.smoothScrollTo(0,0);
    }

    //滚动到第二页
    public void scrollTo1Y(){
        smoothScroll(position1Y);
        currPosition = 1;
        bottomScrollView.smoothScrollTo(0,0);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public int getCurrPosition(){
        return currPosition;
    }

    public interface onCurrPositionChangeListener{
        /**
         *
         * @param positon 0是第一层，1是第二层
         */
        void currPosition(int positon);
    }

    private onCurrPositionChangeListener currPositionChangeListener;
    public void setOnCurrPositionChangeListener(onCurrPositionChangeListener currPositionChangeListener){
        this.currPositionChangeListener = currPositionChangeListener;
    }


    public interface onScrollTopListener{
        void isTop(boolean top);
    }
    private onScrollTopListener scrollTopListener;
    public void setOnScrollTopListener(onScrollTopListener scrollTopListener){
        this.scrollTopListener = scrollTopListener;
    }

}