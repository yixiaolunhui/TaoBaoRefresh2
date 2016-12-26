package com.dalong.taobaorefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dalong on 2016/11/4.
 */

public class LinearLayoutTarget extends ViewGroupTarget<Bitmap> {
    private Context context;

    public LinearLayoutTarget(Context context, ViewGroup linearLayout) {

        super(linearLayout);

        this.context = context;
    }

    /**
     * Sets the {@link Bitmap} on the view using
     * {@link ImageView#setImageBitmap(Bitmap)}.
     *
     * @param resource The bitmap to display.
     */
    @Override
    protected void setResource(Bitmap resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(new BitmapDrawable(context.getResources(), resource));
        }else{
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), resource));
        }
    }
}
