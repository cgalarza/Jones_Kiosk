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
		infoPanel.add(title);

		
		String[] types = i.getType();
		String[] callNum = i.getCallNumberString();
		String[] status = i.getStatus();
		
		for (int a = 0; a < types.length; a++){

		
			JLabel itemInfo = new JLabel(types[a] + " " + callNum[a] + ", " + status[a]);
			itemInfo.setAlignmentX(LEFT_ALIGNMENT);
			itemInfo.setFont(Globals.SMALL_TEXT);
		
			infoPanel.add(itemInfo);
		}
		
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
