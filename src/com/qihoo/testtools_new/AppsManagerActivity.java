package com.qihoo.testtools_new;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qihoo.testtools_new.bean.ApplicationBean;
import com.qihoo.testtools_new.service.FloatingService;
import com.qihoo.testtools_new.utils.GetAppLaunchTime;

public class AppsManagerActivity extends Activity {

	TextView app_manager_back, app_manager_appName, app_manager_count;
	ImageView app_manager_appImg;
	CheckBox app_manager_CPU_cb, app_manager_mem_cb, app_manager__bat_cb,
			app_manager_traffic_cb;
	Button app_manager_start_btn, app_manager_history_btn;
	CheckBox[] boxs = new CheckBox[4];

	ApplicationBean appBean;

	GetAppLaunchTime getAppLaunchTime;
	int position;

	boolean isAppStart = false;
	FloatViewStopBroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apps_manager);

		position = getIntent().getIntExtra("position", 0);
		appBean = TestToolNewApplication.appsList.get(position);

		getAppLaunchTime = new GetAppLaunchTime();

		initView();
		initData();
		initListener();

		// ע��㲥
		receiver = new FloatViewStopBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TestToolNewApplication.INTENT_ACTION_BROADCAST_FLOAT_VIEW_STOP);
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initView() {
		app_manager_back = (TextView) findViewById(R.id.app_manager_back);
		app_manager_appName = (TextView) findViewById(R.id.app_manager_appName);
		app_manager_count = (TextView) findViewById(R.id.app_manager_count);
		app_manager_appImg = (ImageView) findViewById(R.id.app_manager_appImg);
		app_manager_CPU_cb = (CheckBox) findViewById(R.id.app_manager_CPU_cb);
		app_manager_mem_cb = (CheckBox) findViewById(R.id.app_manager_mem_cb);
		app_manager__bat_cb = (CheckBox) findViewById(R.id.app_manager__bat_cb);
		app_manager_traffic_cb = (CheckBox) findViewById(R.id.app_manager_traffic_cb);
		app_manager_start_btn = (Button) findViewById(R.id.app_manager_start_btn);
		app_manager_history_btn = (Button) findViewById(R.id.app_manager_history_btn);
		boxs[0] = app_manager_CPU_cb;
		boxs[1] = app_manager_mem_cb;
		boxs[2] = app_manager__bat_cb;
		boxs[3] = app_manager_traffic_cb;

	}

	private void initData() {
		app_manager_appImg.setImageDrawable(appBean.getAppimage());
		app_manager_appName.setText(appBean.getAppname() + "");

	}

	private void initListener() {

		app_manager_back.setOnClickListener(lis);
		app_manager_CPU_cb.setOnClickListener(lis);
		app_manager_mem_cb.setOnClickListener(lis);
		app_manager__bat_cb.setOnClickListener(lis);
		app_manager_traffic_cb.setOnClickListener(lis);
		app_manager_start_btn.setOnClickListener(lis);
		app_manager_history_btn.setOnClickListener(lis);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.app_manager_back: {// �������
				AppsManagerActivity.this.finish();
			}
				break;
			case R.id.app_manager_CPU_cb: {// ���CPUcheckbox

				checkCount();
			}
				break;
			case R.id.app_manager_mem_cb: {
				checkCount();
			}
				break;
			case R.id.app_manager_traffic_cb: {
				checkCount();
			}
				break;
			case R.id.app_manager__bat_cb: {
				checkCount();
			}
				break;
			case R.id.app_manager_start_btn: {// ����������ԣ���ʼ���г��򣬽��м��

				if (isAppStart) {// ����,������رշ���

					isAppStart = false;

					app_manager_start_btn.setText("��������");
					Intent intent = new Intent();
					intent.setClass(AppsManagerActivity.this,
							FloatingService.class);
					// intent.putExtra("position", position);
					stopService(intent);

				} else {// ����ǹرգ�������������
					isAppStart = true;
					app_manager_start_btn.setText("ֹͣ����");

					// ������ʾ������Service
					start_flow();

					// ��������
					startApp();

				}
				// // ������ʾ������Service
				// start_flow();
				//
				// // ��������
				// startApp();

			}
				break;
			case R.id.app_manager_history_btn: {// ����鿴��¼����ת����¼ҳ
			}
				break;
			}

		}
	};

	protected void checkCount() {
		int count = 0;
		for (CheckBox box : boxs) {
			if (box.isChecked()) {// ѡ��

				count++;
			}
		}
		app_manager_count.setText("ѡ����Ե����ݣ�" + count);

	}

	private void startApp() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		if ("com.qihoo.testtools_new".equals(appBean.getPackagename())) {
			// ������Լ��Ͳ�kill
			return;
		} else {
			am.killBackgroundProcesses(appBean.getPackagename());

			Intent launchIntent = new Intent();
			launchIntent.setComponent(new ComponentName(appBean
					.getPackagename(), appBean.getMainactivity()));

			// thisTime����App������ʱ��
			long thisTime = getAppLaunchTime
					.startActivityWithTime(launchIntent);
			Toast.makeText(AppsManagerActivity.this,
					"���������ʱ" + thisTime + "����", Toast.LENGTH_LONG).show();
		}
	}

	private void start_flow() {
		Intent intent = new Intent();
		intent.setClass(this, FloatingService.class);
		intent.putExtra("position", position);
		startService(intent);
	}

	class FloatViewStopBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (TestToolNewApplication.INTENT_ACTION_BROADCAST_FLOAT_VIEW_STOP
					.equals(action)) {
				// ���½���
				isAppStart = false;
				app_manager_start_btn.setText("��������");

			}
		}

	}

}
