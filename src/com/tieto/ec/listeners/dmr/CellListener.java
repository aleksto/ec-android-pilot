package com.tieto.ec.listeners.dmr;

import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.gui.table.Cell;

import android.view.View;
import android.view.View.OnClickListener;

public class CellListener implements OnClickListener{

	private final Cell cell;

	public CellListener(Cell cell) {
		this.cell = cell;
	}

	public void onClick(View v) {
		InfoDialog.showInfoDialog(cell.getContext(), cell.getActual().getComment());
	}
}
