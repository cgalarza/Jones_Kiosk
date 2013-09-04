import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class PromotionPanel extends JPanel implements ActionListener, MouseListener{

	private final String FILE_EXTENTION = ".jpg";
	private final String DISPLAY_MOVIES_FILE = System.getProperty("user.home") + "/Desktop/display_movies.rtf";
	private final int SPEED = 3000;
	private JButton next, previous, back;
	private Timer timer;
	private JPanel rotatingPanel, centerPanel;

	private ArrayList<Item> promotionalMovies;

	private int current; // Counter that represents the first movie to be displayed
	private int total;

	/*
	 * Constructor
	 */
	public PromotionPanel() {

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

		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(previous, BorderLayout.WEST);
		centerPanel.add(next, BorderLayout.EAST);
		centerPanel.add(displayTitle, BorderLayout.NORTH);
		centerPanel.add(rotatingPanel, BorderLayout.CENTER);
		centerPanel.addMouseListener(this);
		
		this.setLayout(new CardLayout());
		
		this.add(centerPanel, "Promo_Movies");

		CardLayout cl = (CardLayout) (this.getLayout());
		cl.show(this, "Promo_Movies");
		
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
			JPanel displayItem = new DisplayItemPanel(promotionalMovies.get(counter));
			displayItem.addMouseListener(this);
			panel.add(displayItem);
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
			BorderLayout layout = (BorderLayout) centerPanel.getLayout();
			centerPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			
			rotatingPanel = createRotatingMoviesPanel();
			centerPanel.add(rotatingPanel, BorderLayout.CENTER);
			centerPanel.validate();

			timer.restart();
		}

		else if (e.getSource() == previous) {
			if (current == 0)
				current = total - 1;
			else
				current--;
			
			BorderLayout layout = (BorderLayout) centerPanel.getLayout();
			centerPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			
			rotatingPanel = createRotatingMoviesPanel();
			centerPanel.add(rotatingPanel, BorderLayout.CENTER);
			centerPanel.validate();
			timer.restart();
		}
		else if (e.getSource() == back){
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, "Promo_Movies");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().getClass() == DisplayItemPanel.class){
			DisplayItemPanel panelClicked = (DisplayItemPanel) e.getSource();
			System.out.println(panelClicked.getItem().getTitle());
			
			JPanel verboseMovieDisplay = new VerboseItemPanel(panelClicked.getItem());
			back = new JButton("Back");
			back.addActionListener(this);
			
			JPanel verbosePanel = new JPanel();
			verbosePanel.add(back);
			verbosePanel.add(verboseMovieDisplay);
			
			this.add(verbosePanel, "Verbose_Description");
			
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, "Verbose_Description");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
