import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DisplayItemPanel extends JPanel{
	
	private Item i;
	
	public DisplayItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.i = i;
		
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel name = new JLabel(i.getTitle());
		name.setFont(MyFont.SMALL_TEXT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		JLabel accessionNum = new JLabel("#" + Integer.toString(i.getJonesAccesionNum()));
		accessionNum.setFont(MyFont.SMALL_TEXT);
		accessionNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(cover);
		this.add(name);
		this.add(accessionNum);

		JLabel status = new JLabel();
		if (i.getStatus().length == 1)
			status.setText(i.getStatus()[0]);
		else
			status.setText(" ");
		status.setFont(MyFont.SMALL_TEXT);
		status.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(status);

	}
	
	public Item getItem(){	
		return this.i;
	}

}
