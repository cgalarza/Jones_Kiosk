import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MovieKiosk extends JFrame implements ActionListener {

	// Name of cards in cardLayouts.
	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";
	
	// By default uses the first monitor found
	private final int MONITOR = 1;
	
	// Canned searches for genre buttons.
	private final String ANIMATION_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Animated%20films%20and%20branch:branchbajmz";
	private final String HORROR_SEARCH = "http://libcat.dartmouth.edu/search/X?s:horror%20films%20and%20branch:branchbajmz";
	private final String WESTERN_SEARCH = "http://libcat.dartmouth.edu/search/X?s:western*%20and%20branch:branchbajmz";
	private final String MUSICAL_SEARCH = "http://libcat.dartmouth.edu/search/X?s:musical%20films%20and%20branch:branchbajmz";
	private final String MYSTERY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:mystery%20films%20and%20branch:branchbajmz";
	private final String SHORT_FILMS_SEARCH = "http://libcat.dartmouth.edu/search/X?s:short%20films%20and%20branch:branchbaj**";
	private final String SCIENCE_FICTION_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Science%20Fiction%20and%20branch:branchbajmz";
	private final String ADVENTURE_SEARCH = "http://libcat.dartmouth.edu/search/X?s:adventure%20films%20and%20branch:branchbajmz";
	private final String DRAMA_SEARCH = "http://libcat.dartmouth.edu/search/X?s:drama%20films%20and%20branch:branchbajmz";
	private final String CHILDREN_SEARCH = "http://libcat.dartmouth.edu/search~S1?/Xs:children%27s%20films%20and%20branch:branchbajmz";
	private final String TELEVISION_PROGRAM_SEARCH = "http://libcat.dartmouth.edu/search~S4?/dTelevision+programs./dtelevision+programs/-3%2C-1%2C0%2CB/exact&FF=dtelevision+programs&1%2C165%2C";
	private final String DOCUMENTARY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:documentary%20films%20and%20branch:branchbajmz";
	private final String COMEDY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:comedy%20films%20and%20branch:branchbajmz";
	private final String WAR_SEARCH = "http://libcat.dartmouth.edu/search/X?s:war%20films%20and%20branch:branchbajmz";
	private final String CRIME_SEARCH = "http://libcat.dartmouth.edu/search/X?s:crime%20films%20and%20branch:branchbajmz";
	private final String ROMANCE_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Romance%20films%20and%20branch:branchbaj**";
	private final String HISTORICAL_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Historical%20films%20and%20branch:branchbaj**";
	private final String FILM_NOIR_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Film%20noir%20films%20and%20branch:branchbaj**";
	private final String THRILLER_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Thrillers%20%28Motion%20pictures%29%20films%20and%20branch:branchbaj**";
	private final String FANTASY_SEARCH = "http://libcat.dartmouth.edu/search/X?s:Fantasy%20films%20and%20branch:branchbaj**";
	
	private final String SEARCH_PROMPT = "Search the Jones Media VHS & DVD Collection";
	
	
	private JPanel bottom, searchMovies, genreButtons, promoPanel;
	private JButton searchButton, logo, adventure, horror, western, animation, musical, mystery, 
		shortFilms, scienceFiction, drama, children, televisionPrograms, documentary, comedy, war, crime, 
		filmNoir, thriller, historical, romance, fantasy;
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
		// When search bar is clicked the entire text is highlighted
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
		boolean isFullScreen = devices[MONITOR].isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);

		if (isFullScreen) {
			// Full-screen mode
			devices[MONITOR].setFullScreenWindow(this);
			validate();
		} else {
			// Windowed mode
			pack();
			setVisible(true);
		}
	}
	
	/**
	 * Creates the genre panel with 20 different buttons. Each buttons represents a different genre 
	 * button and once clicked displays a canned search in a SearchPanel. 
	 * 
	 * Each buttons was placed by absolute positioning (no other layout allowed the flexibility needed).
	 * 
	 * @return JPanel with all genre buttons
	 */
	
	private JPanel createGenrePanel(){
		// TODO: Could genreButtons be their own class?
		JPanel genreButtons = new JPanel();
		genreButtons.setLayout(null);
		genreButtons.setPreferredSize(new Dimension(1694, 216));
		
		animation = new JButton(new ImageIcon(getClass().getResource("Genre_Animation.png")));
		animation.addActionListener(this);
		animation.setBorder(BorderFactory.createEmptyBorder());
		animation.setContentAreaFilled(false);
		animation.setBounds(0, 1, 279, 68);
		genreButtons.add(animation);
		
		horror = new JButton(new ImageIcon(getClass().getResource("Genre_Horror.png")));
		horror.addActionListener(this);
		horror.setBorder(BorderFactory.createEmptyBorder());
		horror.setContentAreaFilled(false);
		horror.setBounds(284, 0, 200, 70);
		genreButtons.add(horror);
		
		western = new JButton(new ImageIcon(getClass().getResource("Genre_Western.png")));
		western.addActionListener(this);
		western.setBorder(BorderFactory.createEmptyBorder());
		western.setContentAreaFilled(false);
		western.setBounds(489, 0, 229, 70);
		genreButtons.add(western);
		
		musical = new JButton(new ImageIcon(getClass().getResource("Genre_Musical.png")));
		musical.addActionListener(this);
		musical.setBorder(BorderFactory.createEmptyBorder());
		musical.setContentAreaFilled(false);
		musical.setBounds(723, 0, 218, 70);
		genreButtons.add(musical);
		
		mystery = new JButton(new ImageIcon(getClass().getResource("Genre_Mystery.png")));
		mystery.addActionListener(this);
		mystery.setBorder(BorderFactory.createEmptyBorder());
		mystery.setContentAreaFilled(false);
		mystery.setBounds(946, 0, 217, 70);
		genreButtons.add(mystery);
		
		filmNoir = new JButton(new ImageIcon(getClass().getResource("Genre_Film_Noir.png")));
		filmNoir.addActionListener(this);
		filmNoir.setBorder(BorderFactory.createEmptyBorder());
		filmNoir.setContentAreaFilled(false);
		filmNoir.setBounds(1168, 0, 134, 142);
		genreButtons.add(filmNoir);
		
		historical = new JButton(new ImageIcon(getClass().getResource("Genre_Historical.png")));
		historical.addActionListener(this);
		historical.setBorder(BorderFactory.createEmptyBorder());
		historical.setContentAreaFilled(false);
		historical.setBounds(1307, 0, 236, 69);
		
		shortFilms = new JButton(new ImageIcon(getClass().getResource("Genre_Short_Films.png")));
		shortFilms.addActionListener(this);
		shortFilms.setBorder(BorderFactory.createEmptyBorder());
		shortFilms.setContentAreaFilled(false);
		shortFilms.setBounds(1548, 0, 152, 132);
		genreButtons.add(shortFilms);
		
		scienceFiction = new JButton(new ImageIcon(getClass().getResource("Genre_Science_Fiction.png")));
		scienceFiction.addActionListener(this);
		scienceFiction.setBorder(BorderFactory.createEmptyBorder());
		scienceFiction.setContentAreaFilled(false);
		scienceFiction.setBounds(0, 77, 187, 139);
		genreButtons.add(scienceFiction);
		
		adventure = new JButton(new ImageIcon(getClass().getResource("Genre_Adventure.png")));
		adventure.addActionListener(this);
		adventure.setBorder(BorderFactory.createEmptyBorder());
		adventure.setContentAreaFilled(false);
		adventure.setBounds(192, 77, 271, 69);
		genreButtons.add(adventure);
		
		drama = new JButton(new ImageIcon(getClass().getResource("Genre_Drama.png")));
		drama.addActionListener(this);
		drama.setBorder(BorderFactory.createEmptyBorder());
		drama.setContentAreaFilled(false);
		drama.setBounds(468, 77, 187, 69);
		genreButtons.add(drama);
		
		children = new JButton(new ImageIcon(getClass().getResource("Genre_Children's.png")));
		children.addActionListener(this);
		children.setBorder(BorderFactory.createEmptyBorder());
		children.setContentAreaFilled(false);
		children.setBounds(660, 77, 259, 69);
		genreButtons.add(children);
		
		televisionPrograms = new JButton(new ImageIcon(getClass().getResource("Genre_Television_Programs.png")));
		televisionPrograms.addActionListener(this);
		televisionPrograms.setBorder(BorderFactory.createEmptyBorder());
		televisionPrograms.setContentAreaFilled(false);
		televisionPrograms.setBounds(924, 77, 239, 139);
		genreButtons.add(televisionPrograms);
		
		romance = new JButton(new ImageIcon(getClass().getResource("Genre_Romance.png")));
		romance.addActionListener(this);
		romance.setBorder(BorderFactory.createEmptyBorder());
		romance.setContentAreaFilled(false);
		romance.setBounds(1307, 73, 236, 69);
		genreButtons.add(romance);	
		
		documentary = new JButton(new ImageIcon(getClass().getResource("Genre_Documentary.png")));
		documentary.addActionListener(this);
		documentary.setBorder(BorderFactory.createEmptyBorder());
		documentary.setContentAreaFilled(false);
		documentary.setBounds(192, 150, 349, 67);
		genreButtons.add(documentary);
		
		comedy = new JButton(new ImageIcon(getClass().getResource("Genre_Comedy.png")));
		comedy.addActionListener(this);
		comedy.setBorder(BorderFactory.createEmptyBorder());
		comedy.setContentAreaFilled(false);
		comedy.setBounds(546, 150, 235, 67);
		genreButtons.add(comedy);
		
		war = new JButton(new ImageIcon(getClass().getResource("Genre_War.png")));
		war.addActionListener(this);
		war.setBorder(BorderFactory.createEmptyBorder());
		war.setContentAreaFilled(false);
		war.setBounds(788, 150, 131, 67);
		genreButtons.add(war);
		
		genreButtons.add(historical);	
		thriller = new JButton(new ImageIcon(getClass().getResource("Genre_Thriller.png")));
		thriller.addActionListener(this);
		thriller.setBorder(BorderFactory.createEmptyBorder());
		thriller.setContentAreaFilled(false);
		thriller.setBounds(1168, 147, 184, 68);
		genreButtons.add(thriller);

		fantasy = new JButton(new ImageIcon(getClass().getResource("Genre_Fantasy.png")));
		fantasy.addActionListener(this);
		fantasy.setBorder(BorderFactory.createEmptyBorder());
		fantasy.setContentAreaFilled(false);
		fantasy.setBounds(1355, 147, 189, 68);
		genreButtons.add(fantasy);	
		
		crime = new JButton(new ImageIcon(getClass().getResource("Genre_Crime.png")));
		crime.addActionListener(this);
		crime.setBorder(BorderFactory.createEmptyBorder());
		crime.setContentAreaFilled(false);
		crime.setBounds(1548, 138, 152, 78);
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
			searchBar.setText(SEARCH_PROMPT);
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
				else if (e.getSource() == fantasy)
					searchMovies = new SearchPanel(new URL(FANTASY_SEARCH));
				else if (e.getSource() == romance)
					searchMovies = new SearchPanel(new URL(ROMANCE_SEARCH));
				else if (e.getSource() == thriller)
					searchMovies = new SearchPanel(new URL(THRILLER_SEARCH));
				else if (e.getSource() == filmNoir)
					searchMovies = new SearchPanel(new URL(FILM_NOIR_SEARCH));
				else if (e.getSource() == historical)
					searchMovies = new SearchPanel(new URL(HISTORICAL_SEARCH));
				
				// Once searchPanel object has been created add to SEARCH_MOVIES card
				bottom.add(searchMovies, SEARCH_MOVIES);
				searchBar.setText(SEARCH_PROMPT);
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