import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PromotionPanel extends JPanel implements ActionListener, MouseListener{

	private final String DISPLAY_MOVIES_FILE = System.getProperty("user.home") 
			+ "/Desktop/Promotional_Movies.txt";
	private final int SPEED = 3000;
	
	// Name of cards in CardLayout.
	private final String PROMO_CARD = "Promo_Movies";
	private final String VERBOSE_CARD = "Verbose_Description";
	
	private JButton next, previous, back;
	private Timer timer;
	private JPanel rotatingPanel, centerPanel;
	
	// Contains all Item objects that are to be displayed.
	private ArrayList<Item> promotionalMovies; 
	private int current; // Counter that represents the first movie to be displayed
	private int total; // Total number of Promotional Movies displayed

	/**
	 * Constructor for PromotionPanel. Reads input from file and displays movies listed in file. 
	 * This class is also responsible for the arrows that correspond to the movement of the movie 
	 * carousel. 
	 */
	public PromotionPanel() {

		promotionalMovies = new ArrayList<Item>();

		// Reading text file.
		File f = new File(DISPLAY_MOVIES_FILE);
		String title = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String currentLine;
			title = br.readLine();
			br.readLine(); // Empty line after the title.
			while ((currentLine = br.readLine()) != null) {
				String[] tokens = currentLine.split(", ");
				
				Item i = new Item(tokens[0], tokens[1]);
				// Don't add the item if it is on reserve.
				if (i.getJonesAccesionNum() == -1)
					continue;
				promotionalMovies.add(i);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			// TODO: Display error message in application
		} catch (ArrayIndexOutOfBoundsException e1){
			e1.printStackTrace();
			//TODO: Display error message in application
		}

		total = promotionalMovies.size();
		current = 0;
		rotatingPanel = createRotatingMoviesPanel();

		JLabel displayTitle = new JLabel(title);
		displayTitle.setFont(MyFont.LARGE_TEXT_BOLD);
		displayTitle.setAlignmentX(CENTER_ALIGNMENT);
		displayTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		next = new JButton(new ImageIcon(getClass().getResource(
				"resources/Right_Arrow.png")));
		next.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		next.setContentAreaFilled(false);
		next.addActionListener(this);

		previous = new JButton(new ImageIcon(getClass().getResource(
				"resources/Left_Arrow.png")));
		previous.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		previous.setContentAreaFilled(false);
		previous.addActionListener(this);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0,0));
		centerPanel.add(previous, BorderLayout.WEST);
		centerPanel.add(next, BorderLayout.EAST);
		centerPanel.add(displayTitle, BorderLayout.NORTH);
		centerPanel.add(rotatingPanel, BorderLayout.CENTER);
		centerPanel.addMouseListener(this);
		
		this.setLayout(new CardLayout());
		this.add(centerPanel, PROMO_CARD);

		CardLayout cl = (CardLayout) (this.getLayout());
		cl.show(this, PROMO_CARD);
		
		// Set up timer for automatic card flipping.
		timer = new Timer(SPEED, this);
		timer.start();
	}

	/**
	 * Creates panel of 5 movies based on what the current movie index is.
	 * 
	 * @return JPanel which contains 5 movies
	 */
	private JPanel createRotatingMoviesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder());

		// Adds the five next movies to the panel.
		for (int a = 0, counter = current; a < 4; a++, counter++) {
			if (counter == total) 
				counter = 0; // Rotation restarted.
			JPanel displayItem = new DisplayMoviePanel(promotionalMovies.get(counter));
			displayItem.setBorder(BorderFactory.createEmptyBorder());
			displayItem.addMouseListener(this);
			panel.add(displayItem);
		}
		
		return panel;
	}

	/**
	 * Creates a new RotatingMoviesPanel and replaces the previous one.
	 */
	private void recreateRotatingMoviesPanel(){
		BorderLayout layout = (BorderLayout) centerPanel.getLayout();
		centerPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		
		rotatingPanel = createRotatingMoviesPanel();
		centerPanel.add(rotatingPanel, BorderLayout.CENTER);
		centerPanel.validate();

		timer.restart();
	}
	
	/**
	 * Listens for the arrow buttons and back button to be clicked or for the timer to go off.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next || e.getSource() == timer) {
			// If next is clicked or if the timer goes off, rotation is increased and the movies 
			// will shift to the left.
			if (current < total-1)
				current++;
			else
				current = 0;
			
			recreateRotatingMoviesPanel();
		} else if (e.getSource() == previous) {
			// If the previous arrow is clicked the rotation is decreased and the movies will shift 
			// to the right.
			if (current == 0)
				current = total - 1;
			else
				current--;
		
			recreateRotatingMoviesPanel();

		} else if (e.getSource() == back){
			// If a movie is clicked a VerboseItemPanel is displayed with a back button. Once the 
			// back button is clicked the panel reverts back to the "Promo_Movies" card.
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, PROMO_CARD);
		}
	}

	/**
	 * Listens for an object of DisplayItemPanel to be clicked. If an item is clicked the 
	 * VerboseItemPanel corresponding to that item is shown.
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().getClass() == DisplayMoviePanel.class){
			// If there is a mouse click and it was on a DisplayItemPanel the verbosePanel for that 
			// item is displayed.
			DisplayMoviePanel panelClicked = (DisplayMoviePanel) e.getSource();
			
			Item i = panelClicked.getItem();
			i.reloadInformation();
			
			JPanel verboseMovieDisplay = new LongDescriptionPanel(i);
			back = new JButton(new ImageIcon(getClass().getResource("resources/Back_Arrow.png")));
			back.setBorder(BorderFactory.createEmptyBorder());
			back.setContentAreaFilled(false);
			back.addActionListener(this);

			JPanel verbosePanel = new JPanel();
			verbosePanel.add(back);
			verbosePanel.add(verboseMovieDisplay);
			
			this.add(verbosePanel, VERBOSE_CARD);
			
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, VERBOSE_CARD);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
