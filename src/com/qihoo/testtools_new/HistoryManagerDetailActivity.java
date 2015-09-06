package com.qihoo.testtools_new;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

import com.qihoo.testtools_new.adapter.HistoryManagerDetailAdapter;
import com.qihoo.testtools_new.view.TopBannerCursor;

public class HistoryManagerDetailActivity extends Activity {

	TopBannerCursor ivCursor;
	Button history_manager_detail_cpu, history_manager_detail_mem,
			history_manager_detail_tra, history_manager_detail_bat;
	ViewPager history_manager_detail__viewpager;

	HistoryManagerDetailAdapter adapter;

	ArrayList<View> data = new ArrayList<View>();
	int oldPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_manager_detail);
		// 初始化视图
		initView();
		initData();
		// 初始化各种事件监听
		initListener();

		// 设置cursor的长度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		ivCursor.setSize(width / 4, 4);
	}

	private void initView() {
		history_manager_detail_cpu = (Button) findViewById(R.id.history_manager_detail_cpu);
		history_manager_detail_mem = (Button) findViewById(R.id.history_manager_detail_mem);
		history_manager_detail_tra = (Button) findViewById(R.id.history_manager_detail_tra);
		history_manager_detail_bat = (Button) findViewById(R.id.history_manager_detail_bat);
		history_manager_detail__viewpager = (ViewPager) findViewById(R.id.history_manager_detail__viewpager);
		ivCursor = (TopBannerCursor) findViewById(R.id.ivCursor);
	}

	private void initData() {
		LayoutInflater inflate = LayoutInflater.from(this);
		View v1 = inflate.inflate(R.layout.history_detail1, null);
		View v2 = inflate.inflate(R.layout.history_detail2, null);
		View v3 = inflate.inflate(R.layout.history_detail3, null);
		View v4 = inflate.inflate(R.layout.history_detail4, null);
		data.add(v1);
		data.add(v2);
		data.add(v3);
		data.add(v4);
		adapter = new HistoryManagerDetailAdapter(this, data);
		history_manager_detail__viewpager.setAdapter(adapter);
	}

	private void initListener() {

		// 当某一项被选中时，需要改变top_banner以及cursor
		history_manager_detail__viewpager
				.addOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {

						topBannerchanged(position);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
					}
				});

		history_manager_detail_cpu.setOnClickListener(bannerCilckListener);
		history_manager_detail_mem.setOnClickListener(bannerCilckListener);
		history_manager_detail_bat.setOnClickListener(bannerCilckListener);
		history_manager_detail_tra.setOnClickListener(bannerCilckListener);

	}

	OnClickListener bannerCilckListener = new OnClickListener() {

		@Override
		public void onClick(View view) {

			// 当点击某一项时，top_banner的变化和滑动viewpager是一样的，同时需要改变viewpager
			if (view == history_manager_detail_bat) {
				topBannerchanged(0);
				history_manager_detail__viewpager.setCurrentItem(0);
			} else if (view == history_manager_detail_cpu) {
				topBannerchanged(1);
				history_manager_detail__viewpager.setCurrentItem(1);
			} else if (view == history_manager_detail_mem) {
				topBannerchanged(2);
				history_manager_detail__viewpager.setCurrentItem(2);
			} else if (view == history_manager_detail_tra) {
				topBannerchanged(3);
				history_manager_detail__viewpager.setCurrentItem(3);
			}

		}
	};

	protected void topBannerchanged(final int position) {
		// 移动cursor
		int fromX = oldPosition * ivCursor.getWidth();
		int toX = position * ivCursor.getWidth();
		TranslateAnimation anim = new TranslateAnimation(fromX, toX, 0, 0);
		anim.setDuration(100);
		anim.setFillAfter(true);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

				// 改变top_banner的图标
				// ibCallLog.setImageResource(R.drawable.top_banner_call_05);
				// ibContact.setImageResource(R.drawable.top_banner_contact_05);
				// ibMessage.setImageResource(R.drawable.top_banner_message_05);
				// ibSetting.setImageResource(R.drawable.top_banner_setting);
				history_manager_detail_cpu.setTextColor(Color.WHITE);
				history_manager_detail_mem.setTextColor(Color.WHITE);
				history_manager_detail_bat.setTextColor(Color.WHITE);
				history_manager_detail_tra.setTextColor(Color.WHITE);
				switch (position) {
				case 0: {
					history_manager_detail_bat.setTextColor(Color.BLACK);
				}
					break;
				case 1: {
					history_manager_detail_cpu.setTextColor(Color.BLACK);
				}
					break;
				case 2: {
					history_manager_detail_mem.setTextColor(Color.BLACK);
				}
					break;
				case 3: {
					history_manager_detail_tra.setTextColor(Color.BLACK);
				}
					break;
				}

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
			}
		});

		ivCursor.startAnimation(anim);
		oldPosition = position;

	}

}
