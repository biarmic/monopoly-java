package windows;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import managers.AudioManager;
import gamestuff.Property;

public class PropertyWindow extends Window {
	private static Font font;
	private ArrayList<Property> propertyList;
	public PropertyWindow(ArrayList<Property> propertyList, Screen screen, AudioManager audioManager, Font font) {
		super("property");
		PropertyWindow.font = new Font(font.getName(),Font.BOLD,60);
		this.propertyList = propertyList;
		JLabel frame = new JLabel();
		Button close = new Button("close",1095,6);
		try {
			frame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/property-frame.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setBounds(0,0,1152,864);
		add(frame,new Integer(2));
		add(close,new Integer(3));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				for(Property prop : propertyList)
					remove(prop);
				screen.removeFromGlassPane(PropertyWindow.this);
			}
		});
		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				int scroll = -event.getWheelRotation()*50;
				if(propertyList.size()>3 && ((scroll<0 && propertyList.get(propertyList.size()-1).getY()>=340) || (scroll>0 && propertyList.get(0).getY()<54)))
					for(Property prop : propertyList)
						prop.setLocation(prop.getX(),prop.getY()+scroll);
			}
		});
		updateList();
	}
	public void updateList() {
		for(Component comp : getComponentsInLayer(1))
			remove(comp);
		if(propertyList.size()==0) {
			JLabel noProperty = new JLabel("<html><div align=\"center\">THE PLAYER DOES NOT HAVE A PROPERTY</div></html>");
			noProperty.setFont(font);
			noProperty.setForeground(Color.white);
			noProperty.setBounds(76,182,1000,500);
			add(noProperty,new Integer(1));
		}
		for(int i = 0; i < propertyList.size(); i++) {
			Property prop = propertyList.get(i);
			prop.setLocation(75+(i%3)*357,54+(i/3)*541);
			add(propertyList.get(i),new Integer(1));
		}
	}
}
