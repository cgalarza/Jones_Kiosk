import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MovieKiosk extends JFrame implements ActionListener {

	// Name of cards in cardLayout.
	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";

	// By default uses the first monitor found.
	private final int MONITOR = 0;
	
	// Canned searches for genre buttons.
	private final String BASE_GENRE_PATH = "http://libcat.dartmouth.edu/search/X?s:";
	private final String END_GENRE_PATH = "%20and%20branch:branchbajmz";
	private final String ANIMATION_SEARCH = BASE_GENRE_PATH + "Animated%20films" + END_GENRE_PATH;
	private final String HORROR_SEARCH = BASE_GENRE_PATH + "horror%20films" + END_GENRE_PATH;
	private final String WESTERN_SEARCH = BASE_GENRE_PATH + "western*" + END_GENRE_PATH;
	private final String MUSICAL_SEARCH = BASE_GENRE_PATH + "musical%20films" + END_GENRE_PATH;
	private final String MYSTERY_SEARCH = BASE_GENRE_PATH + "mystery%20films" + END_GENRE_PATH;
	private final String SHORT_FILMS_SEARCH = BASE_GENRE_PATH + "short%20films" + END_GENRE_PATH;
	private final String SCIENCE_FICTION_SEARCH = 
			BASE_GENRE_PATH + "Science%20Fiction" + END_GENRE_PATH;
	private final String ADVENTURE_SEARCH = BASE_GENRE_PATH + "adventure%20films" + END_GENRE_PATH;
	private final String DRAMA_SEARCH = BASE_GENRE_PATH + "drama%20films" + END_GENRE_PATH;
	private final String CHILDREN_SEARCH = 
			"http://libcat.dartmouth.edu/search~S1?/Xs:children%27s%20films" + END_GENRE_PATH;
	private final String TELEVISION_PROGRAM_SEARCH = 
			"http://libcat.dartmouth.edu/search~S4?/dTelevision+programs./dtelevision+programs/"
			+ "-3%2C-1%2C0%2CB/exact&FF=dtelevision+programs&1%2C165%2C";
	private final String DOCUMENTARY_SEARCH = 
			BASE_GENRE_PATH + "documentary%20films" + END_GENRE_PATH;
	private final String COMEDY_SEARCH = BASE_GENRE_PATH + "comedy%20films" + END_GENRE_PATH;
	private final String WAR_SEARCH = BASE_GENRE_PATH + "war%20films" + END_GENRE_PATH;
	private final String CRIME_SEARCH = BASE_GENRE_PATH + "crime%20films" + END_GENRE_PATH;
	private final String ROMANCE_SEARCH = BASE_GENRE_PATH + "Romance%20films" + END_GENRE_PATH;
	private final String HISTORICAL_SEARCH = BASE_GENRE_PATH + "Historical%20films" + END_GENRE_PATH;
	private final String FILM_NOIR_SEARCH = BASE_GENRE_PATH + "Film%20noir%20films" + END_GENRE_PATH;
	private final String THRILLER_SEARCH = BASE_GENRE_PATH + 
			"Thrillers%20%28Motion%20pictures%29%20films" + END_GENRE_PATH;
	private final String FANTASY_SEARCH = BASE_GENRE_PATH + "Fantasy%20films" + END_GENRE_PATH;
	
	private final String SEARCH_PROMPT = "Search the Jones Media VHS & DVD Collection";
	
	private JPanel bottom, searchMovies, genreButtons, promoPanel;
	private JButton searchButton, logo;
	private JTextField searchBar;

	/**
	 * Constructor of MovieKiosk which creates all the buttons at the top of the screen, the search 
	 * bar, and a PromotionPanel over a SearchPanel (contained within a CardLayout).
	 */
	private MovieKiosk() {
		fullScreenSetUp();

		// Jones Media Center logo in the upper left hand corner of the screen. 
		logo = new JButton(new ImageIcon(getClass().getResource("resources/Jones_Logo.png")));
		logo.setBorder(BorderFactory.createEmptyBorder());
		logo.setContentAreaFilled(false);
		logo.addActionListener(this);

		// Panel which contains 20 different genre buttons.
		genreButtons = createGenrePanel();

		JPanel topButtons = new JPanel();
		topButtons.add(logo);
		topButtons.add(genreButtons);

		// JPanel which contains text input field and a search button.
		JPanel search = new JPanel();
		searchBar = new JTextField(SEARCH_PROMPT);
		searchBar.setPreferredSize(new Dimension(600, 50));
		searchBar.setFont(MyFont.SEARCH_TEXT);
		searchBar.addActionListener(this);
		// When search bar is clicked the entire text is highlighted.
		searchBar.addFocusListener(new java.awt.event.FocusAdapter() {
    	    public void focusGained(java.awt.event.FocusEvent evt) {
    	    	SwingUtilities.invokeLater( new Runnable() {

    				@Override
    				public void run() {
    					searchBar.selectAll();		
    				}
    			});
    	    }
    	});

		searchButton = new JButton(new ImageIcon(
				getClass().getResource("resources/Magnifying_Glass.png")));
		searchButton.setBorder(BorderFactory.createEmptyBorder());
		searchButton.setContentAreaFilled(false);
		searchButton.addActionListener(this);

		search.add(searchBar);
		search.add(searchButton);

		// JPanel which contains JMC logo, genre buttons and the search bar panel.
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(topButtons);
		top.add(search);

		// Creating a PromotionalPanel that displays the promotional movies.
		promoPanel = new PromotionPanel();
		
		// Empty SearchPanel (hidden when the kiosk is first opened).
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
	 * Sets up the application so that it will be in full screen mode the entire time the 
	 * application is in use. By default it selects the first screen found.
	 * 
	 * Heavily used the example code from the Java fullscreen tutorial and API.
	 */
	private void fullScreenSetUp() {

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		boolean isFullScreen = devices[MONITOR].isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);

		if (isFullScreen) {
			// Full-screen mode.
			devices[MONITOR].setFullScreenWindow(this);
			validate();
		} else {
			// Windowed mode.
			pack();
			setVisible(true);
		}
	}
	
	/**
	 * Creates the genre panel with 20 different buttons. Each buttons represents a different genre 
	 * button. When a button is clicked it displays a canned search in the SearchPanel. 
	 * 
	 * @return JPanel with all genre buttons.
	 */
	private JPanel createGenrePanel(){
		JPanel genreButtons = new JPanel();
		genreButtons.setLayout(null);
		genreButtons.setPreferredSize(new Dimension(1694, 216));
			
		// Animation.
		genreButtons.add(
				new GenreButton(0, 1, "resources/Genre_Animation.png", ANIMATION_SEARCH, this));
		// Horror.
		genreButtons.add(
				new GenreButton(284, 0, "resources/Genre_Horror.png", HORROR_SEARCH, this));
		// Western.
		genreButtons.add(
				new GenreButton(489, 0, "resources/Genre_Western.png", WESTERN_SEARCH, this));
		// Musical.
		genreButtons.add(
				new GenreButton(723, 0, "resources/Genre_Musical.png", MUSICAL_SEARCH, this));
		// Mystery.
		genreButtons.add(
				new GenreButton(946, 0, "resources/Genre_Mystery.png", MYSTERY_SEARCH, this));
		// Film Noir.
		genreButtons.add(
				new GenreButton(1168, 0, "resources/Genre_Film_Noir.png", FILM_NOIR_SEARCH, this));
		// Historical.
		genreButtons.add(new GenreButton(1307, 0, "resources/Genre_Historical.png", 
				HISTORICAL_SEARCH, this));	
		// Short Films.
		genreButtons.add(new GenreButton(1548, 0, "resources/Genre_Short_Films.png", 
				SHORT_FILMS_SEARCH, this));
		// Science Fiction.
		genreButtons.add(new GenreButton(0, 77, "resources/Genre_Science_Fiction.png", 
				SCIENCE_FICTION_SEARCH, this));
		// Adventure.
		genreButtons.add(
				new GenreButton(192, 77, "resources/Genre_Adventure.png", ADVENTURE_SEARCH, this));
		// Drama.
		genreButtons.add(
				new GenreButton(468, 77, "resources/Genre_Drama.png", DRAMA_SEARCH, this));
		// Children.
		genreButtons.add(
				new GenreButton(660, 77, "resources/Genre_Children's.png", CHILDREN_SEARCH, this));
		// Television Program.
		genreButtons.add(new GenreButton(924, 77, "resources/Genre_Television_Programs.png", 
				TELEVISION_PROGRAM_SEARCH, this));
		// Roman.
		genreButtons.add(
				new GenreButton(1307, 73, "resources/Genre_Romance.png", ROMANCE_SEARCH, this));	
		// Documentary.
		genreButtons.add(new GenreButton(192, 150, "resources/Genre_Documentary.png", 
				DOCUMENTARY_SEARCH, this));
		// Comedy.
		genreButtons.add(
				new GenreButton(546, 150, "resources/Genre_Comedy.png", COMEDY_SEARCH, this));
		// War.
		genreButtons.add(new GenreButton(788, 150, "resources/Genre_War.png", WAR_SEARCH, this));
		// Thriller.
		genreButtons.add(
				new GenreButton(1168, 147, "resources/Genre_Thriller.png", THRILLER_SEARCH, this));
		// Fantasy.
		genreButtons.add(
				new GenreButton(1355, 147, "resources/Genre_Fantasy.png", FANTASY_SEARCH, this));	
		// Crime.
		genreButtons.add(
				new GenreButton(1548, 138, "resources/Genre_Crime.png", CRIME_SEARCH, this));
		
		return genreButtons;
	}

	/**
	 * Waits for the search button, the logo or the genre buttons to be clicked. When one of these 
	 * buttons is pressed, the corresponding card in the CardLayout is shown.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Gets cardLayout needed to switch cards.
		CardLayout cards = (CardLayout) (bottom.getLayout()); 

		if (e.getSource() == logo) {
			// When the JMC logo is clicked the user is directed back to the display of promotional 
			// movies.
			cards.show(bottom, NEW_MOVIES);
			// Make sure the promotional panel is flipped to the right card.
			CardLayout cl = (CardLayout) (promoPanel.getLayout());
			cl.show(promoPanel, "Promo_Movies");
			searchBar.setText(SEARCH_PROMPT);
		} else if (e.getSource() == searchButton || e.getSource() == searchBar) {
			// While typing enter was hit or the search button was clicked.
			searchMovies = new SearchPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			cards.show(bottom, SEARCH_MOVIES);
		} else { 
			// Assume its a genre button and based on which button was clicked create the correct 
			// searchPanel.
			GenreButton g = (GenreButton)e.getSource();
			searchMovies = new SearchPanel(g.getURL());
				
			// Once searchPanel object has been created, its added to the SEARCH_MOVIES card.
			bottom.add(searchMovies, SEARCH_MOVIES);
			searchBar.setText(SEARCH_PROMPT);
			cards.show(bottom, SEARCH_MOVIES);
		} 	
	}

	/**
	 * Main method that creates the MovieKiosk object.
	 */
	public static void main(String[] args) {
		new MovieKiosk();
	}

}