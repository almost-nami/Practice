package kr.ac.green;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class CMouseListener<W extends Container> extends MouseAdapter {
	private W owner;
	
	public CMouseListener(W owner) {
		this.owner = owner;
	}
	@Override
	public void mouseEntered(MouseEvent me) {
		setBorder(me, true);
	}
	@Override
	public void mouseExited(MouseEvent me) {
		setBorder(me, false);
	}
	@Override
	public void mousePressed(MouseEvent me) {
		showPopup(me);
	}
	@Override
	public void mouseReleased(MouseEvent me) {
		if(me.getButton() == MouseEvent.BUTTON3) {
			showPopup(me);
		} else {
			Component c = (Component)me.getSource();
			if(owner instanceof Viewer) {
				((Viewer)owner).clickImg(c);
			} else {
				Dimension size = c.getSize();
				((ImgDlg)owner).clickImg(size.width/2 - me.getX());
			}
		}
	}
	private void showPopup(MouseEvent me) {
		if(me.isPopupTrigger()) {
			if(owner instanceof ImgDlg) {
				((ImgDlg)owner).showPopup(me);
			}
		}
	}
	private void setBorder(MouseEvent me, boolean flag) {
		JComponent c = (JComponent)me.getComponent();
		if(flag) {
			c.setBorder(new LineBorder(Color.GREEN, 2, true));
		} else {
			c.setBorder(null);
		}
	}
}
