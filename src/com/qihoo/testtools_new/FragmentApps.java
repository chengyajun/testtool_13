package com.qihoo.testtools_new;

import java.util.ArrayList;

import com.qihoo.testtools_new.adapter.AppsAdapter;

import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentApps extends Fragment {
	View rootView;
	ListView lv_apps;
	AppsAdapter adapter;
	AppsDataListBroadcastreceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_apps, null);
		initView();

		receiver = new AppsDataListBroadcastreceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TestToolNewApplication.INTENT_ACTION_BROADCAST_APPS_LIST);
		getActivity().registerReceiver(receiver, filter);
		return rootView;

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}

	private void initView() {
		lv_apps = (ListView) rootView.findViewById(R.id.lv_apps);

		// ģ������
		// data = new ArrayList<String>();
		// for (int i = 0; i < 10; i++) {
		// data.add("Ӧ��" + i);
		// }
		adapter = new AppsAdapter(getActivity(), R.layout.apps_listview_item,
				null);
		lv_apps.setAdapter(adapter);

	}

	// �㲥�����������ռ�������Ӧ�õĹ㲥���������ݺͽ���

	class AppsDataListBroadcastreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (TestToolNewApplication.INTENT_ACTION_BROADCAST_APPS_LIST
					.equals(action)) {
				// �������ݺͽ���
				adapter.setDataChanged(TestToolNewApplication.appsList);
				adapter.notifyDataSetChanged();
			}

		}

	}

}
