package com.tieto.ec.gui.dmr;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.tieto.ec.model.CakePart;

public class CakeDiagram extends View{

	private final ArrayList<CakePart> parts;
	private double maxValue;
	private float width, height;

	public CakeDiagram(Context context, CakePart ... parts) {
		super(context);
		this.parts = new ArrayList<CakePart>();
		
		for (CakePart cakePart : parts) {
			this.parts.add(cakePart);
			maxValue += cakePart.getValue();
		}
	}
	
	public void addCakePart(CakePart part){
		this.parts.add(part);
		maxValue += part.getValue();
	}
	
	public void setMax(double maxValue){
		this.maxValue = maxValue;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width = getWidth();
		height = getHeight();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//Super
		super.onDraw(canvas);
		
		//
		float startAngle = 0;
		Paint paint;
		float sweepAngle;
		for (int i = 0; i < parts.size(); i++) {
			paint = new Paint();
			paint.setColor(parts.get(i).getColor());
			sweepAngle = (float) (parts.get(i).getValue()/maxValue*360);
			
			canvas.drawArc(new RectF(0, 0, width, height), startAngle, (float)(sweepAngle), true, paint);
			
			startAngle += sweepAngle;
		}
	}
}
