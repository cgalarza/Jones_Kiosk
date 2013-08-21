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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PromotionMoviesPanel extends JPanel implements ActionListener {

	private final String FILE_EXTENTION = ".jpg";
	private final String PATH_DISPLAY_MOVIES = "/Users/carlagalarza/Pictures/Display_Movies/";
	private final String DISPLAY_MOVIES_FILE = "/Users/carlagalarza/Desktop/display_movies";
	private final int SPEED = 6000;
	private RotatingMoviesPanel rotatingMoviesPanel;
	private JButton next;
	private JButton previous;
	private Timer timer;
	
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
				File movieFile = new File(PATH_DISPLAY_MOVIES + tokens[1]
						+ FILE_EXTENTION);
				movieImages.add(ImageIO.read(movieFile));
				movieNames.add(tokens[0]);
				movieAccessionNum.add(tokens[1]);
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

		next = new JButton(new ImageIcon(getClass().getResource("Right_Arrow.png")));
		next.setBorder(BorderFactory.createEmptyBorder());
		next.setContentAreaFilled(false);
		next.addActionListener(this);
		
		previous = new JButton(new ImageIcon(getClass().getResource("Left_Arrow.png")));
		previous.setBorder(BorderFactory.createEmptyBorder());
		previous.setContentAreaFilled(false);
		previous.addActionListener(this);
		
		this.add(previous, BorderLayout.WEST);
		this.add(next, BorderLayout.EAST);
		this.add(displayTitle, BorderLayout.NORTH);
		this.add(rotatingMoviesPanel, BorderLayout.CENTER);
		
		
		CardLayout cl = (CardLayout)(rotatingMoviesPanel.getLayout());
		cl.show(rotatingMoviesPanel, "CARD_1");
		
		// Set up timer for automatic card flipping
		timer = new Timer(SPEED, this);
		timer.start();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next || e.getSource() == timer){
			rotatingMoviesPanel.switchToNextPage();
			timer.restart();
			//numberOfPages.setText("Page " + currentPage + " of " + totalPages);
		}

		else if (e.getSource() == previous) {
			rotatingMoviesPanel.switchToPreviousPage();
			timer.restart();
			//numberOfPages.setText("Page " + currentPage + " of " + totalPages);
		}

	}

}
