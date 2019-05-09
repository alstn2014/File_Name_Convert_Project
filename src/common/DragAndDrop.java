package common;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DragAndDrop extends JList implements DropTargetListener {

	public DefaultListModel model = new DefaultListModel();

	public DragAndDrop() {
		setModel(this.model);
		setDragEnabled(true);
		new DropTarget(this, this);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent e) {
		Transferable tr = e.getTransferable();

		if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			e.acceptDrop(3);
			try {
				List FileList = (List) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (int i = 0; i < FileList.size(); i++) {
					this.model.addElement((File) FileList.get(i));
				}
			} catch (UnsupportedFlavorException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {
			System.err.println("DataFlavor.javaFileListFlavor is not supported, rejected");
			e.rejectDrop();
		}

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

}
