package com.topad.view.customviews;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.topad.R;

/**
 * @description Popup菜单
 * @author 
 * */
public class PopupMenu implements OnItemClickListener{
	private PopupWindow mPopupWindow = null;
	private Context context = null;
	private View parentView = null;
	private ListView mListMenu = null;
	private ListAdapter mListMenuAdapter = null;
	private ArrayList<String> mMenuListData = null;
	private int mXPos = 0;
	private int mYPos = 0;
	private PopupMenuInterface mObserver = null;

	public PopupMenu(Context context, View parentView) {
		this.context = context;
		this.parentView = parentView;
	}
	
	public void setPosOffset(int xOffset, int yOffset){
		mXPos = xOffset;
		mYPos = yOffset;
	}
	
	public void setMenuData(ArrayList<String> list){
		mMenuListData = list;
	}
	
	private void ShowPopupWindow(){
		LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = null;
		try {
			view = mLayoutInflater.inflate(R.layout.popup_menu_layout, null);
			view.setOnKeyListener(new OnKeyListener()
			{
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event)
				{
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK)
						{
							if (mPopupWindow != null)
							{
								mPopupWindow.dismiss();
							}
							return true;
						}
					return false;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		mListMenu = (ListView)view.findViewById(R.id.popup_menu_listview);

		if (mMenuListData == null){
			mMenuListData = new ArrayList<String>();
		}
		
		mListMenuAdapter = new ListAdapter(context, mMenuListData);
		mListMenu.setAdapter(mListMenuAdapter);
		mListMenu.setOnItemClickListener(this);

		this.mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.mPopupWindow.setFocusable(true);
		this.mPopupWindow.setTouchable(true);
		this.mPopupWindow.setOutsideTouchable(true);
		
		this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources())); // 响应返回键必须的语句。
		this.mPopupWindow.setOnDismissListener(new PopDissmisListener());
//		this.mPopupWindow.showAtLocation(parentView, Gravity.LEFT | Gravity.TOP, mXPos, mYPos);
	}
	
	public void ShowWidgetAsDropDown(boolean show){
		ShowPopupWindow();
		if (mPopupWindow != null){
			if (show && !mPopupWindow.isShowing()){
				mPopupWindow.showAsDropDown(parentView, mXPos, mYPos);
			}
			else if (!show && mPopupWindow.isShowing()){
				mPopupWindow.dismiss();
			}
		}
	}
	
	public void ShowWidgetAtLocation(int gravity, int xpos, int ypos){
		ShowPopupWindow();
		if (mPopupWindow != null){
			mPopupWindow.showAtLocation(parentView, gravity, xpos, ypos);
		}
	}
	
	public boolean isShowing(){
		if (mPopupWindow != null){
			return mPopupWindow.isShowing();
		}
		
		return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(null != mPopupWindow && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
//			Toast.makeText(context, "item " + position + "is clicked!", Toast.LENGTH_SHORT).show();
			if (mObserver != null){
				mObserver.onPopupMenuItemClick(parent, view, position, id);
			}
		}
	}
	

	public void setObserver(PopupMenuInterface mObserver) {
		this.mObserver = mObserver;
	}

	private class PopDissmisListener implements PopupWindow.OnDismissListener{
		
		@Override
		public void onDismiss(){
		}
	}
	

	private class ListAdapter extends BaseAdapter {
		private ArrayList<String> listData = null;
		private LayoutInflater inflater = null;
	
		public ListAdapter(Context context, ArrayList<String> list) {
			if (list == null || context == null){
				throw new IllegalArgumentException("参数不能为null");
			}
			
			listData = list;
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return listData.size();
		}
	
		@Override
		public Object getItem(int position) {
			return position;
		}
	
		@Override
		public long getItemId(int position) {
			return position;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = inflater.inflate(R.layout.popup_menu_item_layout, null);
			}
			
			TextView tv = (TextView)convertView.findViewById(R.id.popup_menu_item_text);
			
			if (listData.size() == 0){
				return convertView;
			}
			
			String ele = listData.get(position % listData.size());
			tv.setText(ele);

			return convertView;
		}

	}
	
	public interface PopupMenuInterface{
		public void onPopupMenuItemClick(AdapterView<?> parent, View view, int position, long id);
	}
	
}
