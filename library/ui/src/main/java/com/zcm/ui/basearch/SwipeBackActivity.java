package com.zcm.ui.basearch;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zcm.ui.R;
import com.zcm.ui.customview.SwipeBackLayout;

/**
 * Created by zcm on 2018/1/6.
 */

public abstract class SwipeBackActivity extends BaseActivity implements SwipeBackLayout.SwipeBackListener{
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        swipeBackLayout.addView(view);
    }
    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.swipe_bg));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }

    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        getSwipeBackLayout().setDragEdge(dragEdge);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }
    public void setEnableSwipe(boolean enable) {
        getSwipeBackLayout().setEnablePullToBack(enable);
    }
    public void setSwipeDragEdge(SwipeBackLayout.DragEdge dragEdge){
        getSwipeBackLayout().setDragEdge(dragEdge);
    }
    public void setSwipeDragDirectMode(SwipeBackLayout.DragDirectMode dragDirectMode){
        getSwipeBackLayout().setDragDirectMode(dragDirectMode);
    }
    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }
}
