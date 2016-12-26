/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 增加支持RecyclerView
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        init();
    }
    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
        init();
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
        init();
    }

    private void init() {
        mRefreshableView.setOnScrollListener(new OnRecyclerViewScrollListener(){
            @Override
            public void onLastItemVisible() {
                super.onLastItemVisible();
                if(mOnLastItemVisibleListener!=null)mOnLastItemVisibleListener.onLastItemVisible();
            }
        });
    }


    OnLastItemVisibleListener  mOnLastItemVisibleListener;
    public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        mOnLastItemVisibleListener = listener;
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    /**
     * 是否准备好了下拉刷新
     * @return
     */
    @Override
    protected boolean isReadyForPullStart() {
        return isTop();
    }

    /**
     * 是否准备好了上啦加载
     * @return
     */
    @Override
    protected boolean isReadyForPullEnd() {
        return isBottom();
    }

    /**
     * 判断是否达到了顶部
     * @return
     */
    private boolean isTop() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();
        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;
        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    /**
     * 获取第一个可见子View的位置下标
     * @return
     */
    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * 是否到达了底部
     * @return
     */
    private boolean isBottom() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if(lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount()-1) {
                return mRefreshableView.getChildAt(
                        mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    /**
     * 获取最后一个可见子View的位置下标
     * @return
     */
    private int getLastVisiblePosition() {
        View lastVisibleChild = mRefreshableView.getChildAt(mRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }

}