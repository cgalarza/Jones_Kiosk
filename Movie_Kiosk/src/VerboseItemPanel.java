import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


@SuppressWarnings("serial")
public class VerboseItemPanel extends JPanel{

	public VerboseItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		
		JLabel cover = new JLabel(i.getMedImgIcon());
		cover.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(cover, BorderLayout.WEST);
		
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
		scrollPane.setVerticalScrollBarPolicy(
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(800, 500));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
}
