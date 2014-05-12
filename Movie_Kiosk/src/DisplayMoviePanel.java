import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DisplayMoviePanel extends JPanel{
	
	private Item i; 
	
	/**
	 * Constructor of DisplayMoviePanel. Create a panel that displays information about this item.
	 * 
	 * Displays medium picture along with the title and call number.
	 * 
	 * @param i item object that contain all the information about the catalog item
	 * 
	 */
	public DisplayMoviePanel(Item i){
		this.i = i;
		
		// Movie Image.
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		cover.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		// Title.
		JLabel name = new JLabel(i.getTitle());
		name.setFont(MyFont.SMALL_TEXT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		// Accession Number.
		JLabel accessionNum = new JLabel("#" + Integer.toString(i.getJonesAccesionNum()));
		accessionNum.setFont(MyFont.SMALL_TEXT);
		accessionNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(cover);
		this.add(name);
		this.add(accessionNum);
	}
	
	/**
	 * Returns the item object associated with this object.
	 * 
	 * @returns Item instance variable.
	 */
	public Item getItem(){	
		return this.i;
	}
}