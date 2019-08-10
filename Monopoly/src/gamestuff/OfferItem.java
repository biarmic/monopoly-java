package gamestuff;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class OfferItem extends JLayeredPane {
	private static Font font;
	private int amount = 0;
	private Card card = null;
	public OfferItem(int x, int y, int amount, Font font) {
		OfferItem.font = new Font(font.getName(),Font.PLAIN,50);
		this.amount = amount;
		JLabel background = new JLabel();
		JLabel label = new JLabel(updateText(amount),JLabel.CENTER);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/offer-item-cash.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		label.setFont(OfferItem.font);
		label.setForeground(Color.white);
		background.setBounds(0,0,300,100);
		label.setBounds(81,26,200,50);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(label,new Integer(1));
		setBounds(x,y,300,100);
	}
	public OfferItem(int x, int y, Card card, Font font) {
		OfferItem.font = new Font(font.getName(),Font.PLAIN,50);
		this.card = card;
		JLabel background = new JLabel();
		JLabel label = new JLabel("JAIL CARD",JLabel.CENTER);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/offer-item-card.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		label.setFont(OfferItem.font);
		label.setForeground(Color.white);
		background.setBounds(0,0,300,100);
		label.setBounds(0,0,300,100);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(label,new Integer(1));
		setBounds(x,y,300,100);
	}
	public int getAmount() {return amount;};
	public Card getCard() {return card;};
	private String updateText(int cash) {
		String str = "" + cash;
		String res = str.substring(0,str.length()%3)+(str.length()%3==0 ? "" : ",");
		str = str.substring(str.length()%3);
		while(str.length()>0) {
			res += str.substring(0,3)+",";
			str = str.substring(3);
		}
		if(res.charAt(res.length()-1)==',')
			res = res.substring(0,res.length()-1);
		return res;
	}
}
