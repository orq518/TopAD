package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.topad.R;
import com.topad.bean.SearchListBean;
import com.topad.util.SystemBarTintManager;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * 户外搜索二级
 */
public class OtherSearchListActivity extends BaseActivity implements View.OnClickListener {


    int curID=-1;
    /**
     * title布局
     **/
    private TitleView mTitle;
    ListView listview;
    ArrayList<SearchListBean> dataList = new ArrayList<SearchListBean>();
    ListViewAdapter adapter;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutById() {
        return R.layout.activity_search_list;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    /** 沉浸式状态栏 **/
    private SystemBarTintManager mTintManager;
    private void applySelectedColor() {
        int color = Color.argb(0, Color.red(0), Color.green(0), Color.blue(0));
        mTintManager.setTintColor(color);
    }
    @Override
    public void initViews() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        applySelectedColor();
        type = getIntent().getIntExtra("searchType", 0);
        Resources res = getResources();
        String[] tempArray = null;
        switch (type) {
            case 0:
                tempArray = res.getStringArray(R.array.dianshi);
                break;
            case 1:
                tempArray = res.getStringArray(R.array.guangbo);
                break;
            case 2:
                tempArray = res.getStringArray(R.array.baozhi);
                break;
            case 4:
                tempArray = res.getStringArray(R.array.zazhi);
                break;
            case 5:
                tempArray = res.getStringArray(R.array.wangluo);
                break;
        }

        //测试数据
        for (int i = 0; i < tempArray.length; i++) {
            SearchListBean bean = new SearchListBean();
            bean.name = tempArray[i];
            if (curID == i) {
                bean.isSelected = true;
            }
            dataList.add(bean);
        }

        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(getString(R.string.main_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ListViewAdapter(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < dataList.size(); i++) {
                    SearchListBean bean = dataList.get(i);
                    bean.isSelected = false;
                }
                curID = position;
                SearchListBean bean = dataList.get(curID);
                bean.isSelected = true;
                adapter.notifyDataSetChanged();
                onBack();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            default:
                break;
        }
    }

    /**
     * 顶部布局--左按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBack();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBack() {
        if(curID>=0) {
            Intent intent = new Intent();
//            intent.putExtra("id", curID);
            SearchListBean bean = dataList.get(curID);
            intent.putExtra("mediaType", bean.name);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mCtx;
        private LayoutInflater mInflater;
        private ViewHolder mHolder;

        public ListViewAdapter(Context mCtx) {
            this.mCtx = mCtx;
            mInflater = LayoutInflater.from(mCtx);
        }


        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.search_second_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.left_ic = (ImageView) convertView.findViewById(R.id.left_ic);
                viewHolder.right_ic = (ImageView) convertView.findViewById(R.id.right_ic);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            SearchListBean bean = dataList.get(position);
            if (bean.isSelected) {
                viewHolder.left_ic.setVisibility(View.VISIBLE);
                viewHolder.right_ic.setVisibility(View.VISIBLE);
            } else {
                viewHolder.left_ic.setVisibility(View.INVISIBLE);
                viewHolder.right_ic.setVisibility(View.INVISIBLE);
            }
            viewHolder.name.setText(bean.name);
            return convertView;
        }


        class ViewHolder {
            ImageView left_ic,right_ic;
            TextView name;
        }

    }
}

