import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NewMoviesPanel extends JPanel {

	public NewMoviesPanel() {
		
		File f = new File("/Users/carlagalarza/Desktop/New_Movies_Panel/display_movies");
		ArrayList<BufferedImage> movie_imgs = new ArrayList<BufferedImage>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String currentLine;
			String title = br.readLine();
			br.readLine(); // Empty line after the title
			while ((currentLine = br.readLine()) != null){
				String[] tokens = currentLine.split(", ");
				File movieFile = new File("/Users/carlagalarza/Desktop/New_Movies_Panel_Images/" + tokens[2] + ".jpg");
				movie_imgs.add(ImageIO.read(movieFile));
			}
			br.close();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel label1 = new JLabel(new ImageIcon(movie_imgs.get(0)));
		JLabel label2 = new JLabel(new ImageIcon(movie_imgs.get(1)));
		JLabel label3 = new JLabel(new ImageIcon(movie_imgs.get(2)));
		JLabel label4 = new JLabel(new ImageIcon(movie_imgs.get(3)));

		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);

	}

}
