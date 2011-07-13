package com.tieto.ec.gui.animation3D;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;
import com.tieto.R;
import com.tieto.ec.activities.LogIn;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView.Renderer;

public class AnimationRenderer implements Renderer{

	//Android class
	private Activity activity;
	
	//3D
	private float rotationSpeed;
	private FrameBuffer frameBuffer;
	private World world;
	private RGBColor backgroundColor;
	private ArrayList<Object3D> lightSpheres;
	private ArrayList<Light> lights;
	private Object3D mainSphere;
	private Camera cam;
	
	public AnimationRenderer(Activity activity){
		//Init
		this.activity = activity;
		backgroundColor = new RGBColor(0, 0, 0);
		rotationSpeed = (float) (Math.PI/50f);
		lightSpheres = new ArrayList<Object3D>();
		lights = new ArrayList<Light>();
		
		//Adding texture
		if(!TextureManager.getInstance().containsTexture("texture")){
			Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(activity.getResources().getDrawable(R.drawable.energy_components)), 256, 256));
			TextureManager.getInstance().addTexture("texture", texture);			
		}
	}
	
	public void onDrawFrame(GL10 gl) {
		if(cam.getPosition().z < -50){
			//Main sphere
			mainSphere.rotateX(-rotationSpeed*4.2f);
			
			//Cam
			cam.moveCamera(Camera.CAMERA_MOVEIN, 10);			
		}else{
			if(mainSphere.getTransparency() > 0){				
				mainSphere.setTransparency(mainSphere.getTransparency()-1);
			}else{
				activity.startActivity(new Intent(activity, LogIn.class));
			}
		}
		
		//Orbits
		for (Object3D light : lightSpheres) {
			light.rotateY(rotationSpeed*4);
		}
		
		for (Light light : lights) {
			int idx = lights.indexOf(light);
			light.setPosition(lightSpheres.get(idx).getTransformedCenter());
		}
		
		frameBuffer.clear(backgroundColor);
		world.renderScene(frameBuffer);
		world.draw(frameBuffer);
		frameBuffer.display();
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (frameBuffer != null) {
			frameBuffer.dispose();
		}
		frameBuffer = new FrameBuffer(gl, w, h);

		//World
		world = new World();
		world.setAmbientLight(50,50,50);


		//Center sphere
		mainSphere = Primitives.getSphere(50, 10);
		mainSphere.calcTextureWrapSpherical();
		mainSphere.setTexture("texture");
		mainSphere.calcNormals();
		mainSphere.setLighting(Object3D.LIGHTING_ALL_ENABLED);
		mainSphere.setTransparency(80);
		mainSphere.strip();
		mainSphere.build();
		
		world.addObject(mainSphere);
		
		//Orbits
		float x,y,z;
		float radians = 0;
		int radius = 13;
		int nrOfLightSpheres = 8;
		for (int i = 0; i < nrOfLightSpheres; i++) {
			x = (float)(radius*Math.cos(radians));
			y = -radius + 2*radius*(i/(nrOfLightSpheres*1f));
			z = (float)(radius*Math.sin(radians));
			
			//Init and setting up
			Object3D lightSphere = Primitives.getSphere(0.3f);
			lightSphere.translate(x, y, z);
			lightSphere.strip();
			lightSphere.build();
			lightSphere.setRotationPivot(new SimpleVector(-x, -y, -z));
			
			//Light
			Light light = new Light(world);
			light.setPosition(new SimpleVector(x*2, y*2, z*2));
			if(i%2 == 0){
				light.setIntensity(0,0,255);
			}else{
				light.setIntensity(255,255,255);
			}
			
			lights.add(light);
			
			//Adding
			world.addObject(lightSphere);
			lightSpheres.add(lightSphere);
			
			//Rad++
			radians += 2*Math.PI/(nrOfLightSpheres*1f);
		}

		//Camera
		cam = world.getCamera();
		cam.moveCamera(Camera.CAMERA_MOVEOUT, 1000);
		cam.lookAt(mainSphere.getTransformedCenter());

		MemoryHelper.compact();	
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {}
}
