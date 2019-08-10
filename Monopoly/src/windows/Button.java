package windows;

import java.awt.Cursor;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Button extends JLabel {
	private ImageIcon other = null;
	public Button(String name, int x, int y) {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/buttons/"+name+".png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBounds(x,y,getIcon().getIconWidth(),getIcon().getIconHeight());
	}
	public Button(String name, String other, int x, int y) {
		this(name,x,y);
		try {
			this.other = new ImageIcon(ImageIO.read(getClass().getResource("/buttons/"+other+".png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void switchImages() {
		ImageIcon keep = other;
		other = (ImageIcon) getIcon();
		setIcon(keep);
	}
}
