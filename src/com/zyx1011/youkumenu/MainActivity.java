package com.zyx1011.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	private View mMenu1;
	private View mMenu2;
	private View mMenu3;
	private ImageView mIvMenu1Home;
	private ImageView mIvMenu2Middle;

	private boolean mMenuSecond;
	private boolean mMenuThird;
	private boolean mIsMenuKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initEvent();
	}

	private void initEvent() {
		mIvMenu1Home.setOnClickListener(this);
		mIvMenu2Middle.setOnClickListener(this);
	}

	private void initData() {
		mMenuSecond = true;
		mMenuThird = true;
		mIsMenuKey = false; // 是否为菜单键
	}

	private void initView() {
		mMenu1 = findViewById(R.id.menu1);
		mMenu2 = findViewById(R.id.menu2);
		mMenu3 = findViewById(R.id.menu3);

		mIvMenu1Home = (ImageView) findViewById(R.id.menu1_home);
		mIvMenu2Middle = (ImageView) findViewById(R.id.menu2_middle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu1_home:
			if (mMenuSecond) {
				if (mMenuThird) {
					// mMenu3.setVisibility(View.GONE);
					outAnimation(mMenu3, mIvMenu1Home);
					mMenuThird = false;
				}
				// mMenu2.setVisibility(View.GONE);
				outAnimation(mMenu2, mIvMenu1Home);
				mIvMenu2Middle.setEnabled(false);
				mMenuSecond = false;
			} else {
				// mMenu2.setVisibility(View.VISIBLE);
				inAnimation(mMenu2, mIvMenu1Home);
				mIvMenu2Middle.setEnabled(true);
				mMenuSecond = true;
			}
			break;

		case R.id.menu2_middle:
			if (mMenuThird) {
				// mMenu3.setVisibility(View.GONE);
				outAnimation(mMenu3, mIvMenu2Middle);
				mMenuThird = false;
			} else {
				// mMenu3.setVisibility(View.VISIBLE);
				inAnimation(mMenu3, mIvMenu2Middle);
				mMenuThird = true;
			}
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!mIsMenuKey) {
				if (mMenuSecond && mMenuThird) {
					// mMenu1.setVisibility(View.GONE);
					// mMenu2.setVisibility(View.GONE);
					// mMenu3.setVisibility(View.GONE);
					outAnimation(mMenu1, null);
					outAnimation(mMenu2, null);
					outAnimation(mMenu3, null);

					mIvMenu1Home.setEnabled(false);
					mIvMenu2Middle.setEnabled(false);

					mMenuSecond = false;
					mMenuThird = false;
				} else {
					// mMenu1.setVisibility(View.VISIBLE);
					// mMenu2.setVisibility(View.VISIBLE);
					// mMenu3.setVisibility(View.VISIBLE);
					inAnimation(mMenu1, null);
					inAnimation(mMenu2, null);
					inAnimation(mMenu3, null);

					mIvMenu1Home.setEnabled(true);
					mIvMenu2Middle.setEnabled(true);

					mMenuSecond = true;
					mMenuThird = true;
				}
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void inAnimation(View container, final View restrain) {
		RotateAnimation animation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(800);
		animation.setFillAfter(true);
		container.startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				if (restrain != null) {
					restrain.setEnabled(true);
				} else {
					mIsMenuKey = true;
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (restrain != null) {
					restrain.setEnabled(true);
				} else {
					mIsMenuKey = false;
				}
			}
		});
	}

	public void outAnimation(View container, final View restrain) {
		RotateAnimation animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(800);
		animation.setFillAfter(true);
		container.startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				if (restrain != null) {
					restrain.setEnabled(false);
				} else {
					mIsMenuKey = true;
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (restrain != null) {
					restrain.setEnabled(true);
				} else {
					mIsMenuKey = false;
				}
			}
		});
	}

}
