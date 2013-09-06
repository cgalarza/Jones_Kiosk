import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class VerboseItemPanel extends JPanel{

	public VerboseItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		
		JPanel coverAndNumber = new JPanel();
		coverAndNumber.setLayout(new BoxLayout(coverAndNumber, BoxLayout.Y_AXIS));
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setAlignmentX(CENTER_ALIGNMENT);
		cover.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		coverAndNumber.add(cover);
		
		String[] types = i.getType();
		String[] callNum = i.getCallNumberString();
		String[] status = i.getStatus();
		
		for (int a = 0; a < types.length; a++){

			JLabel itemInfo = new JLabel(types[a] + " " + callNum[a] + ", " + status[a]);
			itemInfo.setAlignmentX(LEFT_ALIGNMENT);
			itemInfo.setFont(MyFont.SMALL_TEXT);
			itemInfo.setAlignmentX(CENTER_ALIGNMENT);
		
			coverAndNumber.add(itemInfo);
		}
		
		this.add(coverAndNumber, BorderLayout.WEST);
		
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
				
		System.out.println(info);
		
		JTextPane text = new JTextPane();
		text.setContentType("text/html");
		text.setText(info);
		text.setCaretPosition(0);
		text.setEditable(false);
		text.setOpaque(false);
	
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setVerticalScrollBarPolicy(
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(900, 600));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
}
