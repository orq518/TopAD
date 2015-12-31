package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.topad.R;
import com.topad.util.Utils;
import com.topad.view.customviews.MyGridView;
import com.topad.view.customviews.TitleView;

/**
 * ${todo}<服务详情>
 *     category＝ 广告创意－1、营销策略－2、影视广告－3、动漫创作－4
 * @author lht
 * @data: on 15/10/26 18:32
 */
public class ADSDetailsActivity extends BaseActivity implements OnClickListener {
    private static final String LTAG = ADSDetailsActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** ScrollView **/
    private ScrollView mScrollView;
    /** 名称 **/
    private TextView mName;
    /** 价钱 **/
    private TextView mMoney;
    /** 笔数 **/
    private TextView mCount;
    /** 好评 **/
    private TextView mPraise;
    /** 成交额 **/
    private TextView mBusiness;
    /** 地址 **/
    private TextView mAddress;
    /** 内容 **/
    private TextView mContent;
    /** 案例 **/
    private MyGridView mGridView;
    /** 收藏 **/
    private Button mCollect;
    /** 联系服务商 **/
    private Button mCall;
    /** 购买此产品 **/
    private Button mBuy;

    /** 标题 **/
    private String title;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_ads_details;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);

        mName = (TextView) findViewById(R.id.tv_name);
        mMoney = (TextView) findViewById(R.id.tv_money);
        mCount = (TextView) findViewById(R.id.tv_count);
        mPraise = (TextView) findViewById(R.id.tv_praise);
        mBusiness = (TextView) findViewById(R.id.tv_business);
        mPraise = (TextView) findViewById(R.id.tv_praise);
        mAddress = (TextView) findViewById(R.id.tv_address);
        mContent = (TextView) findViewById(R.id.tv_introduce_content);
        mGridView = (MyGridView) findViewById(R.id.gv_case);
        mCollect = (Button) findViewById(R.id.btn_collect);
        mCall = (Button) findViewById(R.id.btn_call);
        mBuy = (Button) findViewById(R.id.btn_buy);

        mCollect.setOnClickListener(this);
        mCall.setOnClickListener(this);
        mBuy.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // 接收数据
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
        }

        // 显示数据
        showView();
    }

    /**
     * 显示数据
     */
    private void showView() {
        // 设置顶部标题布局
        if (!Utils.isEmpty(title)) {
            mTitleView.setTitle(title);
        }
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

        mScrollView.scrollTo(0, 0);

        //为GridView设置适配器
        mGridView.setAdapter(new MyAdapter(this));
        //注册监听事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(ADSDetailsActivity.this, ADSCaseActivity.class);
                intent.putExtra("title", "巡游VI设计");
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 收藏
            case R.id.btn_collect:

                break;
            // 联系服务商
            case R.id.btn_call:

                break;
            // 购买此产品
            case R.id.btn_buy:

                break;
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
            finish();
        }

    }

    //自定义适配器
    class MyAdapter extends BaseAdapter {
        //上下文对象
        private Context context;
        //图片数组
        private Integer[] imgs = {
                R.drawable.product0, R.drawable.product1, R.drawable.product2,
                R.drawable.product3, R.drawable.product0, R.drawable.product0,
                R.drawable.product0, R.drawable.product0, R.drawable.product0,
                R.drawable.product0, R.drawable.product0, R.drawable.product0,
                R.drawable.product0, R.drawable.product0, R.drawable.product0
        };

        MyAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return imgs.length;
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(
                        new GridView.LayoutParams(
                                (int) (getResources().getDisplayMetrics().density*115),
                                (int) (getResources().getDisplayMetrics().density*115)));//设置ImageView对象布局
                imageView.setAdjustViewBounds(false);//设置边界对齐
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
                imageView.setPadding(8, 8, 8, 8);//设置间距
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imgs[position]);//为ImageView设置图片资源
            return imageView;
        }
    }
}

