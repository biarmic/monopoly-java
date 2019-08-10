package gamestuff;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import windows.GameScene;

public class Utility extends Property {
	public Utility(String name, int line, int tile, GameScene gameScene, Font font) {
		super(PropertyType.utility,name,150,line,tile,gameScene);
		JLabel background = new JLabel();
		JLabel nameLabel = new JLabel("<html><div align=\"center\">"+name+"</div></html>",JLabel.CENTER);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/properties/"+(name.equals("WATER WORKS")?"water-works":"electric-company")+".png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		nameLabel.setFont(new Font(font.getName(),Font.PLAIN,27));
		nameLabel.setForeground(Color.black);
		background.setBounds(0,0,300,500);
		nameLabel.setBounds(35,220,230,100);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(nameLabel,new Integer(1));
		setBounds(500,0,300,500);
	}
	@Override
	public int calculateRent(int numberSameType) {
		if(isMortgaged())
			return 0;
		return getOwner().getGameManager().getLastDieTotal()*(numberSameType==2 ? 10 : 4);
	}
}
