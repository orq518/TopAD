package com.topad.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.topad.R;
import com.topad.bean.SearchListBean;
import com.topad.util.AudioRecorder;
import com.topad.util.LogUtil;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 主界面
 */
public class OutDoorSearchListActivity extends BaseActivity implements View.OnClickListener {

    /**
     * title布局
     **/
    private TitleView mTitle;
    ListView listview;
    ArrayList<SearchListBean> dataList = new ArrayList<SearchListBean>();
    ListViewAdapter adapter;
    int curID=-1;

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
        Resources res = getResources();
        String[] huwaiString = res.getStringArray(R.array.huwai);
        //测试数据
        for (int i = 0; i < huwaiString.length; i++) {
            SearchListBean bean = new SearchListBean();
            bean.name = huwaiString[i];
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
                curID=position;
                for (int i = 0; i < dataList.size(); i++) {
                    SearchListBean bean = dataList.get(i);
                    bean.isSelected = false;
                }
                SearchListBean bean = dataList.get(position);
                bean.isSelected = true;
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(OutDoorSearchListActivity.this, OutDoorSearchSecondListActivity.class);
                intent.putExtra("type", position);
                startActivityForResult(intent, 1);


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
    public void onBack() {

        if(curID>-1) {
            Intent intent = new Intent();
            SearchListBean bean = dataList.get(curID);
            intent.putExtra("data", bean);
            if (!Utils.isEmpty(bean.type)) {
                setResult(RESULT_OK, intent);
            }
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
                convertView = mInflater.inflate(R.layout.search_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.left_ic = (ImageView) convertView.findViewById(R.id.left_ic);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.type = (TextView) convertView.findViewById(R.id.type);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            SearchListBean bean = dataList.get(position);
            if (bean.isSelected) {
                viewHolder.left_ic.setVisibility(View.VISIBLE);
            } else {
                viewHolder.left_ic.setVisibility(View.INVISIBLE);
            }
            if (!Utils.isEmpty(bean.type)) {
                viewHolder.type.setText(bean.type);
            } else {
                viewHolder.type.setText("");
            }
            viewHolder.name.setText(bean.name);
            return convertView;
        }


        class ViewHolder {
            ImageView left_ic;
            TextView name, type;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String type = data.getStringExtra("mediaType");
            for (int i = 0; i < dataList.size(); i++) {
                SearchListBean bean = dataList.get(i);
                bean.isSelected = false;
                bean.type = null;
            }
            SearchListBean bean = dataList.get(curID);
            bean.isSelected = true;
            bean.type = type;
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}

