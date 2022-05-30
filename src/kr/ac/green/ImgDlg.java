package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class ImgDlg extends JDialog implements ActionListener {
	public static final int NEXT = -1;
	public static final int PREV = 1;
	public final static Dimension PIC_SIZE = new Dimension(500,500);
	private Viewer owner;
	private JLabel lbl;
	private int order;
	private JLabel lblStatus;
	private JPopupMenu pMenu;
	private JMenuItem miBack;
	private JMenuItem miForward;
	
	public ImgDlg(Viewer owner, int order) {
		super(owner, "Do u see?", true);
		this.owner = owner;
		this.order = order;
		init();
		setDisplay();
		addListeners();
		showDlg();
	}
	private void init() {
		lblStatus = new JLabel(getStatus(), JLabel.CENTER);
		lblStatus.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		lbl = new JLabel(Viewer.getImageIcon(owner.getSrcPath(order), PIC_SIZE));
		pMenu = new JPopupMenu();
		miBack = new JMenuItem("뒤로");
		miForward = new JMenuItem("앞으로");
		
		pMenu.add(miBack);
		pMenu.add(miForward);
	}
	private String getStatus() {
		return (order + 1) + "/" + owner.getImageCount();
	}
	private void setDisplay() {
		add(lblStatus, BorderLayout.NORTH);
		add(lbl, BorderLayout.CENTER);
	}
	private void addListeners() {
		miBack.addActionListener(this);
		miForward.addActionListener(this);
		lbl.addMouseListener(new CMouseListener<ImgDlg>(this));
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				owner.setVisible(true);
				dispose();
			}
		});
	}
	public void showPopup(MouseEvent me) {
		pMenu.show(me.getComponent(), me.getX(), me.getY());
	}
	private void showDlg() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setLocation(200,200);
		setVisible(true);
	}
	public void clickImg(int direction) {
		if(direction < 0) {
			order = (order == owner.getImageCount()-1) ? 0 : order+1;
		} else {
			order = (order == 0) ? owner.getImageCount()-1 : order-1;
		}
		ImageIcon icon = Viewer.getImageIcon(owner.getSrcPath(order), PIC_SIZE);
		lbl.setIcon(icon);
		lblStatus.setText(getStatus());
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == miBack) {
			clickImg(PREV);
		} else {
			clickImg(NEXT);
		}
	}
}
