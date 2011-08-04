package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnClickListener;

import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.gui.table.Cell;

public class CellListener implements OnClickListener{

	private final Cell cell;

	/**
	 * Creates a bew {@link OnClickListener} for a {@link Cell}
	 * @param cell The cell to listen to. 
	 */
	public CellListener(Cell cell) {
		this.cell = cell;
	}
	
	/**
	 * Runs when the user clicks the {@link Cell}, and it displays a {@link InfoDialog} with some information
	 */
	public void onClick(View v) {
		InfoDialog.showInfoDialog(cell.getContext(), cell.getActual().getComment() + "\n\nTarget: " +
				cell.getTarget().getValue());
	}
}
