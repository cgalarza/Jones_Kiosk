import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class LongDescriptionPanel extends JPanel implements ActionListener{

	private JButton switchRecords;
	private String completeRecord, sumRecord;
	private JTextPane text;
	private boolean entireRecord;
	private Item i;
	private StringBuilder statusInfo;
	
	/**
	 * Constructor of LongDescriptionPanel. Create a panel that can display all the information the 
	 * library catalog has about this item. 
	 * 
	 * Displays a medium picture along with the most pertinent information listed in the catalog. 
	 * The user can choose to display the entire record by clicking a button. 
	 * 
	 * @param i item object that contains all the information about the catalog item
	 */
	public LongDescriptionPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		this.i = i;
				
		ArrayList<String> types = i.getType();
		ArrayList<String> callNum = i.getCallNumberString();
		ArrayList<String> status = i.getStatus();
		
		// Retrieving status information and placing it in a ScrollPane.
		statusInfo = new StringBuilder();
		for (int a = 0; a < types.size(); a++){
			statusInfo.append(types.get(a));
			statusInfo.append(" ");
			statusInfo.append(callNum.get(a));
			statusInfo.append(", ");
			statusInfo.append(status.get(a));
			if (types.size()-1 != a)
				statusInfo.append("<br/>");
		}
				
		// Display DVD cover.
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(CENTER_ALIGNMENT);
		cover.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		// Set up button that switches record.
		switchRecords = new JButton(new ImageIcon(
				getClass().getResource("resources/Display_Entire_Record.png")));
		switchRecords.setBorder(BorderFactory.createEmptyBorder());
		switchRecords.setContentAreaFilled(false);
		switchRecords.setAlignmentX(CENTER_ALIGNMENT);
		switchRecords.addActionListener(this);
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(cover);
		right.add(switchRecords);
		
		this.add(right, BorderLayout.WEST);
		
		// Set up the text field that contains all the information about the item.
		text = new JTextPane();
		text.setContentType("text/html");
		text.setText(summarizedRecord());
		text.setCaretPosition(0);
		text.setEditable(false);
		text.setOpaque(false);
		
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(1100, 775));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Creates a string that contains the text of the entire catalog record with HTML formatting.
	 * 
	 * @return HTML formatted string that contains all the item information
	 */
	private String entireRecord(){
		
		entireRecord = true;
		
		if (completeRecord == null){
		
			// Display information to the left of the screen and placing it in a ScrollPane.
			StringBuilder info = new StringBuilder("<html><body><font face=\"Corbel\">"
					+ "<font size=\"11\"><strong>");
			info.append(i.getTitle()); 
			info.append("</strong></font><font size=\"6\"><br/>");
			info.append(statusInfo);
		
			ArrayList<ArrayList<String>> data = i.getAllWebpageInformation();
			for (int i = 0; i< data.size(); i++){
			
				ArrayList<String> row = data.get(i);
			
				if (row.get(0).equals(""))
					info.append("<br />");
				else if (row.get(0).equals("Title"))
					info.append("<br/><br/><b>" + "Entire Title: </b>");
				else 
					info.append("<br /><br /><b>" + row.get(0) + ": </b>");

				info.append(row.get(1));
			}
		
			info.append("</font></font></font></body></html>");
			completeRecord = info.toString();
		}				
		
		return completeRecord;
	}
	
	/**
	 * Create a string that contains the summarized text of the catalog record with HTML
	 * formatting.
	 * 
	 * @return HTML formatted string that contains the summarized item information
	 */
	private String summarizedRecord(){
		
		entireRecord = false;
		
		if (sumRecord == null){
			// Display information to the left of the screen and place it in a JScrollPane.
			StringBuilder info = new StringBuilder("<html><body>"
					+ "<font face=\"Corbel\"><font size=\"11\"><strong>");
			info.append(i.getTitle()); // Add Title.
			info.append("</strong></font><font size=\"6\"><br/>");
			info.append(statusInfo); // Add Location, CallNumber and Status information.
			if (!i.getSummary().equals("")){
				info.append("<br/><br/><strong>Summary: </strong>"); // Add Summary.
				info.append(i.getSummary());
			}
			if (!i.getPerformer().equals("")){
				info.append("<br/><br/><strong>Cast: </strong>" );
				info.append (i.getPerformer()); // Add Cast/Performers.
			}
			if (!i.getLanguage().equals("")){
				info.append("<br/><br/><strong>Language: </strong>");
				info.append(i.getLanguage()); // Add Language.
			}
			if (!i.getRating().equals("")){
				info.append("<br/><br/><strong>Rating: </strong>");
				info.append(i.getRating()); // Add Rating.
			}
			info.append("</font></font></body></html>");
		
			sumRecord = info.toString();
		}
		
		return sumRecord;
	}

	/**
	 * Switches between the summarized catalog record and the entire catalog record. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Show the entire record. 
		if (e.getSource() == switchRecords && !entireRecord){
			switchRecords.setIcon(new ImageIcon(
					getClass().getResource("resources/Display_Summarized_Record.png")));
			text.setText(entireRecord());
			text.setCaretPosition(0);
		}
		// Show the summarized record.
		else if (e.getSource() == switchRecords && entireRecord){
			switchRecords.setIcon(new ImageIcon(
					getClass().getResource("resources/Display_Entire_Record.png")));
			text.setText(summarizedRecord());
			text.setCaretPosition(0);
		}	
	}
}