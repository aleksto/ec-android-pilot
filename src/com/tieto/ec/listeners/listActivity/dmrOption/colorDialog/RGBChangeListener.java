package com.tieto.ec.listeners.listActivity.dmrOption.colorDialog;

import com.tieto.ec.gui.ColorChooserDialog;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class RGBChangeListener implements OnSeekBarChangeListener{

	private ColorChooserDialog colorChooserDialog;
	
	public RGBChangeListener(ColorChooserDialog colorChooserDialog) {
		this.colorChooserDialog = colorChooserDialog;
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		colorChooserDialog.updateColor();
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

}
