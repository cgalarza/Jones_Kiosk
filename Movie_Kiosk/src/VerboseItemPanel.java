import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class VerboseItemPanel extends JPanel{

	/* 
	 * Constructor of VerboseItemPanel. Create a panel that displays all 
	 * the information the library catalog has about this item. 
	 * 
	 * Displays a medium picture along with all the information given in the
	 * catalog.
	 * 
	 * @param i item object that contains all the information about the catalog item
	 * 
	 */
	public VerboseItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		
		JPanel coverAndNumber = new JPanel();
		coverAndNumber.setLayout(new BoxLayout(coverAndNumber, BoxLayout.Y_AXIS));
		
		// Display image and information under the picture
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(CENTER_ALIGNMENT);
		cover.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		coverAndNumber.add(cover);
		
		String[] types = i.getType();
		String[] callNum = i.getCallNumberString();
		String[] status = i.getStatus();
		
		// Retrieving status information and placing it in a ScrollPane.
		String statusInfo = "<html><body><center><font size=\"5\"><font face=\"Corbel\">";
		for (int a = 0; a < types.length; a++){
			statusInfo = statusInfo.concat(types[a] + " " + callNum[a] + ", " + status[a] + "<br>");
		}
		statusInfo.concat("</font></font></center></body></html>");
		
		JTextPane statusText = new JTextPane();
		statusText.setContentType("text/html");
		statusText.setText(statusInfo);
		statusText.setCaretPosition(0);
		statusText.setEditable(false);
		statusText.setOpaque(false);
		
		JScrollPane scrollStatus = new JScrollPane(statusText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollStatus.setPreferredSize(new Dimension(100, 100));
		scrollStatus.setBorder(BorderFactory.createEmptyBorder());
		coverAndNumber.add(scrollStatus);
		this.add(coverAndNumber, BorderLayout.WEST);
		
		// Display information to the left of the screen and placing it in a ScrollPane.
		String info = "<html><body><font size=\"5\"><font face=\"Corbel\">";

		ArrayList<ArrayList<String>> data = i.getAllWebpageInformation();
		for (ArrayList<String> row : data){
			
			if (row.get(0).equals(""))
				info = info.concat("&nbsp;&nbsp;&nbsp;&nbsp;"); // if there is no label insert a tab
			else
				info = info.concat("<b>" + row.get(0) + ": </b>");
			
			info = info.concat(row.get(1) +"<br /><br />");	
		}
		
		info = info.concat("</font></font></body></html>");
						
		JTextPane text = new JTextPane();
		text.setContentType("text/html");
		text.setText(info);
		text.setCaretPosition(0);
		text.setEditable(false);
		text.setOpaque(false);
	
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(800, 600));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
}
