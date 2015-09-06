package com.qihoo.testtools_new;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.qihoo.testtools_new.bean.ApplicationBean;
import com.qihoo.testtools_new.thread.ListApplicationsThread;

public class TestToolNewApplication extends Application {

	public static TestToolNewApplication mySelf;
	public static List<ApplicationBean> appsList;
	public static String INTENT_ACTION_BROADCAST_APPS_LIST = "com.qihoo.testtools.new.apps.list";
	public static String INTENT_ACTION_BROADCAST_FLOAT_VIEW_STOP = "com.qihoo.testtools.new.float.view.stop";

	/** 存储的文件名 */
	public static final String fileName = "settingfile";
	/** 存储后的文件路径：/data/data/<package name>/shares_prefs + 文件名.xml */
	public static final String PATH = "/data/data/com.qihoo.testtools_new/shared_prefs/settingfile.xml";

	@Override
	public void onCreate() {
		super.onCreate();

		mySelf = this;

		appsList = new ArrayList<ApplicationBean>();

		// 创建SharedPreferences文件，用来存储设置信息

		// 判断文件是否存在，如果不存在则创建
		File file = new File(PATH);
		if (!file.exists()) {
			SharedPreferences preferences = getSharedPreferences(fileName,
					Activity.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putInt("time", 3);
			editor.putBoolean("isWindowOpen", true);
			editor.commit();
		}

		// 启动线程加载手机中所有的应用
		ListApplicationsThread appsThread = new ListApplicationsThread();
		appsThread.start();

	}

}
