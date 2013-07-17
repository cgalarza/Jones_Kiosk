import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NewMoviesPanel extends JPanel {

	public NewMoviesPanel() {
		BufferedImage movie1 = null, movie2 = null, movie3 = null, movie4 = null, movie5 = null;

		try {
			movie1 = ImageIO.read(new File("After_Stonewall.jpg"));
			movie2 = ImageIO.read(new File("Harry_Potter_7_Part2.jpg"));
			movie3 = ImageIO.read(new File("21.jpg"));
			movie4 = ImageIO.read(new File("Midnights_Children.jpg"));
			movie5 = ImageIO.read(new File("The_Waiting_Room.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel label1 = new JLabel(new ImageIcon(movie1));
		JLabel label2 = new JLabel(new ImageIcon(movie2));
		JLabel label3 = new JLabel(new ImageIcon(movie3));
		JLabel label4 = new JLabel(new ImageIcon(movie4));
		JLabel label5 = new JLabel(new ImageIcon(movie5));

		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(label5);

	}

}
