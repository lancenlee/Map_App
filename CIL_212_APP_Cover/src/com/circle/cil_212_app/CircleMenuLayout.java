package com.circle.cil_212_app;

import com.example.cil_212_app.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class CircleMenuLayout extends ViewGroup {

	// →_→ 第一部分...

	private int mRadius; // 定义layout的半径...

	// 定义了两个数值...在选取半径长度的时候需要使用...
	private float mMaxChildDimesionRadio = 1 / 4f;
	private float mCenterItemDimesionRadio = 1 / 3f;

	private LayoutInflater inflater; // 自定义加载布局...

	private double StartAngle; // 定义初始角度...

	private String[] ItemTexts = new String[] { "HTML", "CSS", "JS", "JQuery",
			"DOM", "TEMPLETE" };
	private int[] ItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	private int TouchSlop;

	/*
	 * 加速度检测
	 */

	private float DownAngle;
	private float TmpAngle;
	private long DownTime;
	private boolean isFling; // 判断是否在自由旋转...

	// →_→ 第二部分..

	// 首先我们需要定义一个布局，然后在布局中添加想要加入的控件...
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		inflater = LayoutInflater.from(context);
		/*
		 * 动态的向布局中加载控件...
		 */
		for (int i = 0; i < ItemImgs.length; i++) {
			final int j = i;
			// 定义一个View视图...这个视图的资源需要引用res资源，View会根据res中的Width和Height去加载一个布局...
			// 如果传递的值为true，那么这个视图将会成为上一个视图的子视图...由于我们定义的图片资源都是独立存在各自的容器当中..
			// 因此需要为每一个图片设置一个单独的View...
			View view = inflater.inflate(R.layout.web_circle_1, this, false);
			// 加载资源...
			ImageView iv = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView tv = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);
			iv.setImageResource(ItemImgs[i]);
			tv.setText(ItemTexts[i]);
			// 设置监听事件...
			view.setOnClickListener(new OnClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), ItemTexts[j],
							Toast.LENGTH_SHORT).show();
				}
			});
			addView(view);// 把所有的child view放入到一个总集合中进行保存...
		}
		// 这个值用来判断是否调用触发事件...如果移动的值小于这个值就不触发移动控件...
		TouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	// →_→ 第三部分

	// 在动态加载布局的时候，我们需要人为的进行计算...布局的大小...
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * 调用这个方法的目的是为View设置大小... 直接设置成系统根据父容器算出的一个推荐的最小值...
		 */
		setMeasuredDimension(getSuggestedMinimumWidth(),
				getSuggestedMinimumHeight());

		// 获取半径...
		mRadius = Math.max(getWidth(), getHeight());

		final int count = getChildCount(); // 获取子控件的数量，这里是7...

		int childsize = (int) (mRadius * mMaxChildDimesionRadio);
		/*
		 * 这里涉及到了一个测量的模式，这个模式有三种属性...
		 * 
		 * MeasureSpec.UNSPECIFIED,父视图不对子视图施加任何限制，子视图可以得到任意想要的大小；
		 * 
		 * MeasureSpec.EXACTLY，父视图希望子视图的大小是specSize中指定的大小；
		 * 
		 * MeasureSpec.AT_MOST，子视图的大小最多是specSize中的大小。
		 */
		int childMode = MeasureSpec.EXACTLY;

		// 对所有的子View进行迭代测量...说白了就是这7个View都得进行测量...
		for (int i = 0; i < count; i++) {

			final View child = getChildAt(i);
			// 子控件不可显示，直接跳过..
			if (child.getVisibility() == GONE) {
				continue;
			}
			int makeMeasureSpec = -1;
			// 子控件为中心图标的时候，设置其半径大小为1/3父容器半径的大小...
			// 如果子控件是其他，也就表示为周围空间的时候，设置为父容器半径的1/4大小...
			// 测量的步骤在于下面的代码块...
			if (child.getId() == R.id.id_circle_menu_item_center) {
				// 此步骤是对数据和模式的一个封装...返回一个48位的int值，高32位表示模式，低16位表示数值...
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * mCenterItemDimesionRadio), childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childsize,
						childMode);
			}
			// 这个步骤才是真正的计算过程...传递过去的仍然是一个48位的值，系统会根据传递过去的模式来对View进行参数设置...
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}
	}

	// →_→ 第四部分
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int layoutWidth = r - l;
		int layoutHeight = b - t;

		// 对父容器进行布局...
		int layoutRadius = Math.max(layoutWidth, layoutHeight);

		final int childCount = getChildCount();
		int left, top;
		int radius = (int) (layoutRadius * mMaxChildDimesionRadio);

		// 根据子元素的个数，设置角度...
		float angleDelay = 360 / (getChildCount() - 1);

		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

			// 取角度值..
			StartAngle %= 360;

			float tmp = layoutRadius * 1f / 3 - 1 / 22f * layoutRadius;

			left = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(StartAngle)) - 1 / 2f
							* radius);
			top = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.sin(Math.toRadians(StartAngle)) - 1 / 2f
							* radius);

			// Log.e("TAG", "left = " + left + " , top = " + top);

			// 由于前面还有1/8长度用来放置那个文本框...因此需要求出整体父容器的定位点...
			child.layout(left, top, left + radius, top + radius);
			StartAngle += angleDelay;
		}

		View cView = findViewById(R.id.id_circle_menu_item_center);
		cView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(getContext(), "aa", Toast.LENGTH_LONG).show();
			}
		});

		// 设置中心...
		int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
		int cr = cl + cView.getMeasuredWidth();
		cView.layout(cl, cl, cr, cr);
	}

	// →_→ 第五部分
	private float mLastX;
	private float mLastY;

	private FlingRunnable mFlingRunnable; // 定义了一个线程...为实现第六部分定义了一个对象...

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		// 随手指滑动特效...
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		// 按下操作...事件机制自带的变量...
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			DownAngle = getAngle(x, y);// 获取角度...
			DownTime = System.currentTimeMillis(); // 系统的当前时间...
			TmpAngle = 0;

			// 如果当前在进行快速滚动，那么移除对快速移动的回调...其实就是如果这个界面正在旋转，在旋转的期间我DOWN了一下，那么直接就停止旋转...
			if (isFling) {
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		// 移动操作...
		case MotionEvent.ACTION_MOVE:

			// 获取开始和结束后的角度...我们这里移动的是角度...因此需要获取角度...
			float start = getAngle(mLastX, mLastY);
			float end = getAngle(x, y);

			// 判断x,y的值是否在1，4象限...象限相比大家都明白，是为了获取角度值...
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
				// 在一四象限角度为正...
				StartAngle += end - start;
				TmpAngle += end - start;
			} else {
				StartAngle += start - end;
				TmpAngle += start - end;
			}

			// 重新对布局进行设置...其实就是不断刷新布局的一个过程...
			requestLayout();

			// 将初始值设置为旋转后的值...
			mLastX = x;
			mLastY = y;

			break;
		// 抬起操作...
		case MotionEvent.ACTION_UP:

			// 计算每秒钟移动的角度...
			float anglePrMillionSecond = TmpAngle * 1000
					/ (System.currentTimeMillis() - DownTime);

			// 如果数值大于这个指定的数值，那么就会认为是加速滚动...
			if (Math.abs(anglePrMillionSecond) > 230 && !isFling) {
				// 开启一个新的线程，让其进行自由滚动...
				post(mFlingRunnable = new FlingRunnable(anglePrMillionSecond));
			}

			if (Math.abs(anglePrMillionSecond) > 230 || isFling) {
				return true;
			}
			break;
		}
		return super.dispatchTouchEvent(event);

	}

	// 这里用来测试旋转的角度...算出角度之后返回...返回给上面的方法...
	private float getAngle(float xTouch, float yTouch) {
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	// 在这里我们对坐标值进行一个判断..然后把坐标值返回...目的是测试角度...
	private int getQuadrant(float x, float y) {
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}
	}

	// →_→ 第六部分
	private class FlingRunnable implements Runnable {

		private float velocity;

		public FlingRunnable(float velocity) {
			this.velocity = velocity;
		}

		public void run() {
			if ((int) Math.abs(velocity) < 20) {
				isFling = false;
				return;
			}
			// 减速旋转...
			isFling = true;
			StartAngle += (velocity / 30);
			velocity /= 1.0666F;
			postDelayed(this, 30);// 需要保证时刻对页面进行刷新..因为始终要进行新的布局...
			requestLayout();
		}
	}
}
