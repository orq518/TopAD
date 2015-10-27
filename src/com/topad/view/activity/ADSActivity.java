package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.topad.R;
import com.topad.bean.AdServiceBean;
import com.topad.util.Utils;
import com.topad.view.customviews.PTRGridView;
import com.topad.view.customviews.PTRListView;
import com.topad.view.customviews.PullToRefreshView;
import com.topad.view.customviews.TitleView;
import com.topad.view.customviews.pullToRefresh.PullToRefreshBase;
import com.topad.view.customviews.pullToRefresh.PullToRefreshScrollView;

import java.util.ArrayList;

/**
 * ${todo}<广告服务－广告创意、营销策略、影视广告、动漫创作>
 *     category＝ 广告创意－1、营销策略－2、影视广告－3、动漫创作－4
 * @author lht
 * @data: on 15/10/26 11:06
 */
public class ADSActivity extends BaseActivity implements View.OnClickListener, PullToRefreshView.OnFooterRefreshListener {
    private static final String LTAG = ADSActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    private PullToRefreshView mPullToRefreshView;
    /** GridView对象 **/
    private PTRGridView mGridView;
    /** listView **/
    private PTRListView mListView;
    /** 适配器 **/
    private ListAdapter adapter;
    /** 数据源 **/
    private ArrayList<AdServiceBean> bankList = new ArrayList<AdServiceBean>();

    /** ScrollView **/
    private ScrollView mScrollView;
    /** view **/
    private LinearLayout view;
    /** 类别 **/
    private String category;

    @Override
    public int setLayoutById() {
        mContext = this;
        return 0;
    }

    @Override
    public View setLayoutByView() {
        view = (LinearLayout)View.inflate(this, R.layout.activity_ads_list, null);
        return view;
    }

    @Override
    public void initViews() {
        // 接收数据
        Intent intent = getIntent();
        if (intent != null) {
            category = intent.getStringExtra("category");
        }

        // 顶部标题布局
        mTitleView = (TitleView) view.findViewById(R.id.title);
        if(!Utils.isEmpty(category)){
            if(category.equals("1")){
                mTitleView.setTitle(getString(R.string.ads_advertising_creative_title));
            }else if(category.equals("2")){
                mTitleView.setTitle(getString(R.string.ads_marketing_strategy_title));
            }else if(category.equals("3")){
                mTitleView.setTitle(getString(R.string.ads_tvc_title));
            }else if(category.equals("4")){
                mTitleView.setTitle(getString(R.string.ads_anime_create_title));
            }
        }
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);
//        mPullToRefreshView.hideHeader();
        mListView = (PTRListView) view.findViewById(R.id.listview);

        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ADSActivity.this, ADSDetailsActivity.class);
                intent.putExtra("title",bankList.get(position).name);
                startActivity(intent);
            }
        });

        initData();
    }

    /**
     * 请求数据
     */

    @Override
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_layout:

                break;

            default:
                break;
        }
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

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }

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
                convertView = mInflater.inflate((R.layout.activity_ad_service_item_layout), null);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.ads_icon);
                holder.name = (TextView) convertView .findViewById(R.id.tv_name);
                holder.money = (TextView) convertView .findViewById(R.id.tv_money);
                holder.count = (TextView) convertView .findViewById(R.id.tv_count);
                holder.praise = (TextView) convertView .findViewById(R.id.tv_praise);
                holder.companyName = (TextView) convertView .findViewById(R.id.tv_companyName);



                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(bankList.get(position).name);
            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView name;
            TextView money;
            TextView count;
            TextView praise;
            TextView companyName;
        }
    }

    /**
     * 设置数据--测试
     */
    private void setData() {
        AdServiceBean bModel0 = new AdServiceBean();
        bModel0.name = "提供原创产品广告/策划广告文案/广告脚本";
        bModel0.money = "￥50000/单品";
        bModel0.count = "已出售：15笔";
        bModel0.praise = "100%";
        bModel0.companyName = "中广托普广告传媒有限公司";
        bankList.add(bModel0);

        AdServiceBean bModel1 = new AdServiceBean();
        bModel1.name = "提供原创产品广告/策划广告文案/广告脚本";
        bModel1.money = "￥50000/单品";
        bModel1.count = "已出售：15笔";
        bModel1.praise = "100%";
        bModel1.companyName = "中广托普广告传媒有限公司";
        bankList.add(bModel1);

        AdServiceBean bModel2 = new AdServiceBean();
        bModel2.name = "提供原创产品广告/策划广告文案/广告脚本";
        bModel2.money = "￥50000/单品";
        bModel2.count = "已出售：15笔";
        bModel2.praise = "100%";
        bModel2.companyName = "中广托普广告传媒有限公司";
        bankList.add(bModel2);

        AdServiceBean bModel3 = new AdServiceBean();
        bModel3.name = "提供原创产品广告/策划广告文案/广告脚本";
        bModel3.money = "￥50000/单品";
        bModel3.count = "已出售：15笔";
        bModel3.praise = "100%";
        bModel3.companyName = "中广托普广告传媒有限公司";
        bankList.add(bModel3);

        AdServiceBean bModel4 = new AdServiceBean();
        bModel4.name = "提供原创产品广告/策划广告文案/广告脚本";
        bModel4.money = "￥50000/单品";
        bModel4.count = "已出售：15笔";
        bModel4.praise = "100%";
        bModel4.companyName = "中广托普广告传媒有限公司";
        bankList.add(bModel4);
    }
}
