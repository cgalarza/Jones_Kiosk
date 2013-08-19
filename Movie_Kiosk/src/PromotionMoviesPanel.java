import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PromotionMoviesPanel extends JPanel implements ActionListener {

	private final String FILE_EXTENTION = ".jpg";
	private final String PATH_DISPLAY_MOVIES = "/Users/carlagalarza/Pictures/Display_Movies/";
	private final String DISPLAY_MOVIES_FILE = "/Users/carlagalarza/Desktop/display_movies";
	private JPanel navigationBar;
	private RotatingMoviesPanel rotatingMoviesPanel;
	private JButton previousResults;
	private JButton moreResults;
	
	private ArrayList<String> movieNames;
	private ArrayList<String> movieAccessionNum;
	private ArrayList<BufferedImage> movieImages;

	/*
	 * Constructor
	 */
	public PromotionMoviesPanel() {

		this.setLayout(new BorderLayout());

		File f = new File(DISPLAY_MOVIES_FILE);
		movieImages = new ArrayList<BufferedImage>();
		movieNames = new ArrayList<String>();
		movieAccessionNum = new ArrayList<String>();

		// Reading text file.
		String title = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String currentLine;
			title = br.readLine();
			br.readLine(); // Empty line after the title
			while ((currentLine = br.readLine()) != null) {
				String[] tokens = currentLine.split(", ");
				File movieFile = new File(PATH_DISPLAY_MOVIES + tokens[2]
						+ FILE_EXTENTION);
				movieImages.add(ImageIO.read(movieFile));
				movieNames.add(tokens[0]);
				movieAccessionNum.add(tokens[2]);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JLabel displayTitle = new JLabel(title);
		displayTitle.setFont(Globals.LARGE_TEXT_BOLD);
		displayTitle.setAlignmentX(CENTER_ALIGNMENT);
		displayTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		rotatingMoviesPanel = new RotatingMoviesPanel(movieNames,
				movieAccessionNum, movieImages);

		createNavigationBar();
		this.add(navigationBar, BorderLayout.SOUTH);
		this.add(displayTitle, BorderLayout.NORTH);
		this.add(rotatingMoviesPanel, BorderLayout.CENTER);
		
		CardLayout cl = (CardLayout)(rotatingMoviesPanel.getLayout());
		cl.show(rotatingMoviesPanel, "CARD_1");
	}

	public void createNavigationBar() {
		// Creating navigation bar at the bottom of the screen
		navigationBar = new JPanel();
		navigationBar.setLayout(new BoxLayout(navigationBar, BoxLayout.X_AXIS));
		navigationBar
				.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		// Creating the next button with image.
		previousResults = new JButton(new ImageIcon(getClass().getResource(
				"Previous.png")));
		previousResults.setBorder(BorderFactory.createEmptyBorder());
		previousResults.setContentAreaFilled(false);
		previousResults.addActionListener(this);

		// Label which displays the number of pages.
		// numberOfPages = new JLabel("Page 1 of " + totalPages);

		// Creating the previous button with image.
		moreResults = new JButton(new ImageIcon(getClass().getResource(
				"More.png")));
		moreResults.setBorder(BorderFactory.createEmptyBorder());
		moreResults.setContentAreaFilled(false);
		moreResults.addActionListener(this);

		navigationBar.add(previousResults);
		navigationBar.add(Box.createHorizontalGlue());
		// navigationBar.add(numberOfPages);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(moreResults);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == moreResults){
			rotatingMoviesPanel.switchToNextPage();
			
			//numberOfPages.setText("Page " + currentPage + " of " + totalPages);

		}

		else if (e.getSource() == previousResults) {
			rotatingMoviesPanel.switchToPreviousPage();
			
			//numberOfPages.setText("Page " + currentPage + " of " + totalPages);

		}

	}

}
