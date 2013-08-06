import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private JTextField searchBar;

	/*
	 * Constructor of MovieKiosk which creates all the buttons at the top 
	 * of the screen, the search bar and a NewMoviesPanel over a SearchMoviesPanel
	 * in a CardLayout. 
	 */
	public MovieKiosk() {
		fullScreenSetUp();

		// Jones Media Center Logo and button in the upper left
		// hand corner of the screen. When the logo is clicked the
		// user is directed back to the homepage with the display of
		// movies.
		BufferedImage buffImg = null;
		try {
			buffImg = ImageIO.read(new File("Jones_Logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logo = new JButton(new ImageIcon(buffImg));
		logo.setBorder(BorderFactory.createEmptyBorder());
		logo.setContentAreaFilled(false);
		logo.addActionListener(this);

		// Panel which contains the Google, IMDB, Rotten Tomatoes and
		// Catalog buttons at the top of the main kiosk page.
		JButton googleButton = new JButton();
		googleButton.setText("Google");
		googleButton.setPreferredSize(new Dimension(300, 150));

		JButton imdbButton = new JButton();
		imdbButton.setText("IMDB");
		imdbButton.setPreferredSize(new Dimension(300, 150));

		JButton catologButton = new JButton();
		catologButton.setText("Catolog Button");
		catologButton.setPreferredSize(new Dimension(300, 150));

		JButton rottenTomatoes = new JButton();
		rottenTomatoes.setText("Rotten Tomatoes");
		rottenTomatoes.setPreferredSize(new Dimension(300, 150));

		JPanel topButtons = new JPanel();
		topButtons.add(logo);
		topButtons.add(googleButton);
		topButtons.add(imdbButton);
		topButtons.add(catologButton);
		topButtons.add(rottenTomatoes);

		// JPanel which contains text input field and a search button.
		JPanel search = new JPanel();
		searchBar = new JTextField("Search the Jones Media Collection");
		searchBar.setPreferredSize(new Dimension(600, 50));
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 25));
		searchBar.addActionListener(this);

		// Creating search button with image.
		try {
			buffImg = ImageIO.read(new File("Magnifying_Glass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchButton = new JButton(new ImageIcon(buffImg));
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
		JPanel newMovies = new NewMoviesPanel();

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
			searchMovies = new SearchMoviesPanel();
			searchMovies = new SearchMoviesPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, SEARCH_MOVIES);
		} else if (e.getSource() == logo) {
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, NEW_MOVIES);
			searchBar.setText("Search the Jones Media Collection");
		}
	}

	/*
	 * Main method that creates the kiosk object.
	 */
	public static void main(String[] args) {
		new MovieKiosk();
	}

}
