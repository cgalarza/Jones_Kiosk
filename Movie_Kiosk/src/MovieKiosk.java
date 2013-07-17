import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	private JButton searchButton;
	private JTextField searchBar;

	public MovieKiosk() {
		fullScreenSetUp();
		System.out.println("starting...");

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
		topButtons.add(jinniButton);
		topButtons.add(imdbButton);
		topButtons.add(catologButton);
		topButtons.add(rottenTomatoes);

		// Search JPanel contains text input field and search button
		JPanel search = new JPanel();

		searchBar = new JTextField("Enter Search Term");
		searchBar.setPreferredSize(new Dimension(600, 50));
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 25));
		searchBar.addActionListener(this);
		search.add(searchBar);

		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		search.add(searchButton);

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
		this.setVisible(true);

	}

	// Used example code from java fullscreen support page/api
	private void fullScreenSetUp() {
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
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

	public static void main(String[] args) {
		MovieKiosk m = new MovieKiosk();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton || e.getSource() == searchBar) {
			searchMovies = new SearchMoviesPanel(searchBar.getText());
			bottom.add(searchMovies, SEARCH_MOVIES);
			CardLayout cards = (CardLayout) (bottom.getLayout());
			cards.show(bottom, SEARCH_MOVIES);
			
		}
	}

}
