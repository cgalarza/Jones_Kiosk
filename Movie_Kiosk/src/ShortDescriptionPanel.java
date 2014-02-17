import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class ShortDescriptionPanel extends JPanel {
	
	private Item item;
	JTextPane summary;
	
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
		
		StringBuilder info = new StringBuilder("<html><body><font face=\"Corbel\"><font size=\"6\"><strong>");
		info.append(item.getTitle());
		info.append("</strong></font><font size=\"5\"><br/>");
		
		ArrayList<String> types = i.getType();
		ArrayList<String> callNum = i.getCallNumberString();
		ArrayList<String> status = i.getStatus();
				
		// In case there are multiple entries for type, call number and status they 
		// are all retrieved and added to the infoPanel.
		for (int a = 0; a < types.size(); a++){
			info.append(types.get(a));
			info.append(" ");
			info.append(callNum.get(a));
			info.append(", ");
			info.append(status.get(a));
			info.append("<br/>");
		}
		
		info.append(item.getSummary());
		info.append("</font></font></font></body></html>");
		
		summary = new JTextPane();
		summary.setContentType("text/html");
		summary.setText(info.toString());
		summary.setCaretPosition(0);
		summary.setEditable(false);
		summary.setOpaque(false);
		
		// JPanel which displays image associated with this item
		JPanel imagePanel = new JPanel();
		imagePanel.add(new JLabel(i.getImgIcon()));
		imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		imagePanel.setOpaque(false);
		
		this.setOpaque(true);				
		this.setBorder(BorderFactory.createLineBorder(Color.white, 10));

		if (!types.contains("On Reserve at Jones Media") && status.contains("AVAILABLE"))
			this.setBackground(new Color (204, 255, 204));
		else 
			this.setBackground(new Color (255, 229, 204));
		
		this.add(imagePanel, BorderLayout.WEST);
		this.add(summary, BorderLayout.CENTER);

	}
	
	/**
	 * Returns the item object associated with this object.
	 * 
	 * @returns Item instance variable.
	 */
	public Item getItem(){
		return this.item;
	}
	
	public JTextPane getSummaryTextArea(){
		return this.summary;
	}
}
