package com.topad.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * ${todo}<自定义ExpandableListView>
 *
 * @author lht
 * @data: on 15/10/30 10:20
 */
public class CustomExpandableListView extends ExpandableListView {

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
