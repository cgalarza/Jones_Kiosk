import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class BriefItemPanel extends JPanel {
	
	private Item item;
	
	/**
	 * Constructor of BriefItemPanel. Creates a panel that displays some of 
	 * the information about this item.
	 * 
	 * Displays a small picture along with the title, type of media, call number, 
	 * status and summary.
	 * 
	 * @param i item object that contains all the information about the catalog item.
	 */
	public BriefItemPanel(Item i) {
		this.setLayout(new BorderLayout());
		this.item = i;

		// JPanel that contains all of the title, type of media, call number and 
		// status associated with the item.
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel title = new JLabel(i.getTitle());
		title.setAlignmentX(LEFT_ALIGNMENT);
		title.setFont(MyFont.MEDIUM_TEXT_BOLD);
		infoPanel.add(title);
		
		String[] types = i.getType();
		String[] callNum = i.getCallNumberString();
		String[] status = i.getStatus();
		
		// In case there are multiple entries for type, call number and status they 
		// are all retrieved and added to the infoPanel.
		for (int a = 0; a < types.length; a++){

			JLabel itemInfo = new JLabel(types[a] + " " + callNum[a] + ", " + status[a]);
			itemInfo.setAlignmentX(LEFT_ALIGNMENT);
			itemInfo.setFont(MyFont.SMALL_TEXT);
		
			infoPanel.add(itemInfo);
		}
		
		JTextArea summary = new JTextArea(i.getSummary(), 5, 20);
		summary.setFont(MyFont.SMALL_TEXT);
		summary.setLineWrap(true);
		summary.setEditable(false);
		summary.setOpaque(false);
		summary.setWrapStyleWord(true);
		summary.setAlignmentX(LEFT_ALIGNMENT);
		infoPanel.add(summary);

		// JPanel which displays image associated with this item
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(i.getImgIcon()));
		imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
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
}
