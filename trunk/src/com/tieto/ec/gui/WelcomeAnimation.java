package com.tieto.ec.gui;

import com.tieto.R;
import com.tieto.ec.activities.LogIn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class WelcomeAnimation extends View{

	private Activity activity;
	
	private final int TOTAL_FRAMES = 200;
	private int frameCount = 0;
	private Bitmap bitmap;
	private Matrix matrix;
	private Paint paint;
	private int screenWidth, screenHeight, bitmapWidth, bitmapHeight;
	private float scale;
	
	//Physics
	private final float GRAVITATION = 9.81f;
	private float x;
	private float y = 0;
	private float vy = 0;
	private long t0;

	public WelcomeAnimation(Activity activity) {
		//Super
		super(activity);
		
		//this
		setBackgroundColor(Color.WHITE);

		//Init
		this.activity = activity;
		screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
		screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tieto2);
		bitmapHeight = bitmap.getHeight();
		bitmapWidth = bitmap.getWidth();
		matrix = new Matrix();
		paint = new Paint();
		scale = screenWidth/(bitmapWidth*1f)*0.5f;
		matrix.setScale(scale, scale);
		
		bitmapHeight = (int) (bitmapHeight*scale);
		bitmapWidth = (int) (bitmapWidth*scale);
		
		x = screenWidth/2 - bitmapWidth/2;
		
		t0 = System.currentTimeMillis();
	}
	
	@Override
	protected void onDraw(Canvas c) {
		//Super
		super.onDraw(c);
		Log.d("tieto", "painting frame nr " + frameCount);
		if(frameCount < TOTAL_FRAMES){
			//Physics
			long time = (long) (System.currentTimeMillis()-t0);
			y = (float) (vy*time/1000f + 0.5f*GRAVITATION*Math.pow(time/1000f, 2));
			
//			matrix.setTranslate(0, y);
			Log.d("tieto", "Y: " + y);
			
			//Drawing bitmap
			c.translate(x, y);
			c.drawBitmap(bitmap, matrix, paint);
			
			//Framecount
			frameCount++;
			
			if(y+bitmapHeight > screenHeight){
				t0 = System.currentTimeMillis();
				vy = -vy;
			}
			
			//Repainting view
			invalidate();
		}else{
			//Start login activity
			startingLogin();
		}
	}

	private void startingLogin() {
		Intent intent = new Intent(activity, LogIn.class);
		activity.startActivity(intent);
	}
}
