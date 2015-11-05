package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.topad.bean.NeedsListBean;
import com.topad.bean.SearchListBean;
import com.topad.util.SystemBarTintManager;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

import java.util.ArrayList;

/**
 * 主界面
 */
public class NeedsListActivity extends BaseActivity implements View.OnClickListener {

    String[] guanggaochuangyi = new String[]{"创意文案", "创意脚本", "创意策略", "广告语", "广告摄影"};
    int[] guanggaochuangyi_ic = new int[]{R.drawable.ai_wenan, R.drawable.ai_jiaoben, R.drawable.ai_celve, R.drawable.ai_slogan, R.drawable.ai_cam};
    String[] pingmiansheji = new String[]{"LOGO设计", "VI设计", "CIS 设计", "APP/UI 设计", "包装设计", "海报设计", "装修设计"};
    int[] pingmiansheji_ic = new int[]{R.drawable.vi_logo_design, R.drawable.vi_design, R.drawable.vi_cis_design, R.drawable.vi_ui_design, R.drawable.vi_baozhuang_design, R.drawable.vi_haibao_design, R.drawable.vi_zhuangxiu_design};
    String[] yingxiaotuiguang = new String[]{"营销方案", "产品定位", "渠道建设", "网络营销", "终端促销", "新闻传播"};
    int[] yingxiaotuiguang_ic = new int[]{R.drawable.sales_idea, R.drawable.sales_dingwei, R.drawable.sales_qudao, R.drawable.sales_wangluo, R.drawable.sales_cuxiao, R.drawable.sales_news};
    String[] yingshidongman = new String[]{"广告片拍摄", "影视后期", "宣传片制作", "广告配音/配乐", "动漫创作", "微电影拍摄", "寻找代言人", "MV 创作"};
    int[] yingshidongman_ic = new int[]{R.drawable.comic_paishe, R.drawable.comic_houqi, R.drawable.comic_xuanchuan, R.drawable.comic_peiyue, R.drawable.comic_dongman, R.drawable.comic_weidiany, R.drawable.comic_daiyan, R.drawable.comic_mv};
    String[] wenancehua = new String[]{"活动策划", "软文创作", "公文写作", "英文翻译", "网店文案", "品牌策划"};
    int[] wenancehua_ic = new int[]{R.drawable.write_huodong, R.drawable.write_ruanwen, R.drawable.write_gongwen, R.drawable.write_etranslat, R.drawable.write_wangdian, R.drawable.write_pinpai};
    String[] guanggaojiance = new String[]{"客户广告投放监测报告", "竞品广告投放分析报告", " 行业广告投放分析报告", "单一媒体广告投放分析报告", "全媒体广告投放分析报告", "媒体价值分析报告"};
    int[] guanggaojiance_ic = new int[]{R.drawable.jiance_kehu, R.drawable.jiance_jingpin, R.drawable.jiance_hangye, R.drawable.jiance_danyi, R.drawable.jiance_quanmeiti, R.drawable.jiance_jiazhi};
    String[] zhuanjiapeixun = new String[]{"找创意专家", "找媒体经营专家", "找企业管理专家", "找营销策划专家", "找广告界学术专家", "找广告主培训专家", "互联网培训专家"};
    int[] zhuanjiapeixun_ic = new int[]{R.drawable.teach_chuangyi, R.drawable.teach_meiti, R.drawable.teach_qiye, R.drawable.teach_yingxiao, R.drawable.teach_xueshu, R.drawable.teach_guanggaozhu, R.drawable.teach_web};
    String[] gusnlixicun = new String[]{"制定企业发展战略", "企业经营诊断", "股权激励策略", "企业上市/融资战略规划", "项目商业计划书撰写", "各类行业研究报告"};
    int[] gusnlixicun_ic = new int[]{R.drawable.guanli_fazhancl, R.drawable.guanli_zhenduan, R.drawable.guanli_guquanjl, R.drawable.guanli_rongzi, R.drawable.guanli_fazhancl, R.drawable.guanli_hangyeyj};
    String[] wangzhanruanjian = new String[]{"网站规划/设计/编程", "APP 开发", "网站维护/代管/测试", "微信商务开发", "网站服务器托管/带宽租赁", "软件开发", "网站二次开发/升级/优化"};
    int[] wangzhanruanjian_ic = new int[]{R.drawable.web_design, R.drawable.web_app, R.drawable.web_weihu, R.drawable.web_weixin, R.drawable.web_zulin, R.drawable.web_ruanjian, R.drawable.web_erci};
    String[] gongguanfuwu = new String[]{"公关活动策划", "危机公关处理", "网络舆情监测", "事件营销方案策划", "公关培训服务"};
    int[] gongguanfuwu_ic = new int[]{R.drawable.web_design, R.drawable.web_app, R.drawable.web_weihu, R.drawable.web_weixin, R.drawable.web_zulin, R.drawable.web_ruanjian, R.drawable.web_erci};
    String[] qiyezhaopin = new String[]{"广告公司类", "广告主类", "电视媒体类", "广播媒体类", "报纸/杂志类", "户外媒体类", "互联网媒体类", "营销策划类", "技术人才", "大学生实习", "兼职类"};
    int[] qiyezhaopin_ic = new int[]{R.drawable.zhaopin_adc, R.drawable.zhaopin_ad2, R.drawable.zhaopin_tv, R.drawable.zhaopin_radio, R.drawable.zhaopin_baokan,
            R.drawable.zhaopin_outdoor, R.drawable.zhaopin_web, R.drawable.zhaopin_yx, R.drawable.zhaopin_it, R.drawable.zhaopin_students, R.drawable.zhaopin_jianzhi};
    String[] qitafuwu = new String[]{"品牌起名/公司起名", "名片设计", "图文输出", "出版印刷", "展览服务", "法律咨询服务"};
    int[] qitafuwu_ic = new int[]{R.drawable.others_quming, R.drawable.others_mingpian, R.drawable.others_shuchu, R.drawable.others_chuban, R.drawable.others_zhanlan, R.drawable.others_law};

    String[] tempArray;
    int[] tempArray_ic;
    /**
     * title布局
     **/
    private TitleView mTitle;
    ListView listview;
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

    String titleString = null;

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
        Intent intent = getIntent();
        titleString = intent.getStringExtra("title");
        type = intent.getIntExtra("type", 0);
        switch (type) {
            case 0:
                tempArray = guanggaochuangyi;
                tempArray_ic = guanggaochuangyi_ic;
                break;
            case 1:
                tempArray = pingmiansheji;
                tempArray_ic = pingmiansheji_ic;
                break;
            case 2:
                tempArray = yingxiaotuiguang;
                tempArray_ic = yingxiaotuiguang_ic;
                break;
            case 3:
                tempArray = yingshidongman;
                tempArray_ic = yingshidongman_ic;
                break;
            case 4:
                tempArray = wenancehua;
                tempArray_ic = wenancehua_ic;
                break;
            case 5:
                tempArray = guanggaojiance;
                tempArray_ic = guanggaojiance_ic;
                break;
            case 6:
                tempArray = zhuanjiapeixun;
                tempArray_ic = zhuanjiapeixun_ic;
                break;
            case 7:
                tempArray = gusnlixicun;
                tempArray_ic = gusnlixicun_ic;
                break;
            case 8:
                tempArray = wangzhanruanjian;
                tempArray_ic = wangzhanruanjian_ic;
                break;
            case 9:
                tempArray = gongguanfuwu;
                tempArray_ic = gongguanfuwu_ic;
                break;
            case 10:
                tempArray = qiyezhaopin;
                tempArray_ic = qiyezhaopin_ic;
                break;
            case 11:
                tempArray = qitafuwu;
                tempArray_ic = qitafuwu_ic;
                break;
        }

        // 顶部布局
        mTitle = (TitleView) findViewById(R.id.title);
        // 设置顶部布局
        mTitle.setTitle(titleString);
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
            return tempArray.length;
        }

        @Override
        public Object getItem(int position) {
            return tempArray[position];
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
            viewHolder.name.setText(tempArray[position]);
            viewHolder.left_ic.setImageResource(tempArray_ic[position]);
            return convertView;
        }


        class ViewHolder {
            ImageView left_ic;
            TextView name;
        }

    }
}

