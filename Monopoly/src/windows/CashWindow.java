package windows;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import gamestuff.Player;
import managers.AudioManager;

public class CashWindow extends Window {
	private static Screen screen;
	private static AudioManager audioManager;
	private TradeWindow tradeWindow;
	private Font font;
	private JLabel label = new JLabel("0",JLabel.RIGHT);
	private int cash = 0;
	private Player player;
	private boolean isHuman;
	public CashWindow(Player player, Screen screen, AudioManager audioManager, TradeWindow tradeWindow, Font font) {
		super("cash");
		CashWindow.screen = screen;
		CashWindow.audioManager = audioManager;
		this.tradeWindow = tradeWindow;
		this.player = player;
		this.isHuman = player.isHuman();
		this.font = font;
		Button ok = new Button("ok",186,190);
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.removeFromGlassPane(CashWindow.this);
				enter();
			}
		});
		label.setFont(new Font(font.getName(),Font.PLAIN,110));
		label.setForeground(Color.white);
		label.setBounds(175,64,450,110);
		add(label,new Integer(1));
		add(ok,new Integer(1));
	}
	public void addNumber(int number) {
		if(Math.log10(cash*10+number)<6) {
			cash = cash*10+number;
			label.setText(""+cash);
			updateText();
		}
	}
	public void removeNumber() {
		cash /= 10;
		label.setText(""+cash);
		updateText();
	}
	private void updateText() {
		if(label.getText().length()>1 && label.getText().charAt(0)=='0')
			label.setText(label.getText().substring(1));
		String str = "" + label.getText();
		String res = str.substring(0,str.length()%3)+(str.length()%3==0 ? "" : ",");
		str = str.substring(str.length()%3);
		while(str.length()>0) {
			res += str.substring(0,3)+",";
			str = str.substring(3);
		}
		if(res.charAt(res.length()-1)==',')
			res = res.substring(0,res.length()-1);
		label.setText(res);
	}
	public void enter() {
		if(cash>0 && player.getCash()>=cash)
			tradeWindow.addToOfferWindow(cash,isHuman);
		else if(cash==0) {
			tradeWindow.untick(isHuman);
			screen.addToGlassPane(new MessageWindow("YOU CANNOT ENTER 0 AS THE AMOUNT OF CASH",screen,audioManager,font));
		}else if(player.getCash()<cash) {
			tradeWindow.untick(isHuman);
			screen.addToGlassPane(new MessageWindow("YOU CANNOT ENTER AN AMOUNT MORE THAN THE PLAYER HAS",screen,audioManager,font));
		}
	}
}
