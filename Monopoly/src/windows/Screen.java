package windows;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import managers.AudioManager;

public class Screen extends JFrame {
	private AudioManager audioManager = new AudioManager(this);
	private MainMenu mainMenu = new MainMenu(this,audioManager);
	private PauseWindow pauseWindow = new PauseWindow(this,audioManager);
	private GameScene gameScene;
	private String activeScene = "main-menu";
	private GridBagConstraints gbc = new GridBagConstraints();
	public Screen() {
		super("Monopoly");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainMenu);
		setVisible(true);
		audioManager.playSound("main-menu");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"),"pause");
		getRootPane().getActionMap().put("pause", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(activeScene.equals("game-scene"))
					if(pauseWindow.getParent()!=getGlassPane())
						addToGlassPane(pauseWindow);
					else
						removeFromGlassPane(pauseWindow);
				else
					System.exit(0);
			}
		});
		for(int i = 0; i < 10; i++) {
			final int a = i;
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD"+i),""+i);
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(""+i),""+i);
			getRootPane().getActionMap().put(""+i, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JPanel glass = ((JPanel) getGlassPane());
					if(glass.getComponentCount()>0 && glass.getComponents()[0] instanceof CashWindow)
						((CashWindow) glass.getComponents()[0]).addNumber(a);
				}
			});
			
		}
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_BACK_SPACE),"remove");
		getRootPane().getActionMap().put("remove", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JPanel glass = ((JPanel) getGlassPane());
				if(glass.getComponentCount()>0 && glass.getComponents()[0] instanceof CashWindow)
					((CashWindow) glass.getComponents()[0]).removeNumber();
			}
		});
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"enter");
		getRootPane().getActionMap().put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JPanel glass = ((JPanel) getGlassPane());
				if(glass.getComponentCount()>0 && glass.getComponents()[0] instanceof CashWindow) {
					audioManager.playSound("click");
					CashWindow cash = (CashWindow) glass.getComponents()[0];
					removeFromGlassPane(cash);
					cash.enter();
				}
			}
		});
		/**
		 * 		When the GlassPane of this frame is visible,
		 * 		events of the MouseListeners below the GlassPane
		 * 		will be consumed. For instance, player will not be
		 * 		able to click game buttons when the game is paused.
		 */
		getGlassPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(event.getSource() instanceof JLabel && ((JLabel) event.getSource()).getParent()!=getGlassPane())
					event.consume();
			}
		});
		((JPanel) getGlassPane()).setLayout(new GridBagLayout());
	}
	public String getActiveScene() {
		return activeScene;
	}
	public void openGameScene() {
		closeActiveScene();
		gameScene = new GameScene(this,audioManager);
		add(gameScene);
		gameScene.showPieceWindow();
		validate();
		activeScene = "game-scene";
		audioManager.changeBackgroundSound();
	}
	public void openMainMenu() {
		closeActiveScene();
		getGlassPane().setVisible(false);
		add(mainMenu);
		validate();
		activeScene = "main-menu";
		audioManager.changeBackgroundSound();
	}
	private void closeActiveScene() {
		if(activeScene.equals("main-menu"))
			remove(mainMenu);
		else if(activeScene.equals("game-scene")) {
			for(Component comp : ((JPanel) getGlassPane()).getComponents())
				((JPanel) getGlassPane()).remove(comp);
			remove(gameScene);
		}
	}
	/**
	 * 		This method adds the JLayeredPane to the GlassPane of this
	 * 		frame. Before doing it, checks if there is a component on
	 * 		the GlassPane which should be removed from the GlassPane and
	 * 		added to the GameScene.
	 */
	public void addToGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) getGlassPane());
		for(Component comp : glass.getComponents()) {
			glass.remove(comp);
			gameScene.add(comp,new Integer(gameScene.highestLayer()+1));
		}
		glass.add(pane,gbc);
		glass.setVisible(true);
		gameScene.repaint();
		glass.repaint();
	}
	/**
	 * 		This method removes the JLayeredPane from the GlassPane
	 * 		of this frame. In addition, if there is any component
	 * 		which should be re-added to the GlassPane, re-adds that
	 * 		component.
	 */
	public void removeFromGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) getGlassPane());
		glass.remove(pane);
		for(Component comp : gameScene.getComponentsInLayer(gameScene.highestLayer())) {
			if(comp instanceof Window) {
				gameScene.remove(comp);
				glass.add(comp,gbc);
				break;
			}
		}
		gameScene.repaint();
		glass.repaint();
		glass.setVisible(glass.getComponents().length!=0);
	}
	public static void main(String[] args) {
		new Screen();
	}
}
