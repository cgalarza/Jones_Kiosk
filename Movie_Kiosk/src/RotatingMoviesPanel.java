import java.awt.CardLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RotatingMoviesPanel extends JPanel {

	private int totalPages;
	private int currentPage;

	public RotatingMoviesPanel(ArrayList<String> names,
			ArrayList<String> callNumbers, ArrayList<BufferedImage> images) {

		this.setLayout(new CardLayout());

		totalPages = (int) Math.ceil(names.size() / 4.0);
		System.out.println(totalPages);

		for (int a = 1; a <= totalPages; a++) {
			JPanel fourMovies = new JPanel();

			int start = (a*4)-4;
			int end = (a*4 > names.size()) ? names.size()-1 : (a*4)-1;
			
			for (int i = start; i <= end; i++) {
				JPanel movie = new JPanel();
				movie.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				movie.setLayout(new BoxLayout(movie, BoxLayout.Y_AXIS));
				movie.setAlignmentX(Component.LEFT_ALIGNMENT);
				movie.add(new JLabel(new ImageIcon(images.get(i))));
				
				JLabel name = new JLabel(names.get(i));
				name.setFont(Globals.SMALL_TEXT);
				movie.add(name);
			
				JLabel accessionNum = new JLabel(callNumbers.get(i));
				accessionNum.setFont(Globals.SMALL_TEXT);
				movie.add(accessionNum);

				
				fourMovies.add(movie);
			}

			this.add(fourMovies, "CARD_" + a);

		}
	
		currentPage = 1;

	}
	
	public int getCurrentPage(){
		return this.currentPage;
		
	}
	public int getTotalPages(){
		return this.totalPages;
	}
	
	public void switchToNextPage(){
		if ((currentPage >= 1) && (currentPage < totalPages)) {
			currentPage++;
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, "CARD_" + currentPage);
		}
		
	}
	public void switchToPreviousPage(){
		if(currentPage > 1){
			currentPage--;
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, "CARD_" + currentPage);

		}
	}

}
