package windows;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import gamestuff.Player;
import managers.AudioManager;

public class PlayerWindow extends JLayeredPane {
	private Player player;
	private JLabel cashLabel = new JLabel("1,500");
	private PropertyWindow propertyWindow;
	public PlayerWindow(String color, int x, int y, Font font, Screen screen, AudioManager audioManager, Player player) {
		this.player = player;
		player.setPlayerWindow(this);
		JLabel background = new JLabel();
		Button propertiesButton = new Button("properties",150,85);
		propertyWindow = new PropertyWindow(player.getPropertyList(),screen,audioManager,font);
		cashLabel.setFont(new Font(font.getName(), Font.PLAIN, 40));
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/"+color+"-player.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,384,162);
		cashLabel.setBounds(160,28,200,40);
		cashLabel.setForeground(Color.white);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(cashLabel,new Integer(1));
		add(propertiesButton,new Integer(1));
		propertiesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				propertyWindow.updateList();
				screen.addToGlassPane(propertyWindow);
			}
		});
		setBounds(x,y,384,162);
	}
	public PropertyWindow getPropertyWindow() {
		return propertyWindow;
	}
	public void updateCash() {
		String str = "" + player.getCash();
		String res = str.substring(0,str.length()%3)+(str.length()%3==0 ? "" : ",");
		str = str.substring(str.length()%3);
		while(str.length()>0) {
			res += str.substring(0,3)+",";
			str = str.substring(3);
		}
		if(res.charAt(res.length()-1)==',')
			res = res.substring(0,res.length()-1);
		cashLabel.setText(res);
	}
}
