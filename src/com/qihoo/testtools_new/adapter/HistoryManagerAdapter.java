package com.qihoo.testtools_new.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qihoo.testtools_new.HistoryManagerDetailActivity;
import com.qihoo.testtools_new.R;
import com.qihoo.testtools_new.bean.ApplicationBean1;

public class HistoryManagerAdapter extends BaseAdapter {

	private Context mContext;
	private int mResId;
	private LayoutInflater inflater;
	private ArrayList<ApplicationBean1> tvList;

	// ���췽��
	public HistoryManagerAdapter(Context context, int resId,
			ArrayList<ApplicationBean1> list) {
		this.mContext = context;
		this.mResId = resId;
		this.inflater = LayoutInflater.from(context);
		setdata(list);
	}

	public void setdata(ArrayList<ApplicationBean1> list) {
		if (list == null) {
			list = new ArrayList<ApplicationBean1>();
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
		// ��������ͼ
		if (convertView == null) {
			convertView = inflater.inflate(mResId, null);
		}
		// ��ȡ��ǰֵ
		final ApplicationBean1 bean = tvList.get(position);

		TextView tvAppName = (TextView) convertView
				.findViewById(R.id.tv_history_manager_data);
		tvAppName.setText("" + bean.appName);
		CheckBox choose = (CheckBox) convertView
				.findViewById(R.id.history_manager_item_cb);

		choose.setChecked(bean.isChoose);

		choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bean.isChoose) {
					bean.isChoose = false;
					((CheckBox) v).setChecked(false);
					// bean.isChoose = isChecked;
					// ״̬�ı��򷢹㲥���Ľ���
					Intent intent = new Intent();
					intent.setAction("com.qihoo.testtool.new.history.manager.broadcastreceiver1");
					intent.putExtra("isChoose", false);
					mContext.sendBroadcast(intent);

				} else {
					bean.isChoose = true;
					// bean.isChoose = isChecked;
					// ״̬�ı��򷢹㲥���Ľ���
					Intent intent = new Intent();
					intent.setAction("com.qihoo.testtool.new.history.manager.broadcastreceiver1");
					intent.putExtra("isChoose", true);
					mContext.sendBroadcast(intent);
				}

			}
		});

		// ɾ������
		ImageButton historyDelete = (ImageButton) convertView
				.findViewById(R.id.history_manager_delete);
		historyDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "ɾ������", 3000).show();

			}
		});
		// //ʵ����ת
		// RelativeLayout history_manager_item_content = (RelativeLayout)
		// convertView
		// .findViewById(R.id.history_manager_item_content);
		// history_manager_item_content.setOnClickListener(new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(mContext, HistoryManagerDetailActivity.class);
		// mContext.startActivity(intent);
		// }
		// });

		// // �����ת�����Թ���ҳ��
		// convertView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// Log.i("info", "===========�����ת==========");
		// Intent intent = new Intent();
		// intent.setClass(mContext, HistoryManagerDetailActivity.class);
		// mContext.startActivity(intent);
		// }
		// });
		return convertView;
	}

}
