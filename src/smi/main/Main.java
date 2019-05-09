package smi.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
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
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;

import common.DragAndDrop;
import common.FileManager;

public class Main extends JFrame implements DropTargetListener {
	DefaultListModel model = new DefaultListModel();
	
	DragAndDrop drag;
	JScrollPane mv_scroll, sub_scroll;
	JPanel p_east;
	JPanel p_west;
	JPanel p_north;
	JPanel p_northL;
	JPanel p_northR;
	JPanel p_south;
	JButton bt_convert;
	JButton reset;
	JLabel la_mv;
	JLabel la_sub;
	JLabel how_to_using;
	JLabel la_admin;

	Vector<File> mv_files = new Vector<File>();
	JList mv_Jlist = new JList(mv_files);

	Vector<File> sub_files = new Vector<File>();
	JList sub_Jlist = new JList(sub_files);

	JFileChooser chooser;

	File subFile;
	File mvFile;
	
	public Main() {
		this.setTitle("������ �ڸ� �̸� ��ȯ��");
		how_to_using =new JLabel("�巡�׾� ����ؼ� ����ϼ���");
		la_admin =new JLabel("������ : msKim");
		la_admin.setPreferredSize(new Dimension(300,40));
		drag=new DragAndDrop();
		reset=new JButton("ȭ�� ����");
		p_northL = new JPanel();
		p_northR = new JPanel();
		la_mv = new JLabel("������ ����");
		la_mv.setPreferredSize(new Dimension(320, 40));
		la_sub = new JLabel("�ڸ� ����");
		p_north = new JPanel();
		p_south = new JPanel();
		p_west = new JPanel();
		bt_convert = new JButton("��ȯ");
		//bt_add = new JButton("������ ���");
		//bt_sub = new JButton("�ڸ� ���");
		mv_scroll = new JScrollPane(mv_Jlist);
		sub_scroll = new JScrollPane(sub_Jlist);
		p_north.setPreferredSize(new Dimension(650, 40));
		

		
		//p_north.add(bt_add);
		p_north.add(la_mv);
		p_north.add(la_sub);
		//p_north.add(bt_sub);
		p_south.add(sub_scroll);
		p_south.add(la_admin);
		p_south.add(how_to_using);
		p_south.add(bt_convert);
		p_south.add(reset);
		p_south.setLayout(new FlowLayout(FlowLayout.TRAILING));
		

		mv_Jlist.setModel(drag.model); //DragAndDrop Ŭ�������� ������ Drag�̺�Ʈ
		mv_Jlist.setDragEnabled(true); //Main Drag �̺�Ʈ�� ������ �ڵ�
		new DropTarget(mv_Jlist,drag);
		
		sub_Jlist.setModel(this.model);
		sub_Jlist.setDragEnabled(true);
		new DropTarget(this, this);
		
		this.setLayout(new BorderLayout());
		mv_scroll.setPreferredSize(new Dimension(320, 250));
		sub_scroll.setPreferredSize(new Dimension(330, 260));
		add(p_north, BorderLayout.NORTH);
		add(mv_scroll);
		add(sub_scroll, BorderLayout.EAST);
		add(p_south, BorderLayout.SOUTH);

//		bt_add.addActionListener((e) -> {
//			addMovie();
//		});

//		bt_sub.addActionListener((e) -> {
//			addSub();
//		});

		bt_convert.addActionListener((e) -> {
			bt_convert();
		});
		
		reset.addActionListener((e)->{
			reset();
		});
		
		JOptionPane.showMessageDialog(this, "���ʰ� �������� ������ �濵ȭ �� ���ƾ� �ϰ� �����ٿ� �־���մϴ�!");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(660, 520);
		setVisible(true);
	}
	
	//���� ���� ���
	public void addMovie() {
		chooser = new JFileChooser("C:/Users/alstn/Desktop");
		chooser.setMultiSelectionEnabled(true);
		
		// ----------Ȯ���� �����
		chooser.setFileFilter(new FileNameExtensionFilter("mkv,mp4,avi", "mkv", "mp4", "avi"));
//		chooser.showOpenDialog(bt_add);
		if (chooser.getSelectedFiles() != null) {
			for (File file : chooser.getSelectedFiles()) {
				mv_files.add(file);
			}
		}
		mv_Jlist.updateUI();
	}

//--------------------------------------------------------------------
	//�ڸ� ���� ���
	public void addSub() {

		chooser = new JFileChooser("C:/Users/alstn/Desktop");
		chooser.setMultiSelectionEnabled(true);
		// ----------Ȯ���� �����
		chooser.setFileFilter(new FileNameExtensionFilter("smi,srt,ass", "smi", "srt", "ass"));
//		chooser.showOpenDialog(bt_sub);
		if (chooser.getSelectedFiles() != null) {
			for (File file : chooser.getSelectedFiles()) {
				sub_files.add(file);
			}
			sub_Jlist.updateUI();
		}
	}
	//��ϵ� ����� �ڸ��� �̸��� ����
	public void bt_convert() {
		int movieCount = mv_Jlist.getModel().getSize();
		System.out.println(movieCount);

		for (int i = 0; i < movieCount; i++) {
			File movieFile = (File) mv_Jlist.getModel().getElementAt(i);
			File subFile = (File) sub_Jlist.getModel().getElementAt(i);

			System.out.println("��ȭ:" + movieFile.getAbsolutePath() + "�� �ڸ������� " + subFile.getName() + "�� �ٲ� ����");

			// ���� ������� ������ ����
			// ��� : ��ȭ������ ���󰣴�
			// ���ϸ� : ��ȭ���ϸ��� ���󰣴�
			// Ȯ���ڴ� : �ڸ������� ���󰣴�
			String dir = FileManager.extractDir(movieFile.getAbsolutePath());
			String prefix = FileManager.getPrefix(movieFile.getName());
			String ext = FileManager.getExtend(subFile.getName());

			System.out.println(dir + File.separator + prefix + "." + ext);
			if (mv_Jlist.getModel().getSize() != sub_Jlist.getModel().getSize()) {
				JOptionPane.showMessageDialog(this, "����� �ڸ� ������ ������ �ٸ��ϴ�");

				this.model.removeAllElements();
				drag.model.removeAllElements();
				sub_Jlist.updateUI();
				mv_Jlist.updateUI();

				break;
			} else {
				boolean result = subFile.renameTo(new File(dir + File.separator + prefix + "." + ext));
				System.out.println(i + "��° ��ȯ����� " + result);
			}
		}
		JOptionPane.showMessageDialog(this, "�۾� ����");
		this.model.removeAllElements();
		drag.model.removeAllElements();
		sub_Jlist.updateUI();
		mv_Jlist.updateUI();

	}
	//��ϵ� ���� ���ϰ� �ڸ� ������ �ʱ�ȭ �����ش�
	public void reset() {
		if(JOptionPane.showConfirmDialog(this, "ȭ�� ����")==JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(this, "ó�� �Ϸ�\n");
			this.model.removeAllElements();
			drag.model.removeAllElements();
			sub_Jlist.updateUI();
			mv_Jlist.updateUI();
		}
	}

	public static void main(String[] args) {
		new Main();

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

	//Drag and Drop �̺�Ʈ
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

	}
}