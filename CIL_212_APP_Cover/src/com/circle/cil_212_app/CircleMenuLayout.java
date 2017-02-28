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

	// ��_�� ��һ����...

	private int mRadius; // ����layout�İ뾶...

	// ������������ֵ...��ѡȡ�뾶���ȵ�ʱ����Ҫʹ��...
	private float mMaxChildDimesionRadio = 1 / 4f;
	private float mCenterItemDimesionRadio = 1 / 3f;

	private LayoutInflater inflater; // �Զ�����ز���...

	private double StartAngle; // �����ʼ�Ƕ�...

	private String[] ItemTexts = new String[] { "HTML", "CSS", "JS", "JQuery",
			"DOM", "TEMPLETE" };
	private int[] ItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	private int TouchSlop;

	/*
	 * ���ٶȼ��
	 */

	private float DownAngle;
	private float TmpAngle;
	private long DownTime;
	private boolean isFling; // �ж��Ƿ���������ת...

	// ��_�� �ڶ�����..

	// ����������Ҫ����һ�����֣�Ȼ���ڲ����������Ҫ����Ŀؼ�...
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		inflater = LayoutInflater.from(context);
		/*
		 * ��̬���򲼾��м��ؿؼ�...
		 */
		for (int i = 0; i < ItemImgs.length; i++) {
			final int j = i;
			// ����һ��View��ͼ...�����ͼ����Դ��Ҫ����res��Դ��View�����res�е�Width��Heightȥ����һ������...
			// ������ݵ�ֵΪtrue����ô�����ͼ�����Ϊ��һ����ͼ������ͼ...�������Ƕ����ͼƬ��Դ���Ƕ������ڸ��Ե���������..
			// �����ҪΪÿһ��ͼƬ����һ��������View...
			View view = inflater.inflate(R.layout.web_circle_1, this, false);
			// ������Դ...
			ImageView iv = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView tv = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);
			iv.setImageResource(ItemImgs[i]);
			tv.setText(ItemTexts[i]);
			// ���ü����¼�...
			view.setOnClickListener(new OnClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), ItemTexts[j],
							Toast.LENGTH_SHORT).show();
				}
			});
			addView(view);// �����е�child view���뵽һ���ܼ����н��б���...
		}
		// ���ֵ�����ж��Ƿ���ô����¼�...����ƶ���ֵС�����ֵ�Ͳ������ƶ��ؼ�...
		TouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	// ��_�� ��������

	// �ڶ�̬���ز��ֵ�ʱ��������Ҫ��Ϊ�Ľ��м���...���ֵĴ�С...
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * �������������Ŀ����ΪView���ô�С... ֱ�����ó�ϵͳ���ݸ����������һ���Ƽ�����Сֵ...
		 */
		setMeasuredDimension(getSuggestedMinimumWidth(),
				getSuggestedMinimumHeight());

		// ��ȡ�뾶...
		mRadius = Math.max(getWidth(), getHeight());

		final int count = getChildCount(); // ��ȡ�ӿؼ���������������7...

		int childsize = (int) (mRadius * mMaxChildDimesionRadio);
		/*
		 * �����漰����һ��������ģʽ�����ģʽ����������...
		 * 
		 * MeasureSpec.UNSPECIFIED,����ͼ��������ͼʩ���κ����ƣ�����ͼ���Եõ�������Ҫ�Ĵ�С��
		 * 
		 * MeasureSpec.EXACTLY������ͼϣ������ͼ�Ĵ�С��specSize��ָ���Ĵ�С��
		 * 
		 * MeasureSpec.AT_MOST������ͼ�Ĵ�С�����specSize�еĴ�С��
		 */
		int childMode = MeasureSpec.EXACTLY;

		// �����е���View���е�������...˵���˾�����7��View���ý��в���...
		for (int i = 0; i < count; i++) {

			final View child = getChildAt(i);
			// �ӿؼ�������ʾ��ֱ������..
			if (child.getVisibility() == GONE) {
				continue;
			}
			int makeMeasureSpec = -1;
			// �ӿؼ�Ϊ����ͼ���ʱ��������뾶��СΪ1/3�������뾶�Ĵ�С...
			// ����ӿؼ���������Ҳ�ͱ�ʾΪ��Χ�ռ��ʱ������Ϊ�������뾶��1/4��С...
			// �����Ĳ�����������Ĵ����...
			if (child.getId() == R.id.id_circle_menu_item_center) {
				// �˲����Ƕ����ݺ�ģʽ��һ����װ...����һ��48λ��intֵ����32λ��ʾģʽ����16λ��ʾ��ֵ...
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * mCenterItemDimesionRadio), childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childsize,
						childMode);
			}
			// ���������������ļ������...���ݹ�ȥ����Ȼ��һ��48λ��ֵ��ϵͳ����ݴ��ݹ�ȥ��ģʽ����View���в�������...
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}
	}

	// ��_�� ���Ĳ���
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int layoutWidth = r - l;
		int layoutHeight = b - t;

		// �Ը��������в���...
		int layoutRadius = Math.max(layoutWidth, layoutHeight);

		final int childCount = getChildCount();
		int left, top;
		int radius = (int) (layoutRadius * mMaxChildDimesionRadio);

		// ������Ԫ�صĸ��������ýǶ�...
		float angleDelay = 360 / (getChildCount() - 1);

		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

			// ȡ�Ƕ�ֵ..
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

			// ����ǰ�滹��1/8�������������Ǹ��ı���...�����Ҫ������常�����Ķ�λ��...
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

		// ��������...
		int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
		int cr = cl + cView.getMeasuredWidth();
		cView.layout(cl, cl, cr, cr);
	}

	// ��_�� ���岿��
	private float mLastX;
	private float mLastY;

	private FlingRunnable mFlingRunnable; // ������һ���߳�...Ϊʵ�ֵ������ֶ�����һ������...

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		// ����ָ������Ч...
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		// ���²���...�¼������Դ��ı���...
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			DownAngle = getAngle(x, y);// ��ȡ�Ƕ�...
			DownTime = System.currentTimeMillis(); // ϵͳ�ĵ�ǰʱ��...
			TmpAngle = 0;

			// �����ǰ�ڽ��п��ٹ�������ô�Ƴ��Կ����ƶ��Ļص�...��ʵ��������������������ת������ת���ڼ���DOWN��һ�£���ôֱ�Ӿ�ֹͣ��ת...
			if (isFling) {
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		// �ƶ�����...
		case MotionEvent.ACTION_MOVE:

			// ��ȡ��ʼ�ͽ�����ĽǶ�...���������ƶ����ǽǶ�...�����Ҫ��ȡ�Ƕ�...
			float start = getAngle(mLastX, mLastY);
			float end = getAngle(x, y);

			// �ж�x,y��ֵ�Ƿ���1��4����...������ȴ�Ҷ����ף���Ϊ�˻�ȡ�Ƕ�ֵ...
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
				// ��һ�����޽Ƕ�Ϊ��...
				StartAngle += end - start;
				TmpAngle += end - start;
			} else {
				StartAngle += start - end;
				TmpAngle += start - end;
			}

			// ���¶Բ��ֽ�������...��ʵ���ǲ���ˢ�²��ֵ�һ������...
			requestLayout();

			// ����ʼֵ����Ϊ��ת���ֵ...
			mLastX = x;
			mLastY = y;

			break;
		// ̧�����...
		case MotionEvent.ACTION_UP:

			// ����ÿ�����ƶ��ĽǶ�...
			float anglePrMillionSecond = TmpAngle * 1000
					/ (System.currentTimeMillis() - DownTime);

			// �����ֵ�������ָ������ֵ����ô�ͻ���Ϊ�Ǽ��ٹ���...
			if (Math.abs(anglePrMillionSecond) > 230 && !isFling) {
				// ����һ���µ��̣߳�����������ɹ���...
				post(mFlingRunnable = new FlingRunnable(anglePrMillionSecond));
			}

			if (Math.abs(anglePrMillionSecond) > 230 || isFling) {
				return true;
			}
			break;
		}
		return super.dispatchTouchEvent(event);

	}

	// ��������������ת�ĽǶ�...����Ƕ�֮�󷵻�...���ظ�����ķ���...
	private float getAngle(float xTouch, float yTouch) {
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	// ���������Ƕ�����ֵ����һ���ж�..Ȼ�������ֵ����...Ŀ���ǲ��ԽǶ�...
	private int getQuadrant(float x, float y) {
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}
	}

	// ��_�� ��������
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
			// ������ת...
			isFling = true;
			StartAngle += (velocity / 30);
			velocity /= 1.0666F;
			postDelayed(this, 30);// ��Ҫ��֤ʱ�̶�ҳ�����ˢ��..��Ϊʼ��Ҫ�����µĲ���...
			requestLayout();
		}
	}
}
