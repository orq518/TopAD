package com.topad.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.topad.bean.AdServiceBean;
import com.topad.util.LogUtil;

/**
 * ${todo}<下拉刷新的ListView>
 * 不能在一个拥有Scrollbar的组件中嵌入另一个拥有Scrollbar的组件，因为这不科学，会混淆滑动事件，导致只显示一到两行数据。那么就换一种思路，
 * 首先让子控件的内容全部显示出来，禁用了它的滚动。如果超过了父控件的范围则显示父控件的scrollbar滚动显示内容，思路是这样，一下是代码。
 * 具体的方法是自定义GridView组件，继承自GridView。重载onMeasure方法：
 * @author lht
 * @data: on 15/7/22 18:17
 */
public class PTRListView extends ListView {
	private static final String TAG = "PTRListView";

	public PTRListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PTRListView(Context context) {
		super(context);
	}

	public PTRListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/***
	 * 
	 * 改变高度 其中onMeasure函数决定了组件显示的高度与宽度；
	 * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
	 * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
	 * 之后在ScrollView中添加这个组件就OK了，同样的道理，ListView也适用。
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}