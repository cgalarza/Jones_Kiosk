import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NewMoviesPanel extends JPanel {

	private final String FILE_EXTENTION = ".jpg";
	private final String PATH_DISPLAY_MOVIES = "/Users/carlagalarza/Pictures/Display_Movies/";
	private final String DISPLAY_MOVIES_FILE = "/Users/carlagalarza/Desktop/display_movies";
	
	private ArrayList<String> movieNames;
	private ArrayList<String> movieAccessionNum;
	private ArrayList<BufferedImage> movieImages;
	
	/*
	 * Constructor
	 */
	public NewMoviesPanel() {
		
		this.setLayout(new BorderLayout());
		
		File f = new File(DISPLAY_MOVIES_FILE);
		movieImages = new ArrayList<BufferedImage>();
		movieNames = new ArrayList<String>();
		movieAccessionNum = new ArrayList<String>();
		
		String title = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String currentLine;
			title = br.readLine();
			br.readLine(); // Empty line after the title
			while ((currentLine = br.readLine()) != null){
				String[] tokens = currentLine.split(", ");
				File movieFile = new File(PATH_DISPLAY_MOVIES + tokens[2] + FILE_EXTENTION);
				movieImages.add(ImageIO.read(movieFile));
				movieNames.add(tokens[0]);
				movieAccessionNum.add(tokens[2]);
			}
			br.close();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JLabel displayTitle = new JLabel(title);
		displayTitle.setFont(Globals.LARGE_TEXT_BOLD);
		displayTitle.setAlignmentX(CENTER_ALIGNMENT);
		displayTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel rotatingMoviesPanel = new RotatingMoviesPanel(movieNames, movieAccessionNum, movieImages);
	
		this.add(displayTitle, BorderLayout.NORTH);
		this.add(rotatingMoviesPanel, BorderLayout.CENTER);
		
		//CardLayout cl = (CardLayout)(rotatingMoviesPanel.getLayout());
	    //cl.show(rotatingMoviesPanel, "CARD_1");
	}
	
}
