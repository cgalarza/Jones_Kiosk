import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatalogItem {

	private String title;
	private String callNumberString;
	private String avalibility;
	private int jonesAccesionNum; // -1 if item is not a part of the Jones
									// VHS/DVD collection
	private String typeOfMedia;
	private String summary;
	private String url;
	private String imgLocation;

	public CatalogItem(String link) {
		this.url = link;
		loadInformation();
		findImageLocation();
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
		// Split the string in order to just get the name of the movie and not
		// the rest of the information attached with the title
		String splitTitleString[] = title.text().split("\\[");
		this.title = splitTitleString[0];

		// Retrieving summary
		Elements summary = doc
				.select("td.bibInfoLabel:matches(Summary) + td.bibInfoData");
		this.summary = summary.text();

		// Retrieves the table on the webpage that displays the type of media,
		// assession number and avalibility
		Elements table = doc.select("tr.bibItemsEntry");

		// Retrieves each row of table and saves information
		for (int i = 0; i < table.size(); i++) {
			// Retrieves the html for the row specified
			Element row = table.get(i);

			// Retrieving Type (Jones Media DVD/ Jones Media VHS/ other type of
			// media)
			this.typeOfMedia = row.child(0).text()
					.replace(String.valueOf((char) 160), " ").trim();

			// Retrieving Accession Number
			this.callNumberString = row.child(1).ownText()
					.replace(String.valueOf((char) 160), " ").trim();

			// Retrieving Status
			this.avalibility = row.child(2).text()
					.replace(String.valueOf((char) 160), " ").trim();
		}

		// Check to see if the item is part of the jones media center
		// collection. If it is get the accesion number
		if (typeOfMedia.equals("Jones Media DVD")
				|| typeOfMedia.equals("Jones Media Video tape")) {
			String[] splitCallNumString = callNumberString.split(" ");
			this.jonesAccesionNum = Integer.parseInt(splitCallNumString[0]);
		} else
			// if it's not set the accession number to -1
			this.jonesAccesionNum = -1;

		return true;

	}

	// This method attempts to find an image for the item if it is a part of the
	// Jones collection
	public void findImageLocation() {

		// Getting image location (can be a url or a file location)
		if (typeOfMedia.equals("Jones Media DVD")) {
			File f = new File("/Users/carlagalarza/Pictures/BoxCovers/DVD/"
					+ Integer.toString(jonesAccesionNum) + ".jpeg");
			if (f.exists()) {
				this.imgLocation = "/Users/carlagalarza/Pictures/BoxCovers/DVD/"
						+ Integer.toString(jonesAccesionNum) + ".jpeg";
			}
			else 
				this.imgLocation = "";
		} else if (typeOfMedia.equals("Jones Media Video tape")) {
			File f = new File("/Users/carlagalarza/Pictures/BoxCovers/VHS/"
					+ Integer.toString(jonesAccesionNum) + ".jpeg");
			if (f.exists()) {
				this.imgLocation = "/Users/carlagalarza/Pictures/BoxCovers/VHS/"
						+ Integer.toString(jonesAccesionNum) + ".jpeg";
			}
			else
				this.imgLocation = "";
		} else
			this.imgLocation = "";

	}

	public String getImgLocation(){
		return this.imgLocation;
	}
	
	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.typeOfMedia;
	}

	public String getCallNumberString() {
		return this.callNumberString;
	}

	public String getStatus() {
		return avalibility;
	}

	public String getSummary() {
		return summary;
	}
}
