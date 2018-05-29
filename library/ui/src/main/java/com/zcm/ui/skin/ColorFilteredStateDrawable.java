package com.zcm.ui.skin;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;

import java.util.ArrayList;

/**
 *兼容Android5.0以下StateListDrawable的ColorFilter无效问题
 *
 * @author zhaoyuliang
 */
public class ColorFilteredStateDrawable extends StateListDrawable {

    private ArrayList<int[]> mStates = new ArrayList<int[]>();
    private ArrayList<Integer> mColors = new ArrayList<Integer>();

    public void addState(int[] stateSet, Drawable drawable,int color) {
        mStates.add(stateSet);
        mColors.add(color);
        super.addState(stateSet, drawable.mutate());
    }

    @Override
    protected boolean onStateChange(int[] states) {
        if (this.mStates != null && !this.mStates.isEmpty()) {
            for (int i = 0; i < this.mStates.size(); i++) {
                if (StateSet.stateSetMatches(this.mStates.get(i), states)) {
                    super.setColorFilter(new PorterDuffColorFilter(this.mColors.get(i), PorterDuff.Mode.SRC_IN));
                    return super.onStateChange(states);
                }
            }
            super.clearColorFilter();
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
