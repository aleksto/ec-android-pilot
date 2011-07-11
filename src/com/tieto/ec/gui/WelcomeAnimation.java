package com.tieto.ec.gui;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.LensFlare;
import com.threed.jpct.util.MemoryHelper;
import com.tieto.R;
import com.tieto.ec.activities.LogIn;
import com.tieto.ec.temp.HelloWorld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.View;

public class WelcomeAnimation extends GLSurfaceView implements Renderer{

	private FrameBuffer fb;
	private World world;
	private Object3D cube;
	private RGBColor back = new RGBColor(0, 0, 0);
	private float rad = (float) (Math.PI/200f);
	private ArrayList<Object3D> lightSpheres;
	private ArrayList<Light> lights;
	private SimpleVector degrees;

	public WelcomeAnimation(Context context) {
		super(context);
		
		setRenderer(this);
	}

	public void onDrawFrame(GL10 gl) {
		//Main sphere
		cube.rotateY(rad);

		//Orbits
		for (Object3D light : lightSpheres) {
			light.rotateY(rad);
		}
		
		for (Light light : lights) {
			int idx = lights.indexOf(light);
			light.setPosition(lightSpheres.get(idx).getTransformedCenter());
		}
		
		fb.clear(back);
		world.renderScene(fb);
		world.draw(fb);
		fb.display();
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(gl, w, h);

		//World
		world = new World();
		world.setAmbientLight(100, 100, 100);

		// Create a texture out of the icon...:-)
		Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(getResources().getDrawable(R.drawable.energy_components)), 256, 256));
		TextureManager.getInstance().addTexture("texture", texture);

		//Center sphere
		cube = Primitives.getSphere(50, 10);
		cube.calcTextureWrapSpherical();
		cube.setTexture("texture");
		cube.setSpecularLighting(true);
		cube.strip();
		cube.build();
		
		//Orbits
		float x,y,z;
		float rad = 0;
		int radius = 15;
		int nrOfLightSpheres = 5;
		lightSpheres = new ArrayList<Object3D>();
		lights = new ArrayList<Light>();
		for (int i = 0; i < nrOfLightSpheres; i++) {
			x = (float)(radius*Math.cos(rad));
			y = -radius + 2*radius*(i/(nrOfLightSpheres*1f));
			z = (float)(radius*Math.sin(rad));
			
			//Init and setting up
			Object3D lightSphere = Primitives.getSphere(1);
			lightSphere.translate(x, y, z);
			lightSphere.strip();
			lightSphere.build();
			lightSphere.setRotationPivot(new SimpleVector(-x, -y, -z));
			
			//Light
			Light light = new Light(world);
			light.setPosition(new SimpleVector(x*2, y*2, z*2));
			light.setIntensity((float)(255*Math.random()), (float)(255*Math.random()), (float)(255*Math.random()));
			lights.add(light);
			
			//Adding
			world.addObject(lightSphere);
			lightSpheres.add(lightSphere);
			
			//Rad++
			rad += 2*Math.PI/(nrOfLightSpheres*1f);
		}
		
		//Light rotation
		degrees = new SimpleVector();

		//World
		world.addObject(cube);

		//Camera
		Camera cam = world.getCamera();
		cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
		cam.lookAt(cube.getTransformedCenter());

		MemoryHelper.compact();	
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

	}
}
