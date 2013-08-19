import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
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

	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";
	private JPanel bottom, searchMovies;
	private JButton searchButton, logo;
	private JButton googleButton, imdbButton, catalogButton, rottenTomatoes;
	private JTextField searchBar;

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

		// Panel which contains the Google, IMDB, Rotten Tomatoes and
		// Catalog buttons at the top of the main kiosk page.
		googleButton = new JButton();
		googleButton.setText("Google");
		googleButton.setPreferredSize(new Dimension(300, 150));
		googleButton.addActionListener(this);

		imdbButton = new JButton();
		imdbButton.setText("IMDB");
		imdbButton.setPreferredSize(new Dimension(300, 150));
		imdbButton.addActionListener(this);

		catalogButton = new JButton();
		catalogButton.setText("Catolog Button");
		catalogButton.setPreferredSize(new Dimension(300, 150));
		catalogButton.addActionListener(this);
		
		rottenTomatoes = new JButton();
		rottenTomatoes.setText("Rotten Tomatoes");
		rottenTomatoes.setPreferredSize(new Dimension(300, 150));
		rottenTomatoes.addActionListener(this);
		
		JPanel topButtons = new JPanel();
		topButtons.add(logo);
		topButtons.add(googleButton);
		topButtons.add(imdbButton);
		topButtons.add(catalogButton);
		topButtons.add(rottenTomatoes);

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
		JPanel newMovies = new PromotionMoviesPanel();

		searchMovies = new SearchMoviesPanel();

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
			searchMovies = new SearchMoviesPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, SEARCH_MOVIES);
		} else if (e.getSource() == logo) {
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, NEW_MOVIES);
			searchBar.setText("Search the Jones Media Collection");
		} else if (e.getSource() == googleButton) {
			try {
				openWebpage(new URL("http://www.google.com"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == imdbButton){
			try {
				openWebpage(new URL("http://www.imdb.com"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == catalogButton){
			try {
				openWebpage(new URL("http://libcat.dartmouth.edu/search/"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == rottenTomatoes){
			try {
				openWebpage(new URL("http://www.rottentomatoes.com"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
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
