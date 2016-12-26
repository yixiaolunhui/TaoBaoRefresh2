package com.dalong.taobaorefresh;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by dalong on 2016/11/7.
 */

public class HeaderLayout extends LoadingLayoutBase {

    private  Context mContext;
    private  RotateAnimation refreshingAnimation;
    private  TextView ring_refresh_status;
    private  TaoBaoView mTaoBaoView;
    private  LinearLayout header_base;
    private LinearLayout header_layout;

    public HeaderLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_START);
    }

    public HeaderLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        init(context,mode);
    }

    private void init(Context mContext,PullToRefreshBase.Mode mode) {
        this.mContext=mContext;
        LayoutInflater.from(mContext).inflate(R.layout.taobao_view, this);
        header_base=(LinearLayout)findViewById(R.id.header_base);
        header_layout=(LinearLayout)findViewById(R.id.refresh_header_content);
        mTaoBaoView=(TaoBaoView)findViewById(R.id.taobao_view);
        ring_refresh_status=(TextView)findViewById(R.id.taobao_tv);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.rotating);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        mTaoBaoView.setProgress(90);
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
        mTaoBaoView.setIsShowIcon(true);
    }

    /**
     * 松开后刷新
     */
    @Override
    public void releaseToRefresh() {
        ring_refresh_status.setText("松开刷新");
        mTaoBaoView.setIsShowIcon(false);
    }

    /**
     * 下拉中
     * @param scaleOfLayout scaleOfLayout
     */
    @Override
    public void onPull(float scaleOfLayout) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;
        int progress=(int) ((scaleOfLayout)*100);
        mTaoBaoView.setProgress(progress>90?90:progress);

    }

    /**
     * 正在刷新
     */
    @Override
    public void refreshing() {
        mTaoBaoView.setIsShowIcon(false);
        ring_refresh_status.setText("正在刷新");
        mTaoBaoView.startAnimation(refreshingAnimation);
    }

    @Override
    public void reset() {
        mTaoBaoView.clearAnimation();
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
