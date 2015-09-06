package com.qihoo.testtools_new;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qihoo.testtools_new.view.ActionSheet;
import com.qihoo.testtools_new.view.SlidingMenu;

public class MainActivity extends FragmentActivity implements
ActionSheet.ActionSheetListener{

	ImageButton top_banner_change_btn, buttom_banner_apps,
			buttom_banner_history, buttom_banner_settings;
	TextView top_banner_title;
	FrameLayout fl_content;
	SlidingMenu mSlideMenu;

	FragmentApps mFragmentApps;
	FragmentHistory mFragmentHistory;
	FragmentSettings mFragmentSettings;

	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;
	MainBroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();

		mFragmentManager = getSupportFragmentManager();
		mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.replace(R.id.fl_content, mFragmentApps, "应用程序");
		mFragmentTransaction.commit();
		
		receiver = new MainBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.qihoo.testtool_new.setting_delete");
		registerReceiver(receiver, filter );

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initView() {
		top_banner_change_btn = (ImageButton) findViewById(R.id.top_banner_change_btn);
		buttom_banner_apps = (ImageButton) findViewById(R.id.buttom_banner_apps);
		buttom_banner_history = (ImageButton) findViewById(R.id.buttom_banner_history);
		buttom_banner_settings = (ImageButton) findViewById(R.id.buttom_banner_settings);
		top_banner_title = (TextView) findViewById(R.id.top_banner_title);
		fl_content = (FrameLayout) findViewById(R.id.fl_content);
		mSlideMenu = (SlidingMenu) findViewById(R.id.slidemenu);
		mFragmentApps = new FragmentApps();
		mFragmentHistory = new FragmentHistory();
		mFragmentSettings = new FragmentSettings();

	}

	private void initListener() {

		top_banner_change_btn.setOnClickListener(listener);
		buttom_banner_apps.setOnClickListener(listener);
		buttom_banner_history.setOnClickListener(listener);
		buttom_banner_settings.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.top_banner_change_btn: {// 点击切换按钮
				mSlideMenu.toggle();
			}
				break;
			case R.id.buttom_banner_apps: {// 点击应用程序

				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction.replace(R.id.fl_content, mFragmentApps,
						"应用程序");
				mFragmentTransaction.commit();
				// 恢复初始状态的界面显示
				clearAllBackground();
				// 设置点击按钮的图标变化
				buttom_banner_apps
						.setImageResource(R.drawable.buttom_banner_apps_a);
				top_banner_title.setText("应用程序");

			}
				break;
			case R.id.buttom_banner_history: {// 点击历史记录

				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction.replace(R.id.fl_content, mFragmentHistory,
						"历史记录");
				mFragmentTransaction.commit();
				// 恢复初始状态的界面显示
				clearAllBackground();
				// 设置点击按钮的图标变化
				buttom_banner_history
						.setImageResource(R.drawable.buttom_banner_history_a);
				top_banner_title.setText("历史记录");

			}
				break;
			case R.id.buttom_banner_settings: {// 点击设置

				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction.replace(R.id.fl_content,
						mFragmentSettings, "设置");
				mFragmentTransaction.commit();
				// 恢复初始状态的界面显示
				clearAllBackground();
				// 设置点击按钮的图标变化
				buttom_banner_settings
						.setImageResource(R.drawable.buttom_banner_settings_a);
				top_banner_title.setText("设置");
			}
				break;
			}

		}
	};

	private void clearAllBackground() {
		buttom_banner_apps.setImageResource(R.drawable.buttom_banner_apps);
		buttom_banner_history
				.setImageResource(R.drawable.buttom_banner_history);
		buttom_banner_settings
				.setImageResource(R.drawable.buttom_banner_settings);
		// main_my_info.setBackgroundResource(0);
		// main_friends_helper.setBackgroundResource(0);

	}
	
	class MainBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if("com.qihoo.testtool_new.setting_delete".equals(action)){
				//显示对话框
				
				setTheme(R.style.ActionSheetStyleiOS7);
				showActionSheet();
				
			}
			
		}
		
	}
	
	public void showActionSheet() {
		ActionSheet.createBuilder(this, getSupportFragmentManager())
				.setCancelButtonTitle("取消").setOtherButtonTitles("清除缓存")
				.setCancelableOnTouchOutside(true)
				.setListener(this).show();
	}

	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		
	}

	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		Toast.makeText(this, "------" + index, 3000).show();
	}

}
