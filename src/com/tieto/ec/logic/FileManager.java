package com.tieto.ec.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.tieto.ec.enums.OptionTitle;

import android.app.Activity;
import android.content.Context;

public class FileManager {
	
	public static void writePath(Context context, String path, String text) {
		try {
			byte[] write = text.getBytes();
			FileOutputStream ut = context.openFileOutput(path, Activity.MODE_PRIVATE);
			ut.write(write);
			ut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readPath(Context context, String path) throws IOException{
		FileInputStream inn = context.openFileInput(path);
		ArrayList<Byte> tallene = new ArrayList<Byte>();
		byte ch;
		while((ch = (byte) inn.read()) != -1){
			tallene.add(ch);
		}

		byte[] x = new byte[tallene.size()];
		int tempTeller = 0;
		for(Byte z: tallene){
			x[tempTeller++] = z;
		}

		String a = new String(x);
		inn.close();
		return a;
	}
}
