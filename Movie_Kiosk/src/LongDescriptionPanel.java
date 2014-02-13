import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class LongDescriptionPanel extends JPanel implements ActionListener{

	private JButton switchRecords;
	private JTextPane text;
	private boolean entireRecord;
	private Item i;
	private String statusInfo;
	
	/**
	 * Constructor of VerboseItemPanel. Create a panel that displays all 
	 * the information the library catalog has about this item. 
	 * 
	 * Displays a medium picture along with all the information given in the
	 * catalog.
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
		StringBuilder s = new StringBuilder();
		for (int a = 0; a < types.size(); a++)
			s = s.append(types.get(a) + " " + callNum.get(a) + ", " + status.get(a) + "<br>");
		
		statusInfo = s.toString();
		
		// Display DVD cover
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(CENTER_ALIGNMENT);
		cover.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		switchRecords = new JButton("Display entire record");
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
		scrollPane.setPreferredSize(new Dimension(1000, 800));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

		this.add(scrollPane, BorderLayout.CENTER);
	
	}
	
	private String entireRecord(){
		
		entireRecord = true;
		
		// Display information to the left of the screen and placing it in a ScrollPane.
		StringBuilder info = new StringBuilder ("<html><body><font size=\"6\"><font face=\"Corbel\">");

		ArrayList<ArrayList<String>> data = i.getAllWebpageInformation();
		for (ArrayList<String> row : data){
			
			if (row.get(0).equals(""))
				info.append("&nbsp;&nbsp;&nbsp;&nbsp;"); // if there is no label insert a tab
			else 
				info.append("<b>" + row.get(0) + ": </b>");

			info.append(row.get(1));
			info.append("<br /><br />");
			
			if (row.get(0).equals("Description")){
				info.append("<strong>Accession Number & Availability: </strong>");
				info.append(statusInfo);
				info.append("<br />");
				
			}
		}
		
		info.append("</font></font></font></body></html>");
						
		return info.toString();
	}
	
	private String summarizedRecord(){
		
		entireRecord = false;
		
		// Display information to the left of the screen and placing it in a ScrollPane.
		StringBuilder info = new StringBuilder("<html><body><font face=\"Corbel\"><font size=\"11\"><strong>");
		info.append(i.getTitle()); 
		info.append("</strong></font><font size=\"6\"><br/>");
		info.append(statusInfo);
		info.append("<br/><strong>Summary: </strong>");
		info.append(i.getSummary());
		info.append("<br/><br/><strong>Performer(s): </strong>" );
		info.append (i.getPerformer());
		info.append("<br/><br/><strong>Language: </strong>");
		info.append(i.getLanguage());
		info.append("</font></font></body></html>");
								
		return info.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == switchRecords && !entireRecord){
			switchRecords.setText("Display Summarized Record");
			text.setText(entireRecord());
			text.setCaretPosition(0);
		}
		
		else if (e.getSource() == switchRecords && entireRecord){
			switchRecords.setText("Display Entire Record");
			text.setText(summarizedRecord());
			text.setCaretPosition(0);
		}
		
	}
	
}
