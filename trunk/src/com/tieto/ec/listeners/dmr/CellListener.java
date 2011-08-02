package com.tieto.ec.listeners.dmr;

import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.gui.table.Cell;

import android.view.View;
import android.view.View.OnClickListener;

public class CellListener implements OnClickListener{

	private final Cell cell;
	private String text;

	public CellListener(Cell cell) {
		this.cell = cell;
		text = "Target: " + cell.getTarget() + "\nPercent: " + Math.round((cell.getActual()/cell.getTarget())*100) + " %";
	}

	public void onClick(View v) {
		InfoDialog.showInfoDialog(cell.getContext(), text);
	}
}
