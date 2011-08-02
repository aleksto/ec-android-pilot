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
import com.tieto.ec.activities.Login;
import com.tieto.ec.activities.Welcome;
import com.tieto.ec.logic.FileManager;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView.Renderer;

public class AnimationRenderer implements Renderer{

	//Android class
	private Activity activity;
	
	//3D
	private final int TOTAL_NUMBER_OF_SPHERES = 6;
	private final int RADIUS = 13;
	private final float ROTATION_SPEED = (float) (Math.PI/50f);
	private FrameBuffer frameBuffer;
	private World world;
	private RGBColor backgroundColor;
	private ArrayList<Object3D> lightSpheres;
	private ArrayList<Light> lights;
	private Object3D mainSphere;
	private Camera cam;
	private Light camLight;
	
	/**
	 * Creates a 3D renderer for the {@link Welcome} class
	 * @param activity {@link Activity} needed for Android framework actions
	 */
	public AnimationRenderer(Activity activity){
		//Init
		this.activity = activity;
		backgroundColor = new RGBColor(0, 0, 0);
		lightSpheres = new ArrayList<Object3D>();
		lights = new ArrayList<Light>();
		
		//Adding texture
		if(!TextureManager.getInstance().containsTexture("texture")){
			Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(activity.getResources().getDrawable(R.drawable.energy_components)), 256, 256));
			TextureManager.getInstance().addTexture("texture", texture);
		}
	}

	/**
	 * Method called when a new frame is rendering in the 3D animation
	 * This method will rotate the sphere, move the camera against the sphere, and rotating the lights around the sphere
	 */
	public void onDrawFrame(GL10 gl) {
		if(cam.getPosition().z < -50){
			//Main sphere
			mainSphere.rotateX(-ROTATION_SPEED*4.05f);
			
			//Cam
			cam.moveCamera(Camera.CAMERA_MOVEIN, 1f);		
			camLight.setPosition(cam.getPosition());
		}else{
			if(mainSphere.getTransparency() > 0){				
				mainSphere.setTransparency(mainSphere.getTransparency()-1);
			}else{
				activity.startActivity(new Intent(activity, Login.class));
				FileManager.writePath(activity, "Welcome Activity.3D Seen", "true");
			}
		}
		
		//Light orbits
		for (Object3D light : lightSpheres) {
			light.rotateY(ROTATION_SPEED*4);
		}
		
		for (Light light : lights) {
			int idx = lights.indexOf(light);
			light.setPosition(lightSpheres.get(idx).getTransformedCenter());
		}
		
		//miscellaneous 3D
		frameBuffer.clear(backgroundColor);
		world.renderScene(frameBuffer);
		world.draw(frameBuffer);
		frameBuffer.display();
	}

	/**
	 * Method called when a the screen is changed, etc started up or rotated
	 * It builds up the 3D space, sphere and light orbits
	 */
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (frameBuffer != null) {
			frameBuffer.dispose();
		}

		//Init
		frameBuffer = new FrameBuffer(gl, w, h);
		world = new World();

		//Center sphere
		mainSphere = Primitives.getSphere(50, 10);
		mainSphere.calcTextureWrapSpherical();
		mainSphere.setTexture("texture");
		mainSphere.calcNormals();
		mainSphere.setLighting(Object3D.LIGHTING_ALL_ENABLED);
		mainSphere.setTransparency(80);
		mainSphere.strip();
		mainSphere.build();
		
		//World
//		world.setAmbientLight(50,50,50);
//		world.setFogging(World.FOGGING_ENABLED);
		world.addObject(mainSphere);
		
		//Orbits
		float radians = 0;
		for (int i = 0; i < TOTAL_NUMBER_OF_SPHERES; i++) {
			radians = createOrbit(radians, i);
		}
		
		//Floor
		Object3D floor = Primitives.getBox(100, 0.01f);
		floor.calcNormals();
		floor.setLighting(Object3D.LIGHTING_ALL_ENABLED);
		floor.setSpecularLighting(true);
		floor.translate(0, 11, 0);
		floor.rotateY((float) (Math.PI/4f));
		floor.strip();
		floor.build();
		world.addObject(floor);

		//Camera
		cam = world.getCamera();
		cam.moveCamera(Camera.CAMERA_MOVEOUT, 100);
		cam.lookAt(mainSphere.getTransformedCenter());
		
		//Cam light
		camLight = new Light(world);
		camLight.setIntensity(100, 100, 100);
		camLight.setPosition(cam.getPosition());

		MemoryHelper.compact();	
	}

	/**
	 * Used in onSurfaceChanged() to generate a orbit
	 * @param nrOfLightSpheres Total number of spheres
	 * @param i orbit nr i
	 * @return new radian for the next orbit
	 */
	private float createOrbit(float radians, int i) {
		float x, y, z;
		x = (float)(RADIUS*Math.cos(radians));
		y = -RADIUS + 2*RADIUS*(i/(TOTAL_NUMBER_OF_SPHERES*1f));
		z = (float)(RADIUS*Math.sin(radians));
		
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
			light.setIntensity(100,100,100);
//			Color.rgb(180, 201, 220)
		}else{
			light.setIntensity(0,0,0);
		}
		
		lights.add(light);
		
		//Adding
		world.addObject(lightSphere);
		lightSpheres.add(lightSphere);
		
		//Rad++
		radians += 2*Math.PI/(TOTAL_NUMBER_OF_SPHERES*1f);
		return radians;
	}

	/**
	 * Not used
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {}
}
