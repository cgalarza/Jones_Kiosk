import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";
	private JPanel bottom, searchMovies;
	private JButton searchButton, logo;
	private JPanel genreButtons;
	private JTextField searchBar;
	private JButton adventure, horror, western, animation, musical, mystery, shortFilms, scienceFiction;
	private JButton drama, children, televisionPrograms, documentary, comedy, war, crime;

	/*
	 * Constructor of MovieKiosk which creates all the buttons at the top of the
	 * screen, the search bar and a NewMoviesPanel over a SearchMoviesPanel in a
	 * CardLayout.
	 */
	public MovieKiosk() {
		fullScreenSetUp();

		// Jones Media Center Logo and button in the upper left
		// hand corner of the screen. When the logo is clicked the
		// user is directed back to the homepage with the display of
		// movies.

		logo = new JButton(new ImageIcon(getClass().getResource("Jones_Logo.png")));
		logo.setBorder(BorderFactory.createEmptyBorder());
		logo.setContentAreaFilled(false);
		logo.addActionListener(this);

		// Panel which contains buttons with different genres
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

		// JPanel which contains both buttons to webpages at the top
		// and the search bar panel.
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(topButtons);
		top.add(search);

		// Creating a NewMoviesPanel which will display the new
		// aquisitions on the main kiosk page.
		JPanel newMovies = new PromotionPanel();

		searchMovies = new SearchPanel();

		bottom = new JPanel();
		bottom.setLayout(new CardLayout());
		bottom.add(newMovies, NEW_MOVIES);
		bottom.add(searchMovies, SEARCH_MOVIES);

		this.getContentPane().add(top, BorderLayout.NORTH);
		this.getContentPane().add(bottom, BorderLayout.CENTER);
		this.setBackground(Color.WHITE);
		this.setVisible(true);

	}

	/**
	 * Sets up the application so that it will be in full screen mode at all
	 * times.
	 * 
	 * Heavily used the example code from the Java fullscreen tutorial and API.
	 */
	private void fullScreenSetUp() {

		// This will be the final setup
		// have to hide both dock and menu bar
		// this.setUndecorated(true);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// this.setVisible(true);

		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		boolean isFullScreen = devices[1].isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);

		if (isFullScreen) {
			// Full-screen mode
			devices[1].setFullScreenWindow(this);
			validate();
		} else {
			// Windowed mode
			pack();
			setVisible(true);
		}
	}
	
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
	

	/*
	 * Method that waits for the search button or the logo to be pressed. When
	 * one of these buttons are pressed the corresponding card in the CardLayout
	 * is shown.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton || e.getSource() == searchBar) {
			// Lets the user know that their request has been received
			searchMovies = new SearchPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, SEARCH_MOVIES);
		} else if (e.getSource() == logo) {
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, NEW_MOVIES);
			searchBar.setText("Search the Jones Media Collection");
		} else if (e.getSource() == animation){
			// webpage: http://libcat.dartmouth.edu/search/X?s:Animated%20films%20and%20branch:branchbaj**
			
			try {
				searchMovies = new SearchPanel(new URL("http://libcat.dartmouth.edu/search/X?s:Animated%20films%20and%20branch:branchbaj**"));
				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			bottom.add(searchMovies, SEARCH_MOVIES);
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, SEARCH_MOVIES);
		}
	}

	public void openWebpage(URL url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop()
				: null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url.toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Main method that creates the kiosk object.
	 */
	public static void main(String[] args) {
		new MovieKiosk();
	}

}
