package windows;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import gamestuff.Property;

public class TickBox extends JLayeredPane {
	private static Font font;
	private JLabel tick = new JLabel();
	private boolean isCash;
	public TickBox(int x, int y, Property property, Font font) {
		this(x,y,property.getName(),font);
	}
	public TickBox(int x, int y, String str, Font font) {
		TickBox.font = new Font(font.getName(),Font.PLAIN,27);
		isCash = str.equals("CASH");
		JLabel background = new JLabel();
		JLabel name = new JLabel("<html><div align=\"center\">"+str+"</div></html>",JLabel.CENTER);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/buttons/tick-box.png"))));
			tick.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/buttons/tick.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		name.setFont(TickBox.font);
		name.setForeground(Color.white);
		background.setBounds(0,0,350,100);
		name.setBounds(100,17,233,65);
		tick.setBounds(17,17,65,65);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(name,new Integer(1));
		setBounds(x,y,350,100);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(tick.getParent()==TickBox.this) {
					remove(tick);
					repaint();
				}else
					add(tick,new Integer(1));
			}
		});
	}
	public boolean isCash() {
		return isCash;
	}
	public void untick() {
		if(tick.getParent()==this) {
			remove(tick);
			repaint();
		}
	}
}
