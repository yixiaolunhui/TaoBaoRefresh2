package com.handmark.pulltorefresh.library.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by zhouweilong on 2016/11/7.
 */

public class MyHeaderLayout extends LoadingLayoutBase {

    private  Context mContext;
    private  RotateAnimation refreshingAnimation;
    private  TextView ring_refresh_status;
    private  RingProgressBar progress_bar;
    private  LinearLayout header_base;
    private LinearLayout header_layout;

    public MyHeaderLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_START);
    }

    public MyHeaderLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        init(context,mode);
    }

    private void init(Context mContext,PullToRefreshBase.Mode mode) {
        this.mContext=mContext;
        LayoutInflater.from(mContext).inflate(R.layout.my_refresh_header, this);
        header_base=(LinearLayout)findViewById(R.id.header_base);
        header_layout=(LinearLayout)findViewById(R.id.header_layout);
        progress_bar=(RingProgressBar)findViewById(R.id.progress_bar);
        ring_refresh_status=(TextView)findViewById(R.id.ring_refresh_status);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.rotating);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        progress_bar.setProgress(90);

        LayoutParams lp = (LayoutParams) header_base.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;
        reset();
    }

    @Override
    public int getContentSize() {
        return header_layout.getHeight();
    }

    /**
     * 下拉可以刷新
     */
    @Override
    public void pullToRefresh() {
        ring_refresh_status.setText("下拉刷新");
        progress_bar.setIsShowIcon(true);
    }

    /**
     * 松开后刷新
     */
    @Override
    public void releaseToRefresh() {
        ring_refresh_status.setText("松开刷新");
        progress_bar.setIsShowIcon(false);
    }

    /**
     * 下拉中
     * @param scaleOfLayout scaleOfLayout
     */
    @Override
    public void onPull(float scaleOfLayout) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;
        int progress=(int) ((scaleOfLayout)*100);
        progress_bar.setProgress(progress>90?90:progress);

    }

    /**
     * 正在刷新
     */
    @Override
    public void refreshing() {
        ring_refresh_status.setText("正在刷新");
        progress_bar.startAnimation(refreshingAnimation);
    }

    @Override
    public void reset() {
        progress_bar.clearAnimation();
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }
}
