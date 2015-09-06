package com.qihoo.testtools_new.thread;

import android.content.Context;
import android.content.Intent;

import com.qihoo.testtools_new.TestToolNewApplication;
import com.qihoo.testtools_new.utils.ApplicationsDataUtils;

public class ListApplicationsThread extends Thread {
	

	@Override
	public void run() {
		super.run();

		// ������������
		TestToolNewApplication.appsList = ApplicationsDataUtils

		.getApplicationsDataList(TestToolNewApplication.mySelf);

		// ���͹㲥�������ݺͽ���
		Intent intent = new Intent();
		intent.setAction(TestToolNewApplication.INTENT_ACTION_BROADCAST_APPS_LIST);
		TestToolNewApplication.mySelf.sendStickyBroadcast(intent);
	}

}
