package com.topad.util;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class CustomLinkMovementMethod extends LinkMovementMethod {
	@Override
	public boolean onTouchEvent(TextView widget, Spannable buffer,
			MotionEvent event) {
		// TODO Auto-generated method stub
		 int action = event.getAction();

	        if (action == MotionEvent.ACTION_UP ||
	            action == MotionEvent.ACTION_DOWN) {
	            int x = (int) event.getX();
	            int y = (int) event.getY();

	            x -= widget.getTotalPaddingLeft();
	            y -= widget.getTotalPaddingTop();

	            x += widget.getScrollX();
	            y += widget.getScrollY();

	            Layout layout = widget.getLayout();
	            int line = layout.getLineForVertical(y);
	            int off = layout.getOffsetForHorizontal(line, x);

	            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

	            if (link.length != 0) {
	                if (action == MotionEvent.ACTION_UP) {
	                    link[0].onClick(widget);
	                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
	                			buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
	                			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	                } else if (action == MotionEvent.ACTION_DOWN) {
	                	buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
	                			buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
	                			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    Selection.setSelection(buffer,
								buffer.getSpanStart(link[0]),
								buffer.getSpanEnd(link[0]));
	                }

	                return true;
	            } else {
	                Selection.removeSelection(buffer);
	            }
	        }

	        return Touch.onTouchEvent(widget, buffer, event);
	}
}
