package com.topad.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topad.R;

/**
 * ${todo}<甄选项目>
 *
 * @author lht
 * @data: on 15/10/28 17:32
 */
public class SelectProjectFragmnet extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 设置activity的布局
		View view = inflater.inflate(R.layout.fragment_two, null);
		return view;
	}
}