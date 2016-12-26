package com.dalong.taobaorefresh;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by dalong on 2016/11/7.
 */

public class FooterLayout extends LoadingLayoutBase {

    private  LinearLayout footer_base;
    private TextView footerTv;

    public FooterLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_END);
    }

    public FooterLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        init(context,mode);
    }

    private void init(Context mContext,PullToRefreshBase.Mode mode) {
        LayoutInflater.from(mContext).inflate(R.layout.my_refresh_footer, this);
        footer_base=(LinearLayout)findViewById(R.id.footer_base);
        footerTv=(TextView)findViewById(R.id.footer);

        LayoutParams lp = (LayoutParams) footer_base.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;
    }

    @Override
    public int getContentSize() {
        return footer_base.getHeight();
    }

    /**
     * 下拉可以刷新
     */
    @Override
    public void pullToRefresh() {
        footerTv.setText("上拉加载");
    }

    /**
     * 松开后刷新
     */
    @Override
    public void releaseToRefresh() {
        footerTv.setText("松开加载");
    }

    /**
     * 下拉中
     * @param scaleOfLayout scaleOfLayout
     */
    @Override
    public void onPull(float scaleOfLayout) {

    }

    /**
     * 正在刷新
     */
    @Override
    public void refreshing() {
        footerTv.setText("正在加载");
    }

    @Override
    public void reset() {
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
