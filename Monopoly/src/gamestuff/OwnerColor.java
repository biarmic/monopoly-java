package gamestuff;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OwnerColor extends JLabel {
	private int remainder;
	private int x;
	private int y;
	public OwnerColor(int remainder, int x, int y) {
		this.remainder = remainder;
		this.x = x;
		this.y = y;
		setBounds(x,y,94,54);
	}
	public void changeIcon(PlayerColor color) {
		if(color!=null) {
			try {
				setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/ownercolor/"+color.toString()+"-owner-"+remainder+".png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			setIcon(null);
		}
	}
}
