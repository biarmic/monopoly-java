package windows;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import gamestuff.Player;
import gamestuff.PlayerColor;
import gamestuff.Property;
import managers.AudioManager;
import managers.GameManager;

public class GameScene extends JLayeredPane {
	private static Screen screen;
	private static AudioManager audioManager;
	private static GameManager gameManager;
	private PieceWindow pieceWindow;
	private MenuWindow menuWindow;
	private GetOutWindow getOutWindow;
	private static Font font;
	private Player[] players = new Player[4];
	public GameScene(Screen screen, AudioManager audioManager) {
		GameScene.screen = screen;
		GameScene.audioManager = audioManager;
		pieceWindow = new PieceWindow(audioManager,this);
		JLabel background = new JLabel();
		JLabel board = new JLabel();
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/background.png"))));
			board.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/board.png"))));
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/others/font.ttf")).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(font);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		gameManager = new GameManager(screen,audioManager,this,font);
		for(int i = 0; i < 4; i++)
			players[i] = new Player(PlayerColor.values()[i],audioManager,gameManager,this,players);
		PlayerWindow redPlayerWindow = new PlayerWindow("red",0,0,font,screen,audioManager,players[0]);
		PlayerWindow bluePlayerWindow = new PlayerWindow("blue",1536,0,font,screen,audioManager,players[1]);
		PlayerWindow greenPlayerWindow = new PlayerWindow("green",0,918,font,screen,audioManager,players[2]);
		PlayerWindow yellowPlayerWindow = new PlayerWindow("yellow",1536,918,font,screen,audioManager,players[3]);
		menuWindow = new MenuWindow(screen,audioManager,gameManager,this,font);
		getOutWindow = new GetOutWindow(screen,audioManager,gameManager,font);
		background.setBounds(0,0,1920,1080);
		board.setBounds(99,39,1722,1002);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(board,new Integer(1));
		add(redPlayerWindow,new Integer(2));
		add(bluePlayerWindow,new Integer(2));
		add(greenPlayerWindow,new Integer(2));
		add(yellowPlayerWindow,new Integer(2));
		add(menuWindow,new Integer(2));
		add(players[0],new Integer(600));
		add(players[1],new Integer(601));
		add(players[2],new Integer(602));
		add(players[3],new Integer(603));
	}
	public MenuWindow getMenuWindow() {
		return menuWindow;
	}
	public void showPieceWindow() {
		screen.addToGlassPane(pieceWindow);
	}
	public void showBuyWindow(Player player, Property property) {
		BuyWindow buyWindow = new BuyWindow(player,property,screen,audioManager,gameManager,font);
		screen.addToGlassPane(buyWindow);
	}
	public void showGetOutWindow() {
		screen.addToGlassPane(getOutWindow);
	}
	public void receiveColor(PlayerColor color) {
		screen.removeFromGlassPane(pieceWindow);
		gameManager.receiveColor(players,color);
	}
}
