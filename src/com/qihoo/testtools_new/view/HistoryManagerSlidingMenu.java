package com.qihoo.testtools_new.view;

import com.qihoo.testtools_new.HistoryManagerDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class HistoryManagerSlidingMenu extends HorizontalScrollView {
	private LinearLayout mWapper;
	private ViewGroup mContent;
	private ViewGroup mRightDelete;
	private int mScreenWidth;

	private int mContentWidth;
	private int mRightDeleteWidth;
	// dp
	private int mMenuRightPadding = 80;

	private boolean once;

	private boolean isOpen;
	private Context mContext;

	/**
	 * δʹ���Զ�������ʱ������
	 * 
	 * @param context
	 * @param attrs
	 */
	public HistoryManagerSlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * ��ʹ�����Զ�������ʱ������ô˹��췽��
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public HistoryManagerSlidingMenu(Context context, AttributeSet attrs,
			int defStyle) {

		super(context, attrs, defStyle);
		this.mContext = context;
		if (!isInEditMode()) {
			// ��ȡ���Ƕ��������
			// TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
			// R.styleable.SlidingMenu, defStyle, 0);
			//
			// int n = a.getIndexCount();
			// for (int i = 0; i < n; i++) {
			// int attr = a.getIndex(i);
			// switch (attr) {
			// case R.styleable.SlidingMenu_rightPadding:
			// mMenuRightPadding = a.getDimensionPixelSize(attr,
			// (int) TypedValue
			// .applyDimension(
			// TypedValue.COMPLEX_UNIT_DIP, 100,
			// context.getResources()
			// .getDisplayMetrics()));
			// break;
			// }
			// }
			// a.recycle();

			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			mScreenWidth = outMetrics.widthPixels;

			mMenuRightPadding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources()
							.getDisplayMetrics());
		}
	}

	public HistoryManagerSlidingMenu(Context context) {
		this(context, null);
	}

	/**
	 * ������View�Ŀ�͸� �����Լ��Ŀ�͸�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!once) {

			mWapper = (LinearLayout) getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(0);
			mRightDelete = (ViewGroup) mWapper.getChildAt(1);
			mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
			mRightDeleteWidth = mRightDelete.getLayoutParams().width = mMenuRightPadding;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * ͨ������ƫ��������menu����
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(0, 0);
		}
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent ev) {
	// int action = ev.getAction();
	//
	// switch (action) {
	// case MotionEvent.ACTION_UP:
	//
	// // ��������ߵĿ��
	// int scrollX = getScrollX();
	//
	// if (scrollX >= mRightDeleteWidth / 2) {
	// this.smoothScrollTo(mRightDeleteWidth, 0);
	// isOpen = false;
	// } else {
	// this.smoothScrollTo(0, 0);
	// isOpen = true;
	// }
	// return true;
	// }
	// return super.onTouchEvent(ev);
	// }

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			// ���������ж�״̬�Ƿ�Ϊ��ʾ���������ʾ����ر�delete������ǹر��򲻴���
			if (isOpen) {
				this.smoothScrollTo(0, 0);

			}
			return true;
		}

		case MotionEvent.ACTION_UP: {
			// �ж��������ʾ״̬����״̬��Ϊ�رգ�����ǹر�״̬�����ж�λ��
			if (isOpen) {
				this.smoothScrollTo(0, 0);
				isOpen = false;
				
				return true;
			} else {
				// ��������ߵĿ��
				int scrollX = getScrollX();

				if (scrollX >= mRightDeleteWidth / 2) {
					this.smoothScrollTo(mRightDeleteWidth, 0);
					isOpen = true;
				} else if((scrollX < mRightDeleteWidth / 2)&&scrollX>0){
					this.smoothScrollTo(0, 0);
					isOpen = false;
				}else{
					this.smoothScrollTo(0, 0);
					isOpen = false;
					
					//ʵ����ת
					Intent intent = new Intent();
					intent.setClass(mContext, HistoryManagerDetailActivity.class);
					mContext.startActivity(intent);
				}
				return true;

			}
		}

		}
		return super.onTouchEvent(ev);
	}

	/**
	 * �򿪲˵�
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(mRightDeleteWidth, 0);
		isOpen = false;
	}

	/**
	 * �л��˵�
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	/**
	 * ��������ʱ
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mRightDeleteWidth; // 1 ~ 0
		Log.i("info", "----------" + l);
		// ViewHelper.setTranslationX(mRightDelete, -(mRightDeleteWidth-l));

	}

}
