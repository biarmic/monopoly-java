package windows;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import gamestuff.Card;
import gamestuff.OfferItem;
import gamestuff.Property;

public class OfferWindow extends JPanel {
	private TradeWindow tradeWindow;
	private static Font font;
	public OfferWindow(int x, int y, TradeWindow tradeWindow, Font font) {
		super(null);
		setBackground(new Color(0,0,0,0));
		this.tradeWindow = tradeWindow;
		OfferWindow.font = font;
		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				int scroll = -event.getWheelRotation()*50;
				Component[] comps = getComponents();
				if(comps.length>1 && ((scroll<0 && comps[comps.length-1].getY()+comps[comps.length-1].getHeight()>750) || (scroll>0 && comps[0].getY()<36))) {
					for(Component comp : comps) {
						comp.setLocation(comp.getX(),comp.getY()+scroll);
						tradeWindow.repaint();
					}
				}
			}
		});
		setBounds(x,y,370,750);
	}
	public void add(Property property) {
		Component[] comps = getComponents();
		property.setLocation(44,36+(comps.length>0 ? comps[comps.length-1].getY()+comps[comps.length-1].getHeight() : 0));
		super.add(property);
	}
	public void add(Card card) {
		Component[] comps = getComponents();
		OfferItem item = new OfferItem(44,36+(comps.length>0 ? comps[comps.length-1].getY()+comps[comps.length-1].getHeight() : 0),card,font);
		super.add(item);
	}
	public void add(int cash) {
		Component[] comps = getComponents();
		OfferItem item = new OfferItem(44,36+(comps.length>0 ? comps[comps.length-1].getY()+comps[comps.length-1].getHeight() : 0),cash,font);
		add(item);
	}
	public void remove(Component comp) {
		super.remove(comp);
		updateList();
	}
	public void remove(boolean isCash) {
		for(Component comp : getComponents()) {
			if(isCash && comp instanceof OfferItem && ((OfferItem) comp).getAmount()>0) {
				remove(comp);
				break;
			}else if(!isCash && comp instanceof OfferItem && ((OfferItem) comp).getCard()!=null) {
				remove(comp);
				break;
			}
		}
		updateList();
	}
	private void updateList() {
		Component[] list = getComponents();
		for(int i = 0; i < list.length; i++)
			list[i].setLocation(44,36+540*i);
		tradeWindow.repaint();
	}
}
