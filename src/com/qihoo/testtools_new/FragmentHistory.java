package com.qihoo.testtools_new;

import java.util.ArrayList;

import com.qihoo.testtools_new.adapter.AppsAdapter;
import com.qihoo.testtools_new.adapter.HistoryAdapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentHistory extends Fragment {
	View rootView;
	ListView lv_history;
	HistoryAdapter adapter;
	ArrayList<String> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_history, null);
		initView();
		return rootView;
	}
	private void initView() {
		lv_history = (ListView) rootView.findViewById(R.id.lv_history);

		// 模拟数据
		data = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			data.add("应用" + i);
		}
		adapter = new HistoryAdapter(getActivity(), R.layout.history_listview_item,
				data);
		lv_history.setAdapter(adapter);

	}
}
