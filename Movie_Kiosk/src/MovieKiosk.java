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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MovieKiosk extends JFrame implements ActionListener {

	private final String NEW_MOVIES = "New Movies";
	private final String SEARCH_MOVIES = "Search Movies";
	private JPanel bottom, searchMovies;
	private JButton searchButton, logo;
	private JTextField searchBar;

	public MovieKiosk() {
		fullScreenSetUp();

		// Jones Media Center Logo
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

		// Buttons at the top of main kiosk page
		JButton jinniButton = new JButton();
		jinniButton.setText("Jinni");
		jinniButton.setPreferredSize(new Dimension(300, 150));

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
		topButtons.add(jinniButton);
		topButtons.add(imdbButton);
		topButtons.add(catologButton);
		topButtons.add(rottenTomatoes);

		// JPanel which contains text input field and a search button
		JPanel search = new JPanel();
		searchBar = new JTextField("Enter Search Term");
		searchBar.setPreferredSize(new Dimension(600, 50));
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 25));
		searchBar.addActionListener(this);

		// Search button with image
		BufferedImage magnifyingGlassImg = null;
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

		// JPanel which contains both buttons at the beginning and search bar
		// panel
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(topButtons);
		top.add(search);

		// Getting New Movies to Display
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

	// Used example code from java fullscreen support page/api
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

	public static void main(String[] args) {
		MovieKiosk m = new MovieKiosk();
	}

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
		}
	}

}
