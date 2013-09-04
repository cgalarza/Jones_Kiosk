import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class VerboseItemPanel extends JPanel{

	public VerboseItemPanel(Item i){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		
		JLabel cover = new JLabel(i.getMedImgIcon());
		//cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(cover, BorderLayout.WEST);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
		
		ArrayList<ArrayList<String>> data = i.getAllWebpageInformation();
		for (ArrayList<String> row : data){
			
			JLabel label;
			if (row.get(0).equals(""))
				label = new JLabel(" ");
			else
				label = new JLabel(row.get(0));
			label.setAlignmentX(Component.RIGHT_ALIGNMENT);
			label.setFont(MyFont.SMALL_TEXT);
			labelPanel.add(label);
			
			
			JLabel info = new JLabel(row.get(1));
			info.setAlignmentX(Component.LEFT_ALIGNMENT);
			info.setFont(MyFont.SMALL_TEXT);
			informationPanel.add(info);
			
//			JTextArea info = new JTextArea(row.get(1), 20, 20);
//			info.setFont(Globals.SMALL_TEXT);
//			info.setLineWrap(true);
//			info.setEditable(false);
//			info.setOpaque(false);
//			info.setWrapStyleWord(true);
//			info.setAlignmentX(LEFT_ALIGNMENT);
//			informationPanel.add(info);

		}
		
//		JLabel name = new JLabel(i.getTitle());
//		name.setFont(Globals.SMALL_TEXT);
//		name.setAlignmentX(Component.CENTER_ALIGNMENT);
//	
//		JLabel accessionNum = new JLabel(Integer.toString(i.getJonesAccesionNum()));
//		accessionNum.setFont(Globals.SMALL_TEXT);
//		accessionNum.setAlignmentX(Component.CENTER_ALIGNMENT);

		JScrollPane scrollPane = new JScrollPane(informationPanel,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.add(labelPanel);
		//scrollPane.add(informationPanel);
		this.add(scrollPane, BorderLayout.CENTER);
		
		
	}
}
