package windows;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import managers.AudioManager;

public class MessageWindow extends Window {
	private static Font font;
	public MessageWindow(String message, Screen screen, AudioManager audioManager, Font font) {
		super("message");
		MessageWindow.font = new Font(font.getName(),Font.BOLD,35);
		JLabel label = new JLabel("<html><div align='center'>"+message+"</div></html>",JLabel.CENTER);
		Button ok = new Button("ok-2",188,237);
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.removeFromGlassPane(MessageWindow.this);
			}
		});
		label.setBounds(8,8,559,224);
		label.setFont(MessageWindow.font);
		label.setForeground(Color.white);
		add(label,new Integer(1));
		add(ok,new Integer(1));
	}
}
