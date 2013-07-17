import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatalogItem {

	private String title;
	private String assessionString;
	private String avalibility;
	private String typeOfMedia;
	private String summary;
	private int year;
	private String url;

	public CatalogItem(String title, int jones, String aval, String media,
			String desc, int y) {
		this.title = title;
		this.avalibility = aval;
		this.typeOfMedia = media;
		this.summary = desc;
		this.year = y;
	}

	public CatalogItem(String link) {
		this.url = link;
		loadInformation();
	}

	// TO DO: Make sure to return True/False in case there is any problem
	// loading the webpage
	public boolean loadInformation() {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retrieving title of movie
		Elements title = doc
				.select("td.bibInfoLabel:matches(Title):not(:contains(Alternate)):not(:contains(Uniform)) + td.bibInfoData");
		// Split the string in order to just get the name of the movie and not the rest of the information attached with the title
		 String splitTitleString[] = title.text().split("\\[");
		 this.title = splitTitleString[0];

		// Retrieving summary
		Elements summary = doc
				.select("td.bibInfoLabel:matches(Summary) + td.bibInfoData");
		this.summary = summary.text();

		// Retrieves the table on the webpage that displays the type of media, assession number and avalibility
		Elements table = doc.select("tr.bibItemsEntry");
		
		// Retrieves each row of table and saves information
		for (int i = 0; i < table.size(); i++) {
			// Retrieves the html for the row specified
			Element row = table.get(i);
			
			// Retrieving Type (DVD/VHS)
			this.typeOfMedia = row.child(0).text();

			// Retrieving Accession Number
			this.assessionString = row.child(1).ownText();

			// Retrieving Status
			this.avalibility = row.child(2).text();
		}
		
		return true;

	}

	public String getTitle(){
		return this.title;
	}
	
	public String getType(){
		return this.typeOfMedia;
	}
	
	public String getAssessionString(){
		return this.assessionString;
	}
	
	public String getStatus(){
		return avalibility;
	}
	
	public String getSummary(){
		return summary;
	}
}
