import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel implements ActionListener, MouseListener {

	// Parts of search URL.
	private final String HOMEPAGE_URL = "http://libcat.dartmouth.edu";
	private final String LIMIT_SEARCH_TO_JONES = "and+(branch%3Abranchbaj**+or+branch%3Abranchrsjmc)";
	private final String BEG_URL = "http://libcat.dartmouth.edu/search/X?SEARCH=";
	private final String END_URL = "&searchscope=4&SORT=D&Da=&Db=&p=";
	
	// Name of cards in CardLayout.
	private final String SEARCH_CARD = "Search";
	private final String VERBOSE_CARD = "Verbose_Description";
	private final String CARD_PREFIX = "CARD_";
	
	private ArrayList<Item> searchResults;
	private JPanel search, movies, navigationBar;
	private int totalPages = 1, currentPage = 1;
	private Elements links; // all the links retrieved
	private JButton previousResults, moreResults, back;
	private JLabel numberOfPages;
	
	/**
	 * Constructor of SearchPanel 
	 * 
	 * @param searchTerm search term entered in by the user
	 */
	
	public SearchPanel(String searchTerm) {
		this.setLayout(new CardLayout());
		
		search = new JPanel();
		search.setLayout(new BorderLayout());
		
		movies = new JPanel();
		movies.setLayout(new CardLayout());

		this.searchResults = new ArrayList<Item>();

		String[] words = searchTerm.split(" ");
		String formatedSearchTerm = "";

		for (String s : words)
			formatedSearchTerm = formatedSearchTerm.concat(s + "+");
		
		String completeURL = BEG_URL + formatedSearchTerm + LIMIT_SEARCH_TO_JONES + END_URL;
				
		if (performSearch(completeURL)) {
			retrieveSearchResults(currentPage);
			displayResults();
			createNavigationBar();
			// Adding JPanel which contains movies found as search results
			// and a navigation bar at the bottom.
			search.add(movies, BorderLayout.CENTER);
			search.add(navigationBar, BorderLayout.SOUTH);
		} else {
			JLabel noResults = new JLabel("No results were found.");
			noResults.setFont(MyFont.LARGE_TEXT_BOLD);
			noResults.setForeground(Color.RED);
			noResults.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			search.add(noResults, BorderLayout.NORTH);
		}
		
		this.add(search, SEARCH_CARD);
		CardLayout c1 = (CardLayout) (this.getLayout());
		c1.show(this, SEARCH_CARD);

	}
	
	public SearchPanel(URL url){
		this.setLayout(new CardLayout());
		search = new JPanel();
		search.setLayout(new BorderLayout());
		movies = new JPanel();
		movies.setLayout(new CardLayout());
		
		this.searchResults = new ArrayList<Item>();

		if (performSearch(url.toString())) {
			retrieveSearchResults(currentPage);
			displayResults();
			createNavigationBar();
			// Adding JPanel which contains movies found as search results
			// and a navigation bar at the bottom.
			search.add(movies, BorderLayout.CENTER);
			search.add(navigationBar, BorderLayout.SOUTH);
		} else {
			
			JLabel noResults = new JLabel("No results were found.");
			noResults.setFont(MyFont.LARGE_TEXT_BOLD);
			noResults.setForeground(Color.RED);
			noResults.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			search.add(noResults, BorderLayout.NORTH);
		}
		
		this.add(search, SEARCH_CARD);
		CardLayout c1 = (CardLayout) (this.getLayout());
		c1.show(this, SEARCH_CARD);
	}

	private boolean performSearch(String url) {
		
		// Check if search term was empty return false
		if (url.equals(BEG_URL + "+" + LIMIT_SEARCH_TO_JONES + END_URL) || 
				url.equals(BEG_URL + LIMIT_SEARCH_TO_JONES + END_URL))
			return false;
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Check to see if there is an error message
		if (doc.select(".errormessage").size() > 0)
			return false; // if there is return false
		else {
			// If there isn't an error message check if there is one results or many results.
			String[] entriesFoundTokens = doc.select("i").text().split(" ");			
						
			if (!entriesFoundTokens[0].equals("") && Integer.parseInt(entriesFoundTokens[0]) == 1) {
				// If only one result then the search url is the url of the only search results and 
				// therefore we have to create an Elements object with the appropriate information.
				links = new Elements();
				Attributes attr = new Attributes();
				attr.put("href", url);
				links.add(new Element(Tag.valueOf("a"), HOMEPAGE_URL, attr));
			} else {
				// If there are many results then retrieve all the links of the items
				links = doc.select("span.briefcitTitle");

				totalPages = (int) Math.ceil(links.size() / 4.0);
			}
			return true;
		}
	}

	private void retrieveSearchResults(int page) {
		searchResults = new ArrayList<Item>();

		// If there is only one link, the link is formatted differently 
		// and once the link is displayed the method can return.
		if (links.size() == 1) {
			String url = links.get(0).select("a[href]").attr("href").toString();
			searchResults.add(new Item(url));
		} else {
			// Else get the next four links.
			int start = (page * 4) - 4;
			int end = (page * 4 > links.size()) ? links.size() - 1 : page * 4 - 1;

			// only gets the urls of the elements specified
			for (int i = start; i <= end; i++) {

				Element link = links.get(i);

				// Retrieves url of page
				String url = HOMEPAGE_URL.concat(link.select("a[href]")
						.attr("href").toString());

				searchResults.add(new Item(url));
			}
		}
	}


	private void displayResults() {
		JPanel card = new JPanel();
		card.setLayout(new GridLayout(2, 2));

		// For each search result create a BriefItemPanel and add it to 
		// the JPanel.
		for (Item a : searchResults) {
			
			BriefItemPanel b = new BriefItemPanel(a);
			b.addMouseListener(this);
			card.add(b);
		}
		
		// If there are less than four items displayed add some glue 
		// in order to space out the elements correctly.
		if (searchResults.size() < 4){
			for (int i = 4-searchResults.size(); i > 0; i--)
				card.add(Box.createHorizontalGlue());
		}
		
		// Adds the Panel created as the next card
		movies.add(card, CARD_PREFIX + currentPage);

		// Flips to that card.
		CardLayout cl = (CardLayout) (movies.getLayout());
		cl.show(movies, CARD_PREFIX + currentPage);
	}

	/*
	 * Creates navigation bar at the bottom of the screen.
	 */
	private void createNavigationBar() {
		// Creating navigation bar at the bottom of the screen
		navigationBar = new JPanel();
		navigationBar.setLayout(new BoxLayout(navigationBar, BoxLayout.X_AXIS));
		navigationBar
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Creating the next button with image.
		previousResults = new JButton(new ImageIcon(getClass().getResource(
				"Previous.png")));
		previousResults.setBorder(BorderFactory.createEmptyBorder());
		previousResults.setContentAreaFilled(false);
		previousResults.addActionListener(this);

		// Label which displays the number of pages.
		numberOfPages = new JLabel("Page 1 of " + totalPages);

		// Creating the previous button with image.
		moreResults = new JButton(new ImageIcon(getClass().getResource(
				"More.png")));
		moreResults.setBorder(BorderFactory.createEmptyBorder());
		moreResults.setContentAreaFilled(false);
		moreResults.addActionListener(this);

		navigationBar.add(previousResults);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(numberOfPages);
		navigationBar.add(Box.createHorizontalGlue());
		navigationBar.add(moreResults);
	}

	/*
	 * Listens for the more, previous or back button to be clicked. 
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == moreResults) && (currentPage >= 1)
				&& (currentPage < totalPages)) {
			// If the next arrow is clicked and its not the last page, load and display
			// the next page.
			currentPage++;
			retrieveSearchResults(currentPage);
			displayResults();
			numberOfPages.setText("Page " + currentPage + " of " + totalPages);
		}

		else if ((e.getSource() == previousResults) && (currentPage > 1)) {
			// If the previous button is clicked and its  not the first page, display
			// the previous page.
			currentPage--;
			CardLayout cl = (CardLayout) (movies.getLayout());
			cl.show(movies, CARD_PREFIX + currentPage);

			numberOfPages.setText("Page " + currentPage + " of " + totalPages);
		}
		else if ((e.getSource() == back)){
			// If the back button is hit (only applicable in the VERBOSE_CARD) 
			// then flip to the search card.
			CardLayout c1 = (CardLayout) (this.getLayout());
			c1.show(this, SEARCH_CARD);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().getClass() == BriefItemPanel.class){
			
			BriefItemPanel panelClicked = (BriefItemPanel) e.getSource();
			
			back = new JButton(new ImageIcon(getClass().getResource("Back_Arrow.png")));
			back.setBorder(BorderFactory.createEmptyBorder());
			back.setContentAreaFilled(false);
			back.addActionListener(this);

			JPanel verbosePanel = new JPanel();
			verbosePanel.add(back);
			verbosePanel.add(new VerboseItemPanel(panelClicked.getItem()));
			
			this.add(verbosePanel, VERBOSE_CARD);
			
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, VERBOSE_CARD);
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
