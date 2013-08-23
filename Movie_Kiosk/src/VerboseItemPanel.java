import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class VerboseItemPanel extends JPanel{

	public VerboseItemPanel(Item i){
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
