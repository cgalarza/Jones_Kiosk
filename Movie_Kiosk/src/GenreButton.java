import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class GenreButton extends JButton{

	private URL search_url; 
	
	/*
	 * Creates a button and places it based on the x and y values given.
	 * 
	 * @param x x value of where the upper left hand corner of the button is placed
	 * @param y y value of where the upper left hand corner of the button is placed
	 * @param img_path path of where the image icon image is saved in the package
	 * @param url genre search path
	 * @param l actionListener that is added to this button
	 */
	public GenreButton(int x, int y, String img_path, String url, ActionListener l){
				
		ImageIcon icon = new ImageIcon(getClass().getResource(img_path));
		this.setIcon(icon);
		
		// Each buttons was placed by absolute positioning (no other layout allowed the flexibility 
		// needed).
		this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
		this.addActionListener(l);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setContentAreaFilled(false);
		
		// Save URL as an instance variable.
		try {
			this.search_url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Return the URL object.
	 * 
	 * @return URL search genre url is returned
	 */
	public URL getURL(){
		return search_url;
	}
	
}
