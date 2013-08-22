import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * This class displays the information about the CatalogItem passed in the 
 * constructor.
 */

@SuppressWarnings("serial")
public class BriefItemPanel extends JPanel {
	
	public BriefItemPanel(Item i) {
		this.setLayout(new BorderLayout());

		// JPanel that contains all of the textual information associated with 
		// the item.
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel title = new JLabel(i.getTitle());
		title.setAlignmentX(LEFT_ALIGNMENT);
		title.setFont(Globals.MEDIUM_TEXT_BOLD);
		
		JLabel type = new JLabel(i.getType());
		type.setAlignmentX(LEFT_ALIGNMENT);
		type.setFont(Globals.SMALL_TEXT);
		
		JLabel callNumber = new JLabel(i.getCallNumberString());
		callNumber.setAlignmentX(LEFT_ALIGNMENT);
		callNumber.setFont(Globals.SMALL_TEXT);
		
		JLabel status = new JLabel(i.getStatus());
		status.setAlignmentX(LEFT_ALIGNMENT);
		status.setFont(Globals.SMALL_TEXT);
		
		infoPanel.add(title);
		infoPanel.add(type);
		infoPanel.add(callNumber);
		infoPanel.add(status);
		
		JTextArea summary = new JTextArea(i.getSummary(), 5, 20);
		summary.setFont(Globals.SMALL_TEXT);
		summary.setLineWrap(true);
		summary.setEditable(false);
		summary.setOpaque(false);
		summary.setWrapStyleWord(true);
		summary.setAlignmentX(LEFT_ALIGNMENT);
		
		infoPanel.add(summary);

		// JPanel which displays image associated with this item
		JPanel imagePanel = new JPanel();
		
		// Resize image and place in an image icon
//		ImageIcon icon = new ImageIcon(img.getScaledInstance(
//		img.getWidth() / 4, img.getHeight() / 4, Image.SCALE_SMOOTH));
		ImageIcon icon = i.getImgIcon();
		imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		imagePanel.add(new JLabel(icon));
		
		this.add(imagePanel, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.CENTER);

	}
}
