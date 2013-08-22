import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DisplayItemPanel extends JPanel{
	
	public DisplayItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel name = new JLabel(i.getTitle());
		name.setFont(Globals.SMALL_TEXT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		JLabel accessionNum = new JLabel(i.getCallNumberString());
		accessionNum.setFont(Globals.SMALL_TEXT);
		accessionNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(cover);
		this.add(name);
		this.add(accessionNum);

		
	}

}
