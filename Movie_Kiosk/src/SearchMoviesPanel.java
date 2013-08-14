import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class SearchMoviesPanel extends JPanel implements ActionListener {

	private ArrayList<CatalogItem> searchResults;
	private final String HOMEPAGE_URL = "http://libcat.dartmouth.edu";
	private JPanel movies, navigationBar;
	private int totalPages = 1, currentPage = 1;
	private Elements links; // all the links retrieved
	private JButton previousResults, moreResults;
	private JLabel numberOfPages;
	private Boolean noResults;

	// Default constructor
	public SearchMoviesPanel() {
		this.add(new JLabel("Search results for are loading..."));
	}

	public SearchMoviesPanel(String searchTerm) {
		this.setLayout(new BorderLayout());
		movies = new JPanel();
		movies.setLayout(new CardLayout());

		this.searchResults = new ArrayList<CatalogItem>();
		noResults = performSearch(searchTerm);
		retrieveSearchResults(currentPage);
		displayResults();
		createNavigationBar();

		// Adding JPanel which contains movies found as search results
		// and a navigation bar at the bottom.
		this.add(movies, BorderLayout.CENTER);
		this.add(navigationBar, BorderLayout.SOUTH);

	}

	public boolean performSearch(String searchTerm) {
		String firstUrlPart = "http://libcat.dartmouth.edu/search/X?SEARCH=";
		String secondUrlPart = "&searchscope=4&SORT=D&Da=&Db=&p=";

		String[] words = searchTerm.split(" ");
		String formatedSearchTerm = "";

		for (String s : words) {
			formatedSearchTerm = formatedSearchTerm.concat(s + "+");
		}

		String completeURL = firstUrlPart + formatedSearchTerm + secondUrlPart;

		Document doc = null;
		try {
			doc = Jsoup.connect(completeURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Check to see if there is an error message
		if (doc.select(".errormessage").size() > 0) 
			return true; //if there is return true
		else {

			System.out.println(doc.select("i").text());

			String[] entriesFoundTokens = doc.select("i").text().split(" ");

			if (Integer.parseInt(entriesFoundTokens[0]) == 1) {
				links = new Elements();
				Attributes attr = new Attributes();
				attr.put("href", completeURL);
				links.add(new Element(Tag.valueOf("a"), HOMEPAGE_URL, attr));
				System.out.println(links.toString());
				
				
			} else {

				links = doc.select("span.briefcitTitle");
				System.out.println(links.toString());
				
				totalPages = (int) Math.ceil(links.size() / 4.0);
			}
			return false;
		}
	}

	public void retrieveSearchResults(int page) {
		searchResults = new ArrayList<CatalogItem>();

		int start = (page * 4) - 4;
		int end = (page * 4 > links.size()) ? links.size() - 1 : page * 4 - 1;

		// only gets the urls of the elements specified
		for (int i = start; i <= end; i++) {

			// if there is only one link, its formatted differently
			if (links.size() == 1){
				String url = links.get(0).select("a[href]").attr("href").toString();
				searchResults.add(new CatalogItem(url));
			}
			else {
			Element link = links.get(i);

			// Retrieves url of page
			String url = HOMEPAGE_URL.concat(link.select("a[href]")
					.attr("href").toString());

			searchResults.add(new CatalogItem(url));
			}
		}
	}

	private void displayResults() {
		JPanel card = new JPanel();
		card.setLayout(new GridLayout(2, 2));

		// for each search result create a panel
		for (CatalogItem a : searchResults) {
			card.add(new CatalogItemPanel(a));
		}

		movies.add(card, "CARD_" + currentPage);

		CardLayout cl = (CardLayout) (movies.getLayout());
		cl.show(movies, "CARD_" + currentPage);
	}

	public void createNavigationBar() {
		// Creating navigation bar at the bottom of the screen
		navigationBar = new JPanel();
		navigationBar.setLayout(new BoxLayout(navigationBar, BoxLayout.X_AXIS));
		navigationBar
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Creating the next button with image.
		previousResults = new JButton(new ImageIcon(getClass().getResource("Previous.png")));
		previousResults.setBorder(BorderFactory.createEmptyBorder());
		previousResults.setContentAreaFilled(false);
		previousResults.addActionListener(this);

		// Label which displays the number of pages.
		numberOfPages = new JLabel("Page 1 of " + totalPages);

		// Creating the previous button with image.	
		moreResults = new JButton(new ImageIcon(getClass().getResource("More.png")));
		moreResults.setBorder(BorderFactory.createEmptyBorder());
		moreResults.setContentAreaFilled(false);
		moreResults.addActionListener(this);

		navigationBar.add(previousResults);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(numberOfPages);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(moreResults);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == moreResults) && (currentPage >= 1)
				&& (currentPage < totalPages)) {
			currentPage++;
			retrieveSearchResults(currentPage);
			displayResults();
			numberOfPages.setText("Page " + currentPage + " of " + totalPages);

		}

		else if ((e.getSource() == previousResults) && (currentPage > 1)) {
			currentPage--;
			CardLayout cl = (CardLayout) (movies.getLayout());
			cl.show(movies, "CARD_" + currentPage);

			numberOfPages.setText("Page " + currentPage + " of " + totalPages);

		}

	}

}
