package windows;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import gamestuff.Land;
import gamestuff.Player;
import gamestuff.Property;
import gamestuff.Railroad;
import gamestuff.Utility;
import managers.AudioManager;

public class ManageWindow extends Window {
	private ArrayList<Property> list;
	private int index = -1;
	private Button mortgage = new Button("mortgage",612,400);
	private Button unmortgage = new Button("unmortgage",612,400);
	public ManageWindow(Player player, Screen screen, AudioManager audioManager, Font font) {
		super("upgrade");
		this.list = player.getPropertyList();
		Button leftArrow = new Button("left-arrow",28,252);
		Button rightArrow = new Button("right-arrow",484,252);
		Button upgrade = new Button("upgrade",612,104);
		Button downgrade = new Button("downgrade",612,252);
		Button close = new Button("close",getWidth()-56,6);
		add(leftArrow,new Integer(1));
		add(rightArrow,new Integer(1));
		add(upgrade,new Integer(1));
		add(downgrade,new Integer(1));
		add(mortgage,new Integer(1));
		add(close,new Integer(1));
		leftArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(list.size()>0)
					previous();
			}
		});
		rightArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(list.size()>0)
					next();
			}
		});
		upgrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(index==-1)
					screen.addToGlassPane(new MessageWindow("THERE IS NO PROPERTY TO UPGRADE",screen,audioManager,font));
				else if(list.get(index) instanceof Land && ((Land)list.get(index)).canUpgrade())
					((Land) list.get(index)).upgrade();
				else if(list.get(index) instanceof Land)
					screen.addToGlassPane(new MessageWindow("YOU CANNOT UPGRADE THIS PROPERTY",screen,audioManager,font));
				else if(list.get(index) instanceof Railroad)
					screen.addToGlassPane(new MessageWindow("RAILROADS CANNOT BE UPGRADED",screen,audioManager,font));
				else if(list.get(index) instanceof Utility)
					screen.addToGlassPane(new MessageWindow("UTILITIES CANNOT BE UPGRADED",screen,audioManager,font));
			}
		});
		downgrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(index==-1)
					screen.addToGlassPane(new MessageWindow("THERE IS NO PROPERTY TO DOWNGRADE",screen,audioManager,font));
				else if(list.get(index) instanceof Land && ((Land)list.get(index)).canDowngrade())
					((Land) list.get(index)).downgrade();
				else if(list.get(index) instanceof Land)
					screen.addToGlassPane(new MessageWindow("YOU CANNOT DOWNGRADE THIS PROPERTY",screen,audioManager,font));
				else if(list.get(index) instanceof Railroad)
					screen.addToGlassPane(new MessageWindow("RAILROADS CANNOT BE DOWNGRADED",screen,audioManager,font));
				else if(list.get(index) instanceof Utility)
					screen.addToGlassPane(new MessageWindow("UTILITIES CANNOT BE DOWNGRADED",screen,audioManager,font));
			}
		});
		mortgage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(index==-1)
					screen.addToGlassPane(new MessageWindow("THERE IS NO PROPERTY TO MORTGAGE",screen,audioManager,font));
				else if(list.get(index) instanceof Land && ((Land) list.get(index)).canMortgage()) {
					list.get(index).getOwner().transaction(list.get(index).getPrice()/2);
					list.get(index).setMortgaged(true);
				}else if(list.get(index) instanceof Land)
					screen.addToGlassPane(new MessageWindow("YOU CANNOT MORTGAGE THIS PROPERTY",screen,audioManager,font));
				else {
					list.get(index).getOwner().transaction(list.get(index).getPrice()/2);
					list.get(index).setMortgaged(true);
				}
				updateMortgageButtons();
			}
		});
		unmortgage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(index==-1)
					screen.addToGlassPane(new MessageWindow("THERE IS NO PROPERTY TO UNMORTGAGE",screen,audioManager,font));
				else if(list.get(index).isMortgaged() && list.get(index).canUnmortgage()) {
					list.get(index).getOwner().transaction(-list.get(index).getPrice()/2);
					list.get(index).setMortgaged(false);
				}else if(list.get(index).isMortgaged())
					screen.addToGlassPane(new MessageWindow("YOU CANNOT UNMORTGAGE THIS PROPERTY",screen,audioManager,font));
				updateMortgageButtons();
			}
		});
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				for(Property prop : list)
					remove(prop);
				screen.removeFromGlassPane(ManageWindow.this);
			}
		});
	}
	public void updateList() {
		if(list.size()==0)
			index = -1;
		else {
			index = 0;
			list.get(index).setLocation(156,52);
			add(list.get(index),new Integer(1));
			updateMortgageButtons();
		}
	}
	private void next() {
		remove(list.get(index));
		index = (index+1)%list.size();
		list.get(index).setLocation(156,52);
		add(list.get(index),new Integer(1));
		updateMortgageButtons();
	}
	private void previous() {
		remove(list.get(index));
		if(index==0)
			index = list.size()-1;
		else
			index = (index-1)%list.size();
		list.get(index).setLocation(156,52);
		add(list.get(index),new Integer(1));
		updateMortgageButtons();
	}
	private void updateMortgageButtons() {
		if(index!=-1 && list.get(index).isMortgaged() && mortgage.getParent()==this) {
			remove(mortgage);
			add(unmortgage,new Integer(1));
		}else if(index!=-1 && !list.get(index).isMortgaged() && unmortgage.getParent()==this){
			remove(unmortgage);
			add(mortgage,new Integer(1));
		}
	}
}
