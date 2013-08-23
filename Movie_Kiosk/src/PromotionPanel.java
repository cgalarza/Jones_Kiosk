import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
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
public class PromotionPanel extends JPanel implements ActionListener {

	private final String FILE_EXTENTION = ".jpg";
	private final String PATH_DISPLAY_MOVIES = "/Users/carlagalarza/Pictures/Display_Movies/";
	private final String DISPLAY_MOVIES_FILE = "/Users/carlagalarza/Desktop/display_movies";
	private final int SPEED = 3000;
	private JButton next;
	private JButton previous;
	private Timer timer;
	private JPanel rotatingPanel;

	private ArrayList<Item> promotionalMovies;

	private int current; // Counter that represents the first movie to be
							// displayed
	private int total;

	/*
	 * Constructor
	 */
	public PromotionPanel() {

		this.setLayout(new BorderLayout());

		File f = new File(DISPLAY_MOVIES_FILE);
		promotionalMovies = new ArrayList<Item>();

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

				Item i = new Item(tokens[0], tokens[1], tokens[2]);
				promotionalMovies.add(i);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		total = promotionalMovies.size();
		current = 0;
		rotatingPanel = createRotatingMoviesPanel();

		JLabel displayTitle = new JLabel(title);
		displayTitle.setFont(Globals.LARGE_TEXT_BOLD);
		displayTitle.setAlignmentX(CENTER_ALIGNMENT);
		displayTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		next = new JButton(new ImageIcon(getClass().getResource(
				"Right_Arrow.png")));
		next.setBorder(BorderFactory.createEmptyBorder());
		next.setContentAreaFilled(false);
		next.addActionListener(this);

		previous = new JButton(new ImageIcon(getClass().getResource(
				"Left_Arrow.png")));
		previous.setBorder(BorderFactory.createEmptyBorder());
		previous.setContentAreaFilled(false);
		previous.addActionListener(this);

		this.add(previous, BorderLayout.WEST);
		this.add(next, BorderLayout.EAST);
		this.add(displayTitle, BorderLayout.NORTH);
		this.add(rotatingPanel, BorderLayout.CENTER);

		// Set up timer for automatic card flipping
		timer = new Timer(SPEED, this);
		timer.start();
	}

	private JPanel createRotatingMoviesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		for (int a = 0, counter = current; a < 4; a++, counter++) {

			if (counter == total) 
				counter = 0;
			
			panel.add(new DisplayItemPanel(promotionalMovies.get(counter)));
		}
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next || e.getSource() == timer) {
			if (current < total-1)
				current++;
			else
				current = 0;
			BorderLayout layout = (BorderLayout) this.getLayout();
			this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			
			rotatingPanel = createRotatingMoviesPanel();
			this.add(rotatingPanel, BorderLayout.CENTER);
			this.validate();

			timer.restart();
		}

		else if (e.getSource() == previous) {
			if (current == 0)
				current = total - 1;
			else
				current--;
			
			BorderLayout layout = (BorderLayout) this.getLayout();
			this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			
			rotatingPanel = createRotatingMoviesPanel();
			this.add(rotatingPanel, BorderLayout.CENTER);
			this.validate();
			timer.restart();
		}
	}
}
