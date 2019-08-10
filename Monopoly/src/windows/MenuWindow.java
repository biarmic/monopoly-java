package windows;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;
import managers.AudioManager;
import managers.GameManager;

public class MenuWindow extends JLayeredPane {
	private static GameManager gameManager;
	private Button endTurn = new Button("end-turn",0,0);
	private Button reroll = new Button("reroll",0,0);
	private Button bankrupt = new Button("bankrupt",0,0);
	private Button buy = new Button("buy",259,0);
	private Button getOut = new Button("get-out",259,0);
	public MenuWindow(Screen screen, AudioManager audioManager, GameManager gameManager, GameScene gameScene, Font font) {
		MenuWindow.gameManager = gameManager;
		Button manage = new Button("manage",518,0);
		Button trade = new Button("trade",777,0);
		add(endTurn,JLayeredPane.DEFAULT_LAYER);
		add(buy,JLayeredPane.DEFAULT_LAYER);
		add(manage,JLayeredPane.DEFAULT_LAYER);
		add(trade,JLayeredPane.DEFAULT_LAYER);
		setBounds(447,498,1027,83);
		endTurn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer() && gameManager.getTurnPlayer().getReroll()==0)
					gameManager.endTurn();
			}
		});
		reroll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer() && gameManager.getTurnPlayer().getReroll()>0)
					gameManager.rollDice();
			}
		});
		bankrupt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.openMainMenu();
				screen.addToGlassPane(new MessageWindow("YOU WENT BANKRUPT",screen,audioManager,font));
			}
		});
		buy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer())
					gameManager.showBuyWindow();
			}
		});
		getOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer())
					gameScene.showGetOutWindow();
			}
		});
		manage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer())
					gameManager.showManageWindow();
			}
		});
		trade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(gameManager.getHumanPlayer()==gameManager.getTurnPlayer())
					gameManager.showTradeWindow();
			}
		});
	}
	/**
	 * 		If the player has to reroll the die, endTurnButton
	 * 		will be removed (the player cannot end his/her turn
	 * 		without rerolling) and rerollButton will be added to
	 * 		the MenuWindow.
	 */
	public void addReroll() {
		if(endTurn.getParent()==this) {
			remove(endTurn);
			add(reroll,JLayeredPane.DEFAULT_LAYER);
		}
	}
	public void removeReroll() {
		if(reroll.getParent()==this) {
			remove(reroll);
			add(endTurn,JLayeredPane.DEFAULT_LAYER);
		}
	}
	public void updateMenu() {
		if(gameManager.getHumanPlayer().getCash()<0) {
			if(reroll.getParent()==this)
				remove(reroll);
			if(endTurn.getParent()==this)
				remove(endTurn);
			if(bankrupt.getParent()!=this)
				add(bankrupt,JLayeredPane.DEFAULT_LAYER);
		}else {
			if(gameManager.getHumanPlayer().getReroll()==0) {
				if(reroll.getParent()==this)
					remove(reroll);
				if(bankrupt.getParent()==this)
					remove(bankrupt);
				if(endTurn.getParent()!=this)
					add(endTurn,JLayeredPane.DEFAULT_LAYER);
			}else {
				if(endTurn.getParent()==this)
					remove(endTurn);
				if(bankrupt.getParent()==this)
					remove(bankrupt);
				if(reroll.getParent()!=this)
					add(reroll,JLayeredPane.DEFAULT_LAYER);
			}
		}
	}
	/**
	 * 		If the player is in the jail, buyButton will be removed
	 * 		(the player cannot buy a property from the bank although
	 * 		he/she can trade items) and getOutButton will be added to
	 * 		the MenuWindow.
	 */
	public void addGetOut() {
		if(buy.getParent()==this) {
			remove(buy);
			add(getOut,JLayeredPane.DEFAULT_LAYER);
		}
	}
	public void removeGetOut() {
		if(getOut.getParent()==this) {
			remove(getOut);
			add(buy,JLayeredPane.DEFAULT_LAYER);
		}
	}
}
