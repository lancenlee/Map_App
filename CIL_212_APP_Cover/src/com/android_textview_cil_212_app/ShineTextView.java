package com.android_textview_cil_212_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class ShineTextView extends TextView{

	//������Ⱦ���֣���Ⱦ���ֵ���ɫЧ��..
	private LinearGradient linearGradient;
	//���ν�����ȾЧ��...
	private RadialGradient radialGradient;
	//������Ⱦ����...
	private Matrix matrix;
	//���廭��..
	private Paint paint;
    private int ViewWidth=0;
    private int ViewHeight=0;
    //������ʾ..
    private int TransWidth=0;
    private int TransHeight=0;
    
	public ShineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		if(ViewWidth==0||ViewHeight==0){
			ViewHeight=getMeasuredHeight();
			ViewWidth=getMeasuredWidth();
		    if(ViewHeight>0||ViewWidth>0){
		    	paint=getPaint();
		    	//���û��ν�����Ⱦ...
		    	radialGradient=new RadialGradient(50, 50, 85, new int[]{0x11FEB726, 0xffFEB726, 0x33FEB726}, new float[]{0,0.5f,1},Shader.TileMode.CLAMP);
		    	linearGradient=new LinearGradient(-ViewWidth, 0, 0, 0, new int[]{0x33ffffff, 0xffffffff, 0x33ffffff}, new float[]{0,0.5f,1},Shader.TileMode.CLAMP);
		    	
		        paint.setShader(radialGradient);  //������ɫ��.��ɫ������radialGradient���õ����Խ�����ɫ..
		        paint.setColor(Color.parseColor("#ffffffff"));
		        
		        paint.setShadowLayer(3, 2, 2, 0xFFFF00FF);
		        matrix=new Matrix();
		    }
		}
	}
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(matrix!=null){
			TransHeight += ViewHeight/10;
			TransWidth  += ViewWidth/10;
			if(TransHeight>2*ViewHeight && TransWidth>2*ViewWidth){
				TransHeight = -ViewHeight;
				TransWidth  = -ViewWidth;
			}
			matrix.setTranslate(TransWidth, ViewHeight);
			radialGradient.setLocalMatrix(matrix);
			postInvalidateDelayed(50);
		}
	}

}
