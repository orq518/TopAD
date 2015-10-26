package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.topad.bean.NeedsListBean;
import com.topad.bean.SearchListBean;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * 主界面
 */
public class NeedsListActivity extends BaseActivity implements View.OnClickListener {

    String[] guanggaochuangyi = new String[]{"创意文案", "创意脚本", "创意策略", "广告语", "广告摄影"};
    String[] pingmiansheji = new String[]{"LOGO设计", "VI设计", "CIS 设计", "APP/UI 设计", "包装设计", "海报设计", "装修设计"};
    String[] yingxiaotuiguang = new String[]{"营销方案", "产品定位", "渠道建设", "网络营销", "终端促销", "新闻传播"};
    String[] yingshidongman = new String[]{"广告片拍摄", "影视后期", "宣传片制作", "广告配音/配乐", "动漫创作", "微电影拍摄", "寻找代言人", "MV 创作", "创意脚本", "创意策略", "广告语", "广告摄影"};
    String[] wenancehua = new String[]{"活动策划", "软文创作", "公文写作", "英文翻译", "网店文案", "品牌策划"};
    String[] guanggaojiance = new String[]{"客户广告投放监测报告", "竞品广告投放分析报告", " 行业广告投放分析报告", "单一媒体广告投放分析报告", "全媒体广告投放分析报告", "媒体价值分析报告"};
    String[] zhuanjiapeixun = new String[]{"找创意专家", "找媒体经营专家", "找企业管理专家", "找营销策划专家", "找广告界学术专家", "找广告主培训专家", "互联网培训专家"};
    String[] gusnlixicun = new String[]{"制定企业发展战略", "企业经营诊断", "股权激励策略", "企业上市/融资战略规划", "项目商业计划书撰写", "各类行业研究报告"};
    String[] wangzhanruanjian = new String[]{"网站规划/设计/编程", "APP 开发", "网站维护/代管/测试", "微信商务开发", "网站服务器托管/带宽租赁", "软件开发", "网站二次开发/升级/优化"};
    String[] gongguanfuwu = new String[]{"公关活动策划", "危机公关处理", "网络舆情监测", "事件营销方案策划", "公关培训服务"};
    String[] qiyezhaopin = new String[]{"广告公司类", "广告主类", "电视媒体类", "广播媒体类", "报纸/杂志类", "户外媒体类", "互联网媒体类", "营销策划类", "技术人才", "大学生实习", "兼职类"};
    String[] qitafuwu = new String[]{"品牌起名/公司起名", "名片设计", "图文输出", "出版印刷", "展览服务", "法律咨询服务"};
    String[] tempArray;
    /**
     * title布局
     **/
    private TitleView mTitle;
    ListView listview;
    ArrayList<NeedsListBean> dataList = new ArrayList<NeedsListBean>();
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

    @Override
    public void initViews() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                tempArray = guanggaochuangyi;
                break;
            case 1:
                tempArray = pingmiansheji;
                break;
            case 2:
                tempArray = yingxiaotuiguang;
                break;
            case 3:
                tempArray = yingshidongman;
                break;
            case 4:
                tempArray = wenancehua;
                break;
            case 5:
                tempArray = guanggaojiance;
                break;
            case 6:
                tempArray = zhuanjiapeixun;
                break;
            case 7:
                tempArray = gusnlixicun;
                break;
            case 8:
                tempArray = wangzhanruanjian;
                break;
            case 9:
                tempArray = gongguanfuwu;
                break;
            case 10:
                tempArray = qiyezhaopin;
                break;
            case 11:
                tempArray = qitafuwu;
                break;
        }
        //测试数据
        for (int i = 0; i < tempArray.length; i++) {
            NeedsListBean bean = new NeedsListBean();
            bean.name = tempArray[i];
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
                Intent intent = new Intent(NeedsListActivity.this, ShareNeedsEditActivity.class);
                startActivity(intent);
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
                convertView = mInflater.inflate(R.layout.needs_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.left_ic = (ImageView) convertView.findViewById(R.id.left_ic);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NeedsListBean bean = dataList.get(position);
            viewHolder.name.setText(bean.name);
            return convertView;
        }


        class ViewHolder {
            ImageView left_ic;
            TextView name;
        }

    }
}

