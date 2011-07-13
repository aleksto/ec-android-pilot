package com.tieto.ec.gui.animation3D;

import com.tieto.ec.listeners.welcomActivity.WelcomeListener;

import android.app.Activity;
import android.opengl.GLSurfaceView;

public class WelcomeAnimation extends GLSurfaceView{

	public WelcomeAnimation(Activity activity) {
		//Super
		super(activity);
		
		//Thos
		setRenderer(new AnimationRenderer(activity));
		setOnClickListener(new WelcomeListener(activity));
	}
}
