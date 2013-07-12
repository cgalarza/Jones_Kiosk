import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class SearchMoviesPanel extends JPanel{

	private ArrayList<CatalogItem> searchResults;
	private final String HOMEPAGE_URL = "http://libcat.dartmouth.edu";
	
	// Default constructor
	public SearchMoviesPanel(){
		this.add(new JLabel("Search results loading..."));
	}
	
	public SearchMoviesPanel(String searchTerm){
		this.add(new JLabel("Search results for " + searchTerm + " are loading..."));
		this.searchResults = new ArrayList<CatalogItem>();
		performSearch(searchTerm);
		
	}
	
	public void performSearch(String searchTerm){
		String firstUrlPart = "http://libcat.dartmouth.edu/search/X?SEARCH=";
		String secondUrlPart = "&searchscope=4&SORT=D&Da=&Db=&p=";
		
		
		String[] words = searchTerm.split(" ");
		String formatedSearchTerm = "";

		for (String s : words){
			formatedSearchTerm = formatedSearchTerm.concat(s);
			formatedSearchTerm = formatedSearchTerm.concat("+");
		}
		
		System.out.println(formatedSearchTerm);
		String completeURL = firstUrlPart + formatedSearchTerm + secondUrlPart;
		
		System.out.println(completeURL);
		Document doc= null;
		try {
			doc = Jsoup.connect(completeURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(doc);
		
		Elements links = doc.select("span.briefcitTitle");

		
		for (Element link : links){
			System.out.println("");
			
			String url = HOMEPAGE_URL.concat(link.select("a[href]").attr("href").toString());
			//added some comments
			
			searchResults.add(new CatalogItem(url));
			System.out.println(url);

		}
		
		
		
	}
	
}
