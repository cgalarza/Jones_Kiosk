import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MovieKiosk extends JFrame implements ActionListener {

	// Name of cards in cardLayouts.
	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";
	
	// Canned searches for genre buttons.
	private final String ANIMATION_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Animated%20films%20and%20branch:branchbajmz";
	private final String HORROR_SEARCH = "http://libcat.dartmouth.edu/search/X?s:horror%20films%20and%20branch:branchbajmz";
	private final String WESTERN_SEARCH = "http://libcat.dartmouth.edu/search/X?s:western*%20and%20branch:branchbajmz";
	private final String MUSICAL_SEARCH = "http://libcat.dartmouth.edu/search/X?s:musical%20films%20and%20branch:branchbajmz";
	private final String MYSTERY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:mystery%20films%20and%20branch:branchbajmz";
	private final String SHORT_FILMS_SEARCH = "http://libcat.dartmouth.edu/search/X?s:short%20films%20and%20branch:branchbajmz";
	private final String SCIENCE_FICTION_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Science%20Fiction%20and%20branch:branchbajmz";
	private final String ADVENTURE_SEARCH = "http://libcat.dartmouth.edu/search/X?s:adventure%20films%20and%20branch:branchbajmz";
	private final String DRAMA_SEARCH = "http://libcat.dartmouth.edu/search/X?s:drama%20films%20and%20branch:branchbajmz";
	private final String CHILDREN_SEARCH = "http://libcat.dartmouth.edu/search~S1?/Xs:children%27s%20films%20and%20branch:branchbajmz";
	private final String TELEVISION_PROGRAM_SEARCH = "http://libcat.dartmouth.edu/search~S4?/dTelevision+programs./dtelevision+programs/-3%2C-1%2C0%2CB/exact&FF=dtelevision+programs&1%2C165%2C";
	private final String DOCUMENTARY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:documentary%20films%20and%20branch:branchbajmz";
	private final String COMEDY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:comedy%20films%20and%20branch:branchbajmz";
	private final String WAR_SEARCH = "http://libcat.dartmouth.edu/search/X?s:war%20films%20and%20branch:branchbajmz";
	private final String CRIME_SEARCH = "http://libcat.dartmouth.edu/search/X?s:crime%20films%20and%20branch:branchbajmz";
	
	private JPanel bottom, searchMovies, genreButtons, promoPanel;
	private JButton searchButton, logo, adventure, horror, western, animation, musical, mystery, 
		shortFilms, scienceFiction, drama, children, televisionPrograms, documentary, comedy, war, crime;
	private JTextField searchBar;

	/**
	 * Constructor of MovieKiosk which creates all the buttons at the top of the
	 * screen, the search bar and a PromotionPanel over a SearchPanel 
	 * (contained within a CardLayout).
	 */
	private MovieKiosk() {
		fullScreenSetUp();

		// Jones Media Center logo in the upper left hand corner of the screen. 
		// When the logo is clicked the user is directed back to the display of
		// promotional movies.
		logo = new JButton(new ImageIcon(getClass().getResource("Jones_Logo.png")));
		logo.setBorder(BorderFactory.createEmptyBorder());
		logo.setContentAreaFilled(false);
		logo.addActionListener(this);

		// Panel which contains 15 different genre buttons.
		genreButtons = createGenrePanel();

		JPanel topButtons = new JPanel();
		topButtons.add(logo);
		topButtons.add(genreButtons);

		// JPanel which contains text input field and a search button.
		JPanel search = new JPanel();
		searchBar = new JTextField("Search the Jones Media Collection");
		searchBar.setPreferredSize(new Dimension(600, 50));
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 25));
		searchBar.addActionListener(this);

		searchButton = new JButton(new ImageIcon(getClass().getResource("Magnifying_Glass.png")));
		searchButton.setBorder(BorderFactory.createEmptyBorder());
		searchButton.setContentAreaFilled(false);
		searchButton.addActionListener(this);

		search.add(searchBar);
		search.add(searchButton);

		// JPanel which contains JMC logo, genre buttons and the search 
		// bar panel.
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(topButtons);
		top.add(search);

		// Creating a PromotionalPanel that displays movies pre-selected and recorded
		// in a text file.
		promoPanel = new PromotionPanel();
		
		// Empty SearchPanel (hidden when the kiosk is first opened.)
		searchMovies = new SearchPanel("");

		bottom = new JPanel();
		bottom.setLayout(new CardLayout());
		bottom.add(promoPanel, NEW_MOVIES);
		bottom.add(searchMovies, SEARCH_MOVIES);

		this.getContentPane().add(top, BorderLayout.NORTH);
		this.getContentPane().add(bottom, BorderLayout.CENTER);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
	}

	/**
	 * Sets up the application so that it will be in full screen mode at all
	 * times. By default it selects the first screen found.
	 * 
	 * Heavily used the example code from the Java fullscreen tutorial and API.
	 */
	private void fullScreenSetUp() {

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		boolean isFullScreen = devices[0].isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);

		if (isFullScreen) {
			// Full-screen mode
			devices[0].setFullScreenWindow(this);
			validate();
		} else {
			// Windowed mode
			pack();
			setVisible(true);
		}
	}
	
	/**
	 * Creates the genre panel with 15 different buttons. Each buttons represents a different genre 
	 * button and once clicked displays a canned search in a SearchPanel. 
	 * 
	 * Each buttons was placed by absolute positioning (no other layout allowed the flexibility needed).
	 * 
	 * @return JPanel with all genre buttons
	 */
	
	private JPanel createGenrePanel(){
		
		JPanel genreButtons = new JPanel();
		genreButtons.setLayout(null);
		genreButtons.setPreferredSize(new Dimension(1223, 201));
		
		animation = new JButton(new ImageIcon(getClass().getResource("Genre_Animation.png")));
		animation.addActionListener(this);
		animation.setBorder(BorderFactory.createEmptyBorder());
		animation.setContentAreaFilled(false);
		animation.setBounds(0, 0, 258, 65);
		genreButtons.add(animation);
		
		horror = new JButton(new ImageIcon(getClass().getResource("Genre_Horror.png")));
		horror.addActionListener(this);
		horror.setBorder(BorderFactory.createEmptyBorder());
		horror.setContentAreaFilled(false);
		horror.setBounds(263, 0, 185, 65);
		genreButtons.add(horror);
		
		western = new JButton(new ImageIcon(getClass().getResource("Genre_Western.png")));
		western.addActionListener(this);
		western.setBorder(BorderFactory.createEmptyBorder());
		western.setContentAreaFilled(false);
		western.setBounds(453, 0, 212, 65);
		genreButtons.add(western);
		
		musical = new JButton(new ImageIcon(getClass().getResource("Genre_Musical.png")));
		musical.addActionListener(this);
		musical.setBorder(BorderFactory.createEmptyBorder());
		musical.setContentAreaFilled(false);
		musical.setBounds(670, 0, 202, 65);
		genreButtons.add(musical);
		
		mystery = new JButton(new ImageIcon(getClass().getResource("Genre_Mystery.png")));
		mystery.addActionListener(this);
		mystery.setBorder(BorderFactory.createEmptyBorder());
		mystery.setContentAreaFilled(false);
		mystery.setBounds(877, 0, 201, 65);
		genreButtons.add(mystery);
		
		shortFilms = new JButton(new ImageIcon(getClass().getResource("Genre_Short_Films.png")));
		shortFilms.addActionListener(this);
		shortFilms.setBorder(BorderFactory.createEmptyBorder());
		shortFilms.setContentAreaFilled(false);
		shortFilms.setBounds(1083, 0, 141, 122);
		genreButtons.add(shortFilms);
		
		scienceFiction = new JButton(new ImageIcon(getClass().getResource("Genre_Science_Fiction.png")));
		scienceFiction.addActionListener(this);
		scienceFiction.setBorder(BorderFactory.createEmptyBorder());
		scienceFiction.setContentAreaFilled(false);
		scienceFiction.setBounds(0, 70, 173, 129);
		genreButtons.add(scienceFiction);
		
		adventure = new JButton(new ImageIcon(getClass().getResource("Genre_Adventure.png")));
		adventure.addActionListener(this);
		adventure.setBorder(BorderFactory.createEmptyBorder());
		adventure.setContentAreaFilled(false);
		adventure.setBounds(178, 70, 251, 64);
		genreButtons.add(adventure);
		
		drama = new JButton(new ImageIcon(getClass().getResource("Genre_Drama.png")));
		drama.addActionListener(this);
		drama.setBorder(BorderFactory.createEmptyBorder());
		drama.setContentAreaFilled(false);
		drama.setBounds(434, 70, 173, 64);
		genreButtons.add(drama);
		
		children = new JButton(new ImageIcon(getClass().getResource("Genre_Children's.png")));
		children.addActionListener(this);
		children.setBorder(BorderFactory.createEmptyBorder());
		children.setContentAreaFilled(false);
		children.setBounds(612, 70, 240, 64);
		genreButtons.add(children);
		
		televisionPrograms = new JButton(new ImageIcon(getClass().getResource("Genre_Television_Programs.png")));
		televisionPrograms.addActionListener(this);
		televisionPrograms.setBorder(BorderFactory.createEmptyBorder());
		televisionPrograms.setContentAreaFilled(false);
		televisionPrograms.setBounds(857, 70, 221, 129);
		genreButtons.add(televisionPrograms);
		
		documentary = new JButton(new ImageIcon(getClass().getResource("Genre_Documentary.png")));
		documentary.addActionListener(this);
		documentary.setBorder(BorderFactory.createEmptyBorder());
		documentary.setContentAreaFilled(false);
		documentary.setBounds(178, 138, 323, 62);
		genreButtons.add(documentary);
		
		comedy = new JButton(new ImageIcon(getClass().getResource("Genre_Comedy.png")));
		comedy.addActionListener(this);
		comedy.setBorder(BorderFactory.createEmptyBorder());
		comedy.setContentAreaFilled(false);
		comedy.setBounds(506, 138, 218, 62);
		genreButtons.add(comedy);
		
		war = new JButton(new ImageIcon(getClass().getResource("Genre_War.png")));
		war.addActionListener(this);
		war.setBorder(BorderFactory.createEmptyBorder());
		war.setContentAreaFilled(false);
		war.setBounds(729, 138, 121, 62);
		genreButtons.add(war);
		
		crime = new JButton(new ImageIcon(getClass().getResource("Genre_Crime.png")));
		crime.addActionListener(this);
		crime.setBorder(BorderFactory.createEmptyBorder());
		crime.setContentAreaFilled(false);
		crime.setBounds(1083, 127, 141, 72 );
		genreButtons.add(crime);
		
		return genreButtons;
	}

	/**
	 * Method that waits for the search button, the logo or the genre buttons to 
	 * be clicked. When one of these buttons are pressed the corresponding card 
	 * in the CardLayout is shown.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout cards = (CardLayout) (bottom.getLayout()); // gets cardLayout needed to switch cards

		if (e.getSource() == logo) {
			// JMC Logo is clicked.
			cards.show(bottom, NEW_MOVIES);
			// Make sure the promotional panel is flipped to the right card
			CardLayout cl = (CardLayout) (promoPanel.getLayout());
			cl.show(promoPanel, "Promo_Movies");
			searchBar.setText("Search the Jones Media Collection");
		} else if (e.getSource() == searchButton || e.getSource() == searchBar) {
			// While typing enter was hit our the search buttons was clicked.
			searchMovies = new SearchPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			cards.show(bottom, SEARCH_MOVIES);
		} else { 
			// Assume its a genre button and based on which button was clicked 
			// create the correct searchPanel.
			try {
				
				if (e.getSource() == animation)
					searchMovies = new SearchPanel(new URL(ANIMATION_SEARCH));
				else if (e.getSource() == horror)
					searchMovies = new SearchPanel(new URL(HORROR_SEARCH));
				else if (e.getSource() == western)
					searchMovies = new SearchPanel(new URL(WESTERN_SEARCH));
				else if (e.getSource() == musical)
					searchMovies = new SearchPanel(new URL(MUSICAL_SEARCH));
				else if (e.getSource() == mystery)
					searchMovies = new SearchPanel(new URL(MYSTERY_SEARCH));
				else if (e.getSource() == shortFilms)
					searchMovies = new SearchPanel(new URL(SHORT_FILMS_SEARCH));
				else if (e.getSource() == scienceFiction)
					searchMovies = new SearchPanel(new URL(SCIENCE_FICTION_SEARCH));
				else if (e.getSource() == adventure)
					searchMovies = new SearchPanel(new URL(ADVENTURE_SEARCH));
				else if (e.getSource() == drama)
					searchMovies = new SearchPanel(new URL(DRAMA_SEARCH));
				else if (e.getSource() == children)
					searchMovies = new SearchPanel(new URL(CHILDREN_SEARCH));
				else if (e.getSource() == televisionPrograms)
					searchMovies = new SearchPanel(new URL(TELEVISION_PROGRAM_SEARCH));
				else if (e.getSource() == documentary)
					searchMovies = new SearchPanel(new URL(DOCUMENTARY_SEARCH));
				else if (e.getSource() == comedy)
					searchMovies = new SearchPanel(new URL(COMEDY_SEARCH));
				else if (e.getSource() == war)
					searchMovies = new SearchPanel(new URL(WAR_SEARCH));
				else if (e.getSource() == crime)
					searchMovies = new SearchPanel(new URL(CRIME_SEARCH));
				
				// Once searchPanel object has been created add to SEARCH_MOVIES card
				bottom.add(searchMovies, SEARCH_MOVIES);
				cards.show(bottom, SEARCH_MOVIES);
				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		} 	
	}

	/**
	 * Main method that creates the MovieKiosk object.
	 */
	public static void main(String[] args) {
		new MovieKiosk();
	}

}