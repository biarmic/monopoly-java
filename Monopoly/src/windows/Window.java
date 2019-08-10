package windows;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public abstract class Window extends JLayeredPane {
	public Window(String name) {
		JLabel background = new JLabel();
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/"+name+".png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,background.getIcon().getIconWidth(),background.getIcon().getIconHeight());
		add(background,JLayeredPane.DEFAULT_LAYER);
		setBounds((1920-background.getWidth())/2,(1080-background.getHeight())/2,background.getWidth(),background.getHeight());
		setPreferredSize(getSize());
	}
}
