package gamestuff;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Building extends JLabel {
	public Building(String type, int x, int y) {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/"+type+".png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(x,y,23,23);
	}
}

