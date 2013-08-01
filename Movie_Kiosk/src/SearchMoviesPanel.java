import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class SearchMoviesPanel extends JPanel {

	private ArrayList<CatalogItem> searchResults;
	private final String HOMEPAGE_URL = "http://libcat.dartmouth.edu";
	JPanel movies;
	JPanel navigationBar;

	// Default constructor
	public SearchMoviesPanel() {
		this.add(new JLabel("Search results for are loading..."));
	}

	public SearchMoviesPanel(String searchTerm) {
		this.setLayout(new BorderLayout());
		movies = new JPanel();
		movies.setLayout(new GridLayout(2,2));
		this.searchResults = new ArrayList<CatalogItem>();
		performSearch(searchTerm);
		displayResults();
		createNavigationBar();
	
		
		// Adding movies found as search results and navigation bar at the bottom
		this.add(movies, BorderLayout.CENTER);
		this.add(navigationBar, BorderLayout.SOUTH);
		

	}
	
	public void createNavigationBar(){
		// Creating navigation bar at the bottom of the screen
		navigationBar = new JPanel();
		navigationBar.setLayout(new BoxLayout(navigationBar, BoxLayout.X_AXIS));
		JButton previousResults = new JButton("Previous Results");
		
		JLabel numberOfPages = new JLabel("Page 1 of 3");
		
		JButton moreResults = new JButton("More Results");
		
		navigationBar.add(previousResults);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(numberOfPages);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(moreResults);

		
	}

	public void performSearch(String searchTerm) {
		String firstUrlPart = "http://libcat.dartmouth.edu/search/X?SEARCH=";
		String secondUrlPart = "&searchscope=4&SORT=D&Da=&Db=&p=";

		String[] words = searchTerm.split(" ");
		String formatedSearchTerm = "";

		for (String s : words) {
			formatedSearchTerm = formatedSearchTerm.concat(s);
			formatedSearchTerm = formatedSearchTerm.concat("+");
		}

		String completeURL = firstUrlPart + formatedSearchTerm + secondUrlPart;

		Document doc = null;
		try {
			doc = Jsoup.connect(completeURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Elements links = doc.select("span.briefcitTitle");

		// only gets the first four results
		for (int i = 0; i < 4; i++) {
			System.out.println("");
			
			Element link = links.get(i);
			
			String url = HOMEPAGE_URL.concat(link.select("a[href]")
					.attr("href").toString());
			// added some comments

			searchResults.add(new CatalogItem(url));
			System.out.println(url);

		}

	}

	private void displayResults() {
		// for each search result
		for (CatalogItem a : searchResults) {
			movies.add(new CatalogItemPanel(a));

		}
	}

}
