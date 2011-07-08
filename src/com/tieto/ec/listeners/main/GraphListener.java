package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.WellPeriod;
import com.tieto.ec.activities.WellSingle;
import com.tieto.ec.gui.Graph;
import com.tieto.ec.logic.DateFormat;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class GraphListener implements OnTouchListener {

	private WellPeriod wellPeriod;
	private Graph graph;
	private Toast toast;
	private TextView text;
	private String objectID, username, password, namespace, url;

	public GraphListener(WellPeriod wellPeriod, String objectID, String username, String password, String namespace, String url){
		//Init
		this.username = username;
		this.password = password;
		this.objectID = objectID;
		this.namespace = namespace;
		this.url = url;
		this.wellPeriod = wellPeriod;
		toast = new Toast(wellPeriod);
		text = new TextView(wellPeriod);

		//text
		text.setTextColor(Color.BLACK);

		//Toast
		toast.setView(text);
		toast.setGravity(Gravity.CENTER, 1, 1);
	}

	public boolean onTouch(View v, MotionEvent event) {
		graph = (Graph) v;
		try{
			if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
				toast.show();
				text.setText(DateFormat.parse(graph.getGraphWidget().getXVal(event.getX())));
			}else if(event.getAction() == MotionEvent.ACTION_UP){
				toast.cancel();
				start();
			}
		}catch(java.lang.IllegalArgumentException e){
			
		}
		return true;
	}

	private void start() {
		Intent intent = new Intent(wellPeriod, WellSingle.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("namespace", namespace);
		intent.putExtra("url", url);
		intent.putExtra("daytime", text.getText());
		intent.putExtra("objectID", objectID);
		wellPeriod.startActivity(intent);
	}

}
