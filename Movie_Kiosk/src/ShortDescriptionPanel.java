import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ShortDescriptionPanel extends JPanel {
	
	private Item item;
	JTextArea summary;
	
	/**
	 * Constructor of BriefItemPanel. Creates a panel that displays some of 
	 * the information about this item.
	 * 
	 * Displays a small picture along with the title, type of media, call number, 
	 * status and summary.
	 * 
	 * @param i item object that contains all the information about the catalog item.
	 */
	public ShortDescriptionPanel(Item i) {
		this.setLayout(new BorderLayout());
		this.item = i;

		// JPanel that contains all of the title, type of media, call number and 
		// status associated with the item.
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setOpaque(false);
		
		
		JLabel title = new JLabel(i.getTitle());
		title.setAlignmentX(LEFT_ALIGNMENT);
		title.setFont(MyFont.MEDIUM_TEXT_BOLD);
		infoPanel.add(title);
		
		ArrayList<String> types = i.getType();
		ArrayList<String> callNum = i.getCallNumberString();
		ArrayList<String> status = i.getStatus();
				
		// In case there are multiple entries for type, call number and status they 
		// are all retrieved and added to the infoPanel.
		for (int a = 0; a < types.size(); a++){

			JLabel itemInfo = new JLabel(types.get(a) + " " + callNum.get(a) + ", " + status.get(a));
			itemInfo.setAlignmentX(LEFT_ALIGNMENT);
			itemInfo.setFont(MyFont.SMALL_TEXT);			
			infoPanel.add(itemInfo);
		}
		
		summary = new JTextArea(i.getSummary(), 5, 20);
		summary.setFont(MyFont.SMALL_TEXT);
		summary.setLineWrap(true);
		summary.setEditable(false);
		summary.setOpaque(false);
		summary.setWrapStyleWord(true);
		summary.setAlignmentX(LEFT_ALIGNMENT);
		summary.setRequestFocusEnabled(true);
		infoPanel.add(summary);
		
		// JPanel which displays image associated with this item
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(i.getImgIcon()));
		imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		imagePanel.setOpaque(false);
		
		this.setOpaque(true);				
		this.setBorder(BorderFactory.createLineBorder(Color.white, 10));

		if (status.contains("AVAILABLE"))
			this.setBackground(new Color (204, 255, 204));
		else 
			this.setBackground(new Color (255, 229, 204));
		
		// if on course reserves blue
		// orange is one disc is out and other one isnt?
		// otherwise red?
		
		this.add(imagePanel, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.CENTER);

	}
	
	/**
	 * Returns the item object associated with this object.
	 * 
	 * @returns Item instance variable.
	 */
	public Item getItem(){
		return this.item;
	}
	
	public JTextArea getSummaryTextArea(){
		return this.summary;
	}
}
