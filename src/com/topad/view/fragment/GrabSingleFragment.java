package com.topad.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topad.R;
import com.topad.bean.AdServiceBean;
import com.topad.bean.GrabSingleBean;
import com.topad.util.LogUtil;
import com.topad.util.Utils;
import com.topad.view.activity.ADSDetailsActivity;
import com.topad.view.activity.GrabSingleDetailsActivity;
import com.topad.view.customviews.PTRListView;
import com.topad.view.customviews.PullToRefreshView;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * ${todo}<我要抢单>
 *
 * @author lht
 * @data: on 15/10/28 18:32
 */
public class GrabSingleFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener {
	private static final String LTAG = GrabSingleFragment.class.getSimpleName();
	/** 上下文 **/
	private Context mContext;
	/** 根view布局 **/
	private View mRootView;
	/** 下载更多 **/
	private PullToRefreshView mPullToRefreshView;
	/** listView **/
	private PTRListView mListView;
	/** 适配器 **/
	private ListAdapter adapter;
	/** 数据源 **/
	private ArrayList<GrabSingleBean> bankList = new ArrayList<GrabSingleBean>();

	@Override
	public String getFragmentName() {
		return LTAG;
	}

	/**
	 * ***生命周期******
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == mRootView) {
			mRootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fargment_grab_single, null);
		}
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initView() {
		mPullToRefreshView = (PullToRefreshView) mRootView.findViewById(R.id.main_pull_refresh_view);
		mListView = (PTRListView) mRootView.findViewById(R.id.listview);

		adapter = new ListAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(mContext, GrabSingleDetailsActivity.class);
				intent.putExtra("title",bankList.get(position).name);
				startActivity(intent);
			}
		});

		initData();
	}

	@Override
	public void setVisible(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		LogUtil.d("是否显示：" + isVisibleToUser + "    isNeedRefresh:" + isNeedRefresh);
		if (isVisibleToUser && isNeedRefresh) {
			isNeedRefresh = false;
		}
	}

	/**
	 * 请求数据
	 */
	public void initData() {
//        String merchantId = getIntent().getStringExtra("merchantId");
//        StringBuffer sb = new StringBuffer();
//        sb.append(Constants.getCurrUrl()).append(Constants.URL_BANK_LIST)
//                .append("?");
//        sb.append("merchantId=").append(merchantId);
//
//        showProgressDialog();
//        postWithoutLoading(sb.toString(), true, new HttpCallback() {
//            @Override
//            public <T> void onModel(T t) {
//                closeProgressDialog();
//
//                BankListModel blModel = (BankListModel) t;
//                if (blModel != null) {
//                    bankList.clear();
//                    bankList.addAll(blModel.bankList);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(BaseModel base) {
//
//            }
//        }, BankListModel.class);

		setData();
	}

	/**
	 * 下拉监听
	 * @param view
	 */
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				Utils.showToast(mContext, "加载更多数据!");
			}

		}, 2000);
	}


	private class ListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ListAdapter() {
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bankList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return bankList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate((R.layout.fargment_grab_single_item), null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView.findViewById(R.id.im_icon);
				holder.name = (TextView) convertView .findViewById(R.id.tv_name);
				holder.price = (TextView) convertView .findViewById(R.id.tv_price);
				holder.content = (TextView) convertView .findViewById(R.id.tv_content);
				holder.time = (Button) convertView .findViewById(R.id.btn_time);
				holder.countdown = (Button) convertView .findViewById(R.id.btn_countdown);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(bankList.get(position).name);
			holder.price.setText(bankList.get(position).price);
			holder.content.setText(bankList.get(position).content);
			holder.time.setText(bankList.get(position).time);
			holder.countdown.setText(bankList.get(position).countdown);
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
			TextView price;
			TextView content;
			TextView time;
			TextView countdown;
		}
	}

	/**
	 * 设置数据--测试
	 */
	private void setData() {
		GrabSingleBean bModel0 = new GrabSingleBean();
		bModel0.name = "北京市聚宝网深圳分公司";
		bModel0.price = "￥120000";
		bModel0.content = "高价发标编辑捕鱼游戏一套完整成熟的概率数值控制代码～";
		bModel0.time = "2015-10-22";
		bModel0.countdown = "还有3天到期";
		bankList.add(bModel0);

		GrabSingleBean bModel1 = new GrabSingleBean();
		bModel1.name = "北京市聚宝网深圳分公司";
		bModel1.price = "￥120000";
		bModel1.content = "高价发标编辑捕鱼游戏一套完整成熟的概率数值控制代码～";
		bModel1.time = "2015-10-22";
		bModel1.countdown = "还有3天到期";
		bankList.add(bModel1);

		GrabSingleBean bModel2 = new GrabSingleBean();
		bModel2.name = "北京市聚宝网深圳分公司";
		bModel2.price = "￥120000";
		bModel2.content = "高价发标编辑捕鱼游戏一套完整成熟的概率数值控制代码～";
		bModel2.time = "2015-10-22";
		bModel2.countdown = "还有3天到期";
		bankList.add(bModel2);

		GrabSingleBean bModel3 = new GrabSingleBean();
		bModel3.name = "北京市聚宝网深圳分公司";
		bModel3.price = "￥120000";
		bModel3.content = "高价发标编辑捕鱼游戏一套完整成熟的概率数值控制代码～";
		bModel3.time = "2015-10-22";
		bModel3.countdown = "还有3天到期";
		bankList.add(bModel3);

		GrabSingleBean bModel4 = new GrabSingleBean();
		bModel4.name = "北京市聚宝网深圳分公司";
		bModel4.price = "￥120000";
		bModel4.content = "高价发标编辑捕鱼游戏一套完整成熟的概率数值控制代码～";
		bModel4.time = "2015-10-22";
		bModel4.countdown = "还有3天到期";
		bankList.add(bModel4);
	}
}