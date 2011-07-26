package com.tieto.ec.gui.animation3D;

import com.tieto.ec.listeners.welcomActivity.WelcomeListener;

import android.app.Activity;
import android.opengl.GLSurfaceView;

public class WelcomeAnimation extends GLSurfaceView{

	/**
	 * Creates a new {@link WelcomeAnimation} view, Sets the renderer to {@link AnimationRenderer}
	 * and sets onClick to {@link WelcomeListener}
	 * @param activity {@link Activity} needed for Android framework actions
	 */
	public WelcomeAnimation(Activity activity) {
		//Super
		super(activity);
		
		//Thos
		setRenderer(new AnimationRenderer(activity));
		setOnClickListener(new WelcomeListener(activity));
	}
}
