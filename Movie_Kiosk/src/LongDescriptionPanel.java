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
	 * Constructor of LongDescriptionPanel. Create a panel that displays all the information the 
	 * library catalog has about this item. 
	 * 
	 * Displays a medium picture along with the most pertinent information listed in the catalog. 
	 * The user can choose to display the entire record by clicking a button. 
	 * 
	 * @param i item object that contains all the information about the catalog item
	 * 
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
	
	private String summarizedRecord(){
		
		entireRecord = false;
		
		if (sumRecord == null){
			// Display information to the left of the screen and placing it in a ScrollPane.
			StringBuilder info = new StringBuilder("<html><body>"
					+ "<font face=\"Corbel\"><font size=\"11\"><strong>");
			info.append(i.getTitle()); 
			info.append("</strong></font><font size=\"6\"><br/>");
			info.append(statusInfo);
			info.append("<br/><br/><strong>Summary: </strong>");
			info.append(i.getSummary());
			info.append("<br/><br/><strong>Performer(s): </strong>" );
			info.append (i.getPerformer());
			info.append("<br/><br/><strong>Language: </strong>");
			info.append(i.getLanguage());
			info.append("</font></font></body></html>");
		
			sumRecord = info.toString();
		}
		
		return sumRecord;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == switchRecords && !entireRecord){
			switchRecords.setIcon(new ImageIcon(
					getClass().getResource("resources/Display_Summarized_Record.png")));
			text.setText(entireRecord());
			text.setCaretPosition(0);
		}
		
		else if (e.getSource() == switchRecords && entireRecord){
			switchRecords.setIcon(new ImageIcon(
					getClass().getResource("resources/Display_Entire_Record.png")));
			text.setText(summarizedRecord());
			text.setCaretPosition(0);
		}
		
	}
	
}
