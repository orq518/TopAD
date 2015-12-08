package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.topad.R;
import com.topad.bean.AdServiceBean;
import com.topad.bean.SystemNewsBean;
import com.topad.util.Utils;
import com.topad.view.customviews.PTRListView;
import com.topad.view.customviews.PullToRefreshView;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * ${todo}<请描述这个类是干什么的>
 *
 * @author lht
 * @data: on 15/12/7 14:41
 */
public class SystemNewsActivity extends BaseActivity implements View.OnClickListener,
        PullToRefreshView.OnFooterRefreshListener{
    private static final String LTAG = SystemNewsActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 下载更多 **/
    private PullToRefreshView mPullToRefreshView;
    /** listView **/
    private PTRListView mListView;
    /** 适配器 **/
    private ListAdapter adapter;
    /** 数据源 **/
    private ArrayList<SystemNewsBean> bankList = new ArrayList<SystemNewsBean>();

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_system_news;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);;
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
        mListView = (PTRListView) findViewById(R.id.listview);

    }

    @Override
    public void initData() {
        showView();
        setData();
    }

    /**
     * 显示数据
     */
    private void showView() {
        // 设置顶部标题布局
        mTitleView.setTitle("我的消息");
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    /**
     * 顶部布局--按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            //
//            case R.id.btn_cash:
//
//                break;
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
                convertView = mInflater.inflate((R.layout.activity_system_news_item_layout), null);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.im_icon);
                holder.content = (TextView) convertView .findViewById(R.id.tv_content);
                holder.time = (TextView) convertView .findViewById(R.id.tv_time);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.content.setText(bankList.get(position).content);
            holder.time.setText(bankList.get(position).time);
            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView content;
            TextView time;
        }
    }

    /**
     * 设置数据--测试
     */
    private void setData() {
        SystemNewsBean bModel0 = new SystemNewsBean();
        bModel0.content = "名字山东科技发达是克己复礼看电视减肥了可是当减肥了看电视";
        bModel0.time = "1小时前";
        bankList.add(bModel0);

        SystemNewsBean bModel1 = new SystemNewsBean();
        bModel1.content = "名字山东科技发达是克己复礼看电视减肥了可是当减肥了看电视";
        bModel1.time = "1小时前";
        bankList.add(bModel1);

        SystemNewsBean bModel2 = new SystemNewsBean();
        bModel2.content = "名字山东科技发达是克己复礼看电视减肥了可是当减肥了看电视";
        bModel2.time = "1小时前";
        bankList.add(bModel2);

        SystemNewsBean bModel3 = new SystemNewsBean();
        bModel3.content = "名字山东科技发达是克己复礼看电视减肥了可是当减肥了看电视";
        bModel3.time = "1小时前";
        bankList.add(bModel3);

        SystemNewsBean bModel4 = new SystemNewsBean();
        bModel4.content = "名字山东科技发达是克己复礼看电视减肥了可是当减肥了看电视";
        bModel4.time = "1小时前";
        bankList.add(bModel4);
    }
}