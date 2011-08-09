package com.tieto.ec.gui.dmr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class WarningMeter extends View{

	private int height = 100;
	private int width = 100;
	private int radius = width/2;
	
	private int actual;
	private int target;
	
	/**
	 * Creates a new WarningMeter from a {@link SectionWarning}
	 * @param dmr {@link Context} needed for Android framework actions
	 * @param sectionWarning The {@link SectionWarning} with the data
	 */
	public WarningMeter(Activity activity, SectionWarning sectionWarning) {
		super(activity);
		
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.CRITICAL || warning.getType() == Type.WARNING) {
				target += warning.getTargetValue();
				actual += warning.getActualValue();
			}
		}
		
		setLayoutParams(new LayoutParams(width, height));
		setBackgroundColor(Color.TRANSPARENT);
		setMinimumHeight(height);
		setMinimumWidth(width);
	}
	
	/**
	 * The method runs when the WarningMeter gets resized
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		height = getHeight();
		width = height;
		radius = width/2;
		
		invalidate();
	}
	
	/**
	 * Draws the actual WarningMeter
	 */
	@Override
	protected void onDraw(Canvas c) {
		//Super
		super.onDraw(c);
		
		//Border circle
		Paint borderPaint = new Paint();
		borderPaint.setAntiAlias(true);
		c.drawCircle(width/2, height/2, radius, borderPaint);

		//Green zone  	 	
		Paint greenZonePaint = new Paint();
		greenZonePaint.setAntiAlias(true);
		greenZonePaint.setColor(Color.GREEN);
		c.drawArc(new RectF(3, 3, width-3, height-3), 360, 40, true, greenZonePaint);
		
		//Yellow zone
		Paint yellowZonePaint = new Paint();
		int[] yellowColors = {Color.rgb(255, 0, 0), Color.YELLOW, Color.GREEN};
		float[] yellowPositions = {0.5f, 0.95f, 1};
		yellowZonePaint.setShader(new SweepGradient(width/2, height/2, yellowColors, yellowPositions));
		yellowZonePaint.setAntiAlias(true);
		yellowZonePaint.setColor(Color.YELLOW);
		c.drawArc(new RectF(3, 3, width-3, height-3), 289, 71, true, yellowZonePaint);

		//Red zone
		Paint redZonePaint = new Paint();
		int[] redColors = {Color.RED, Color.YELLOW};
		float[] redPositions = {0.4f, 1};
		redZonePaint.setShader(new SweepGradient(width/2, height/2, redColors, redPositions));
		redZonePaint.setAntiAlias(true);
		redZonePaint.setColor(Color.RED);
		c.drawArc(new RectF(3, 3, width-3, height-3), 140, 150, true, redZonePaint);
		
		//Main cirlce fill
		Paint circleZonePaint = new Paint();
		circleZonePaint.setShader(new LinearGradient(width/2, 0, width/2, height, Color.WHITE, Color.rgb(180, 201, 220), TileMode.CLAMP));
		circleZonePaint.setAntiAlias(true);
		circleZonePaint.setColor(Color.rgb(180, 201, 220));
		c.drawArc(new RectF(3, 3, width-3, height-3), 400, 100, true, circleZonePaint);
	
		//Borders
		Paint backgroundBorderPaint = new Paint();
		backgroundBorderPaint.setStrokeWidth(2);
		backgroundBorderPaint.setAntiAlias(true);
		backgroundBorderPaint.setColor(Color.BLACK);
		c.drawArc(new RectF(18, 18, width-18, height-18), 140, 260, true, backgroundBorderPaint);
		c.drawLine(width/2 + (float)Math.cos(Math.toRadians(140))*(radius-20), height/2 + (float)Math.sin(Math.toRadians(140))*(radius-20),
				width/2 + (float)Math.cos(Math.toRadians(140))*radius, height/2 + (float)Math.sin(Math.toRadians(140))*radius, backgroundBorderPaint);
		c.drawLine(width/2 + (float)Math.cos(Math.toRadians(400))*(radius-20), height/2 + (float)Math.sin(Math.toRadians(400))*(radius-20),
				width/2 + (float)Math.cos(Math.toRadians(400))*radius, height/2 + (float)Math.sin(Math.toRadians(400))*radius, backgroundBorderPaint);
		
		
		//Main circle
		Paint backgroundPaint = new Paint();
		backgroundPaint.setShader(new LinearGradient(width/2, 0, width/2, height, Color.WHITE, Color.rgb(180, 201, 220), TileMode.CLAMP));
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setColor(Color.rgb(180, 201, 220));
		c.drawCircle(width/2, height/2, radius-20, backgroundPaint);
		
		//Line
		Path path = new Path();
		double angle = Math.PI*0.2222 - (1-(actual)/(target*1f))*Math.PI*1.444;
		Paint linePaint = new Paint();
		linePaint.setShadowLayer(5f, 3, 3, Color.BLACK);
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.LTGRAY);
		linePaint.setStrokeWidth(3);
		path.moveTo(width/2 + (float)Math.cos(angle)*radius, height/2 + (float)Math.sin(angle)*radius);
		angle += Math.toRadians(165);
		path.lineTo(width/2 + (float)Math.cos(angle)*radius*0.3f, height/2 + (float)Math.sin(angle)*radius*0.3f);
		angle += Math.toRadians(30);
		path.lineTo(width/2 + (float)Math.cos(angle)*radius*0.3f, height/2 + (float)Math.sin(angle)*radius*0.3f);
		angle += Math.toRadians(165);
		c.drawPath(path, linePaint);
		
		//Inner circle
		Paint innerCirclePaint = new Paint();
		innerCirclePaint.setAntiAlias(true);
		innerCirclePaint.setShadowLayer(5f, 3, 3, Color.BLACK);
		innerCirclePaint.setColor(Color.GRAY);
		c.drawCircle(width/2, height/2, 10, innerCirclePaint);
		
		//Text
		String text = Math.round((actual/(target*1f)) * 100) + " %";
		Paint paint = new Paint();
		paint.setTextSize(20);
		paint.setAntiAlias(true);
		c.drawText(text, width/2f - width*0.1f, (float) (height*0.9), paint);
	}
}
