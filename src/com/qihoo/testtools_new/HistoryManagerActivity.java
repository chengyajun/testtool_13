package com.qihoo.testtools_new;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.qihoo.testtools_new.adapter.HistoryManagerAdapter;
import com.qihoo.testtools_new.bean.ApplicationBean1;

public class HistoryManagerActivity extends Activity {

	ListView lv_history;
	HistoryManagerAdapter adapter;
	ArrayList<ApplicationBean1> data;

	TextView history_manager_back;
	CheckBox history_all_cb;
	Button history_check_btn;

	String action = "com.qihoo.testtool.new.history.manager.broadcastreceiver1";
	int count = 0;
	HistoryManagerBroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_manager);
		initView();
		initListener();
		receiver = new HistoryManagerBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initView() {

		history_manager_back = (TextView) findViewById(R.id.history_manager_back);
		history_all_cb = (CheckBox) findViewById(R.id.history_all_cb);
		history_check_btn = (Button) findViewById(R.id.history_check_btn);
		lv_history = (ListView) findViewById(R.id.lv_history_manager);

		// 模拟数据
		data = new ArrayList<ApplicationBean1>();
		for (int i = 0; i < 10; i++) {
			ApplicationBean1 bean = new ApplicationBean1();
			bean.appName = "应用" + i;
			bean.appImg = "";
			bean.data = "1234545";
			bean.isBAT = true;
			bean.isCPU = true;
			bean.isMEM = true;
			bean.isTRA = true;
			bean.isChoose = false;
			data.add(bean);

		}
		adapter = new HistoryManagerAdapter(this,
				R.layout.history_manager_listview_item, data);
		lv_history.setAdapter(adapter);

	}

	private void initListener() {
		history_check_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (count > 0) {
					Intent intent = new Intent();
					intent.setClass(HistoryManagerActivity.this,
							HistoryManagerDetailActivity.class);
					HistoryManagerActivity.this.startActivity(intent);
				}
			}
		});

		history_manager_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HistoryManagerActivity.this.finish();

			}
		});
		history_all_cb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isChoose = ((CheckBox) v).isChecked();
				if (isChoose) {// 选中，则更改所有data的数据，重新显示
					for (ApplicationBean1 bean : data) {
						bean.isChoose = true;
					}
					count = data.size();
					history_check_btn.setBackgroundColor(getResources()
							.getColor(R.color.choose_count));
					history_check_btn.setText("统计（" + data.size() + "）");

				} else {// 未选中，修改data数据
					for (ApplicationBean1 bean : data) {
						bean.isChoose = false;
					}
					count = 0;
					history_check_btn.setBackgroundColor(getResources()
							.getColor(R.color.choose));
					history_check_btn.setText("统计（0）");
				}
				adapter.setdata(data);
				adapter.notifyDataSetChanged();
			}
		});
	}

	class HistoryManagerBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if ("com.qihoo.testtool.new.history.manager.broadcastreceiver1"
					.equals(action)) {
				// 更新界面
				boolean isChoose = intent.getBooleanExtra("isChoose", false);
				if (isChoose) {
					count = count + 1;
				} else {
					count = count - 1;
				}
				if (count > 0) {
					history_check_btn.setBackgroundColor(getResources()
							.getColor(R.color.choose_count));
					history_check_btn.setText("统计（" + count + "）");
				} else {
					history_check_btn.setBackgroundColor(getResources()
							.getColor(R.color.choose));
					history_check_btn.setText("统计（0）");
				}

				if (count == data.size()) {
					history_all_cb.setChecked(true);
				} else {
					history_all_cb.setChecked(false);
				}

			}
		}

	}

}
