import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatalogItem {

	private String title;
	private String callNumberString;
	private String status;
	private int jonesAccesionNum; // -1 if item is not a part of the Jones
									// VHS/DVD collection
	private String typeOfMedia;
	private String summary;
	private String url;
	private String imgLocation;

	/*
	 * Using the catalog link information is retrieved and stored in this
	 * object.
	 * 
	 * @param link Catalog link
	 */
	public CatalogItem(String link) {
		this.url = link;
		// If there isn't a problem loading the information
		// then get the image that corresponds with the item.
		if (loadInformation())
			findImageLocation();
	}

	/*
	 * Using the library catalog webpage associated with this item the following
	 * information is saved in instance variables: movie title, summary of
	 * movies, type of media, call number, Jones accession number (if
	 * applicable) and the status of the item.
	 * 
	 * @return true if there weren't any problems loading the information
	 */
	private boolean loadInformation() {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Retrieving title of movie.
		Elements title = doc
				.select("td.bibInfoLabel:matches(Title):not(:contains(Alternate)):not(:contains(Uniform)) + td.bibInfoData");

		// Split the string in order to just get the name of the movie and not
		// the rest of the information attached with the title.
		String splitTitleString[] = title.text().split("\\[");
		this.title = splitTitleString[0];

		// Retrieving summary.
		Elements summary = doc
				.select("td.bibInfoLabel:matches(Summary) + td.bibInfoData");
		this.summary = summary.text();

		// Retrieves the table on the webpage that displays the type of media,
		// accession number and availability.
		Elements table = doc.select("tr.bibItemsEntry");

		// Retrieves each row of table and saves the information.
		for (int i = 0; i < table.size(); i++) {
			// Retrieves the html for the row specified
			Element row = table.get(i);

			// Retrieving Type (Jones Media DVD/ Jones Media VHS/ other type of
			// media).
			this.typeOfMedia = row.child(0).text()
					.replace(String.valueOf((char) 160), " ").trim();

			// Retrieving Accession Number.
			this.callNumberString = row.child(1).ownText()
					.replace(String.valueOf((char) 160), " ").trim();

			// Retrieving Status.
			this.status = row.child(2).text()
					.replace(String.valueOf((char) 160), " ").trim();
		}

		// If item is part of the Jones Media Collection, then its accession
		// number is retrieved. If it is not part of the collection the
		// accession number is set to -1.
		if (typeOfMedia.equals("Jones Media DVD")
				|| typeOfMedia.equals("Jones Media Video tape")) {
			String[] splitCallNumString = callNumberString.split(" ");
			this.jonesAccesionNum = Integer.parseInt(splitCallNumString[0]);
		} else
			this.jonesAccesionNum = -1;

		return true;
	}

	/*
	 * This method attempts to find a movie cover for the item if it a part of
	 * the Jones collection. 
	 * 
	 * If there isn't an image available and it's a part of the collection a 
	 * default image is put in its place. If it isn't a part of the collection 
	 * an image that displays that it isn't part of the collection is used.
	 */
	private void findImageLocation() {

		this.imgLocation = "";

		// Getting image location (can be a url or a file location)
		if (typeOfMedia.equals("Jones Media DVD")) {
			File f = new File(Globals.BEG_DVD_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				this.imgLocation = Globals.BEG_DVD_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg";
			} else
				this.imgLocation = Globals.DEFAULT_IMG_PATH;

		} else if (typeOfMedia.equals("Jones Media Video tape")) {
			File f = new File(Globals.BEG_VHS_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				this.imgLocation = Globals.BEG_VHS_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg";
			} else
				this.imgLocation = Globals.DEFAULT_IMG_PATH;

		} else
			this.imgLocation = Globals.NOT_AVAILABLE_AT_JONES;

	}

	/*
	 * Returns image location.
	 * 
	 * @return String URI of image location.
	 */
	public String getImgLocation() {
		return this.imgLocation;
	}

	/*
	 * Returns title of movie.
	 * 
	 * @return String title of movie
	 */
	public String getTitle() {
		return this.title;
	}

	/*
	 * Returns type of media (Jones Media DVD, Jones Media Video tape, Paddock DVD, etc).
	 * 
	 * @return String type of library item
	 */
	public String getType() {
		return this.typeOfMedia;
	}

	/*
	 * Returns the items call number.
	 * 
	 * @return string call number of item
	 */
	public String getCallNumberString() {
		return this.callNumberString;
	}

	/*
	 * Returns the status of the item.
	 * 
	 * @return String status of item
	 */
	public String getStatus() {
		return status;
	}

	public String getSummary() {
		return summary;
	}
}
