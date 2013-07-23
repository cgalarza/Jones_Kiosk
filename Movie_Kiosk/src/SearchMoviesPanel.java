import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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

	// Default constructor
	public SearchMoviesPanel() {
		this.add(new JLabel("Search results for are loading..."));
	}

	public SearchMoviesPanel(String searchTerm) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.searchResults = new ArrayList<CatalogItem>();
		performSearch(searchTerm);
		displayResults();

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

		for (Element link : links) {
			System.out.println("");

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
			this.add(new CatalogItemPanel(a));

		}
	}

}
