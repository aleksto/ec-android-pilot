package com.tieto.ec.gui.dmr;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.logic.SectionWarning;
import com.tieto.ec.logic.Warning;
import com.tieto.ec.logic.Warning.Type;

public class WarningBar extends View{

	private int height = 100;
	private int width = 100;
	private int radius = width/2;
	
	private int actual;
	private int target;
	
	public WarningBar(DailyMorningReport dmr, SectionWarning sectionWarning) {
		super(dmr);
		
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
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		height = getHeight();
		width = height;
		radius = width/2;
		
		invalidate();
	}
	
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
		
		//Main circle
		Paint backgroundPaint = new Paint();
		backgroundPaint.setShader(new LinearGradient(width/2, 0, width/2, height, Color.WHITE, Color.rgb(180, 201, 220), TileMode.CLAMP));
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setColor(Color.rgb(180, 201, 220));
		c.drawCircle(width/2, height/2, radius-20, backgroundPaint);
		
		//Inner circle
		Paint innerCirclePaint = new Paint();
		innerCirclePaint.setAntiAlias(true);
		innerCirclePaint.setColor(Color.DKGRAY);
		c.drawCircle(width/2, height/2, 10, innerCirclePaint);
		
		//Line
		Paint linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.BLACK);
		linePaint.setStrokeWidth(3);
		double angle = Math.PI*0.2222 - (1-(actual)/(target*1f))*Math.PI*1.444;
		c.drawLine(width/2, height/2, width/2 + (float)Math.cos(angle)*radius, height/2 + (float)Math.sin(angle)*radius, linePaint);
	}
}
