package com.qihoo.testtools_new.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qihoo.testtools_new.AppsManagerActivity;
import com.qihoo.testtools_new.HistoryManagerActivity;
import com.qihoo.testtools_new.R;

public class HistoryAdapter extends BaseAdapter {

	private Context mContext;
	private int mResId;
	private LayoutInflater inflater;
	private ArrayList<String> tvList;

	// 构造方法
	public HistoryAdapter(Context context, int resId, ArrayList<String> list) {
		this.mContext = context;
		this.mResId = resId;
		this.inflater = LayoutInflater.from(context);
		setdata(list);
	}

	private void setdata(ArrayList<String> list) {
		if (list == null) {
			list = new ArrayList<String>();
		}
		this.tvList = list;

	}

	@Override
	public int getCount() {
		return tvList.size();
	}

	@Override
	public Object getItem(int position) {
		return tvList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 可重用视图
		if (convertView == null) {
			convertView = inflater.inflate(mResId, null);
		}
		// 获取当前值
		String item = tvList.get(position);

		TextView tvAppName = (TextView) convertView
				.findViewById(R.id.tv_history_name);
		tvAppName.setText("" + item);

		// 点击跳转到测试管理页面
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, HistoryManagerActivity.class);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

}
