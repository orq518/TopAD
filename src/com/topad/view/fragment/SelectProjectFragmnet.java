package com.topad.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.topad.R;
import com.topad.bean.ChildBean;
import com.topad.bean.GroupBean;
import com.topad.util.LogUtil;
import com.topad.view.activity.GrabSingleDetailsActivity;
import com.topad.view.adapter.SelectProjectEListAdapter;
import com.topad.view.customviews.CustomExpandableListView;
import com.topad.view.customviews.PTRListView;
import com.topad.view.customviews.PullToRefreshView;
import com.topad.view.customviews.TitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * ${todo}<甄选项目>
 *
 * @author lht
 * @data: on 15/10/28 17:32
 */
public class SelectProjectFragmnet extends BaseFragment {
	private static final String LTAG = SelectProjectFragmnet.class.getSimpleName();
	/** 上下文 **/
	private Context mContext;
	/** 根view布局 **/
	private View mRootView;
	/** 数据源 **/
	private ArrayList<GroupBean> groups;
	/** listView **/
	private CustomExpandableListView listView;
	/** 适配器 **/
	private SelectProjectEListAdapter adapter;

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
		mRootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fargment_select_project, null);
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
		groups = new ArrayList<GroupBean>();
		getData();
		listView = (CustomExpandableListView) mRootView.findViewById(R.id.listView);
		adapter = new SelectProjectEListAdapter(mContext, groups, listView);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(adapter);
		listView.setGroupIndicator(null);
	}

	@Override
	public void setVisible(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		LogUtil.d("是否显示：" + isVisibleToUser + "    isNeedRefresh:" + isNeedRefresh);
		if (isVisibleToUser && isNeedRefresh) {
			isNeedRefresh = false;
		}
	}

	/** 初始化数据 */
	private void getData() {
		String[] group = new String[]{"全部", "广告创意", "平面设计", "营销推广", "影视动漫", "文案策划", "广告监测", "公关服务", "网站软件", "管理咨询", "其他服务"};
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

		for(int i=0; i<group.length; i++){
			GroupBean groupBean = new GroupBean(group[i]);
			// 全选
			if(i==0){

			}
			else if(i==1){
				for (int j = 0; j < guanggaochuangyi.length; j++) {
					ChildBean childBean = new ChildBean("0", guanggaochuangyi[j], "1");
					groupBean.addChildrenItem(childBean);
				}
			}
			else if(i==2){
				for (int j = 0; j < pingmiansheji.length; j++) {
					ChildBean childBean = new ChildBean("0", pingmiansheji[j], "0" );
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==3){
				for (int j = 0; j < yingxiaotuiguang.length; j++) {
					ChildBean childBean = new ChildBean("0", yingxiaotuiguang[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==4){
				for (int j = 0; j < yingshidongman.length; j++) {
					ChildBean childBean = new ChildBean("0", yingshidongman[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==5){
				for (int j = 0; j < wenancehua.length; j++) {
					ChildBean childBean = new ChildBean("0", wenancehua[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==6){
				for (int j = 0; j < guanggaojiance.length; j++) {
					ChildBean childBean = new ChildBean("0", guanggaojiance[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==7){
				for (int j = 0; j < guanggaochuangyi.length; j++) {
					ChildBean childBean = new ChildBean("0", guanggaochuangyi[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==8){
				for (int j = 0; j < gongguanfuwu.length; j++) {
					ChildBean childBean = new ChildBean("0", gongguanfuwu[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==9){
				for (int j = 0; j < wangzhanruanjian.length; j++) {
					ChildBean childBean = new ChildBean("0", wangzhanruanjian[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==10){
				for (int j = 0; j < gusnlixicun.length; j++) {
					ChildBean childBean = new ChildBean("0", gusnlixicun[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}else if(i==11){
				for (int j = 0; j < qitafuwu.length; j++) {
					ChildBean childBean = new ChildBean("0", qitafuwu[j], "0");
					groupBean.addChildrenItem(childBean);
				}
			}

			groups.add(groupBean);
		}
	}
}