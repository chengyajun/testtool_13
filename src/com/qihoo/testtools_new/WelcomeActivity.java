package com.qihoo.testtools_new;

import com.qihoo.testtools_new.thread.ListApplicationsThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class WelcomeActivity extends Activity {

	private int DELAY = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// 3����ʵ����ת
		new Handler().postDelayed(new Runnable() {
			public void run() {
				begin();
			}
		}, DELAY);

		

	}

	// 3���ִ����ת
	protected void begin() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	// ����back����Ч
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
