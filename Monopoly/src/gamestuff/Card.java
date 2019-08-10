package gamestuff;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Card extends JLabel {
	private int id;
	public Card(int id, String type) {
		this.id = id;
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/"+type+"/"+type+"-"+id+".png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(660,360,600,360);
	}
	public int getId() {
		return id;
	}
	public abstract void performAction(Player player);
}
