package com.tieto.ec.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

/**
 *This class is used for reading and writing {@link String} to the local drive on the phone.
 */
public class FileManager {
	
	/**
	 * Writes given text to a given path on the local drive
	 * @param context {@link Context} used for Android framework actions
	 * @param path The path for storing the file
	 * @param text The text to write
	 */
	public static void writePath(Context context, String path, String text) {
		try {
			//Replacing "/" with "###" because a path cant contain the path seperator "/"
			if(path.contains("/")){
				path = path.replaceAll("/", "###");
			}
			
			byte[] write = text.getBytes();
			FileOutputStream ut = context.openFileOutput(path, Context.MODE_PRIVATE);
			ut.write(write);
			ut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to read value ({@link String}) from a given path
	 * @param context {@link Context} used for Android framework actions
	 * @param path The path to read
	 * @return A {@link String} with the read value
	 * @throws IOException if file not found
	 */
	public static String readPath(Context context, String path) throws IOException{
		//Replacing "/" with "###" because a path cant contain the path seperator "/"
		if(path.contains("/")){
			path = path.replaceAll("/", "###");
		}
		
		FileInputStream inputStream = context.openFileInput(path);
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		byte currentByte;
		while((currentByte = (byte) inputStream.read()) != -1){
			bytes.add(currentByte);
		}

		String a = new String(copyArrayList(bytes));
		inputStream.close();
		
		
		return a;
	}
	
	/**
	 * Copies from {@link ArrayList<Byte>} to {@link Byte[]}
	 * @param source
	 * @return a new {@link Byte[]}
	 */
	private static byte[] copyArrayList(ArrayList<Byte> list){
		//Init
		byte[] byteArray = new byte[list.size()];
		
		//Copy
		int counter = 0;
		for(Byte tempByte: list){
			byteArray[counter++] = tempByte;
		}
		
		return byteArray;
	}
}
