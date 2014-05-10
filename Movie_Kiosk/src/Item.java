import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Item {

	// Paths to images.
	// TODO: Should be static?
	private final String DEFAULT_IMG_PATH = "resources/Image_Not_Available.png";
	private final String NOT_AVAILABLE_AT_JONES = "resources/Not_Available_at_Jones.png";
	private final String ON_RESERVE_AT_JONES = "resources/On_Reserve_at_Jones.png";
	private final String BEG_DVD_PATH = System.getProperty("user.home") + "/Pictures/DVD/";
	private final String BEG_VHS_PATH = System.getProperty("user.home") + "/Pictures/VHS/";
	
	// Heights for images.
	private final int SMALL_HEIGHT = 300;
	private final int MED_HEIGHT = 600;
	
	private String title, summary, url, language, performer;
	private ArrayList<String> callNumberString, status, typeOfMedia;
	private int jonesAccesionNum; // -1 if item is not a part of the Jones VHS/DVD collection
	private ImageIcon smallImg; // Height of 300
	private ImageIcon medImg;   // Height of 600
	public Elements labelInfoPairs;

	/**
	 * Using the catalog URL, the item's information is retrieved and stored in this object.
	 * 
	 * @param link Catalog url
	 */
	public Item(String link) {
		this.url = link;
		// If there isn't a problem loading the information then get the image that corresponds 
		// with the item.
		if (loadInformation())
			this.smallImg = createImageIcon(SMALL_HEIGHT);
	}

	/**
	 * Constructor specifically for items that are going to be displayed on the promotional screen. 
	 * 
	 * The information for each is pulled from a text document and then that information is sent to 
	 * this constructor in order to create an Item object. NOTE: The title is not retrieved from 
	 * the catalog page, the title given in this constructor is the one used.
	 * 
	 * @param title Title of movie
	 * @param link url link to the correct catalog record
	 */
	public Item(String title, String link) {
		
		this.title = title;
		this.url = link;
		if (loadInformation())
			this.medImg = createImageIcon(MED_HEIGHT);
	}

	/**
	 * Using the library catalog webpage associated with this item the following information is 
	 * saved in instance variables: movie title, summary of movies, type of media, call number, 
	 * Jones accession number (if applicable) and the status of the item.
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

		// If a title has not been set, retrieving the title of the item.
		if (this.title == null) {
			Elements title = doc
				.select("td.bibInfoLabel:matches(Title):not(:contains(Alternate)):not("
						+ ":contains(Uniform)) + td.bibInfoData");

			// Split the string in order to just get the name of the movie and not
			// the rest of the information attached with the title.
			String splitTitleString[] = title.text().split("\\[");
			splitTitleString = splitTitleString[0].split("/");
			this.title = splitTitleString[0];
		}
		// Retrieving summary.
		Elements summary = doc
				.select("td.bibInfoLabel:matches(Summary) + td.bibInfoData");
		this.summary = summary.text();

		// Retrieving language.
		Elements language = doc.select("td.bibInfoLabel:matches(Language) + td.bibInfoData");
		this.language = language.text();
		
		// Retrieving performer.
		Elements performer = doc.select("td.bibInfoLabel:matches(Performer) + td.bibInfoData");
		this.performer = performer.text();
		
		// Retrieves the table on the webpage that displays the type of media, call number and 
		// availability.
		Elements table = doc.select("tr.bibItemsEntry");
				
		// If a table is not empty then retrieve the information
		if (!table.isEmpty()){
			// Retrieves each row of table and saves the information.
			this.typeOfMedia = new ArrayList<String>();
			this.callNumberString = new ArrayList<String>();
			this.status = new ArrayList<String>();
			
			for (int i = 0; i < table.size(); i++) {
				// Retrieves the html for the row specified.
				Element row = table.get(i);
				
				// Retrieving Status.
				String stat = row.child(2).text()
						.replace(String.valueOf((char) 160), " ").trim();
				
				// If the status is "LIBRARY HAS" then we don't want to display it. It's extra 
				// information that's not necessary.
				if (stat.equals("LIBRARY HAS"))
					continue;
				
				this.status.add(stat);
				
				// Retrieving Type (Jones Media DVD/ Jones Media VHS/ other type of media).
				this.typeOfMedia.add(row.child(0).text()
						.replace(String.valueOf((char) 160), " ").trim());
				
				// Retrieving Accession Number.
				this.callNumberString.add(row.child(1).ownText()
						.replace(String.valueOf((char) 160), " ").trim());
			}
		}
		else {
			return false;
		}
		
		// If item is part of the Jones Media Collection, then its accession number is retrieved. 
		// If it is not part of the collection the accession number is set to -1. Have to check 
		// each row because the item might have multiple things associated with it and the first 
		// one listed may not be a Jones Media DVD or VHS.
		this.jonesAccesionNum = -1;
		for (int i = 0; i < typeOfMedia.size(); i++){	
			if (typeOfMedia.get(i).equals("Jones Media DVD")
				|| typeOfMedia.get(i).equals("Jones Media Video tape")) {
			String[] splitCallNumString = callNumberString.get(i).split(" ");
			this.jonesAccesionNum = Integer.parseInt(splitCallNumString[0]);
			}
		}
		
		return true;
	}

	/**
	 * This method attempts to find a movie cover for the item if it a part of the Jones collection.
	 * 
	 * If there isn't an image available and it's a part of the collection a default image is put 
	 * in its place. If it isn't a part of the collection an image that displays that it isn't part 
	 * of the collection is used. The image is also resized to the height given.
	 * 
	 * @param height height the ImageIcon should be when it is returned
	 * @return image returned as an ImageIcon
	 * 
	 */
	private ImageIcon createImageIcon(int height) {
		ImageIcon img;

		// Getting image location.
		if (typeOfMedia.contains("Jones Media DVD")) {
			// If Jones DVD.
			File f = new File(BEG_DVD_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				img = new ImageIcon(BEG_DVD_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg");
			} else
				img = new ImageIcon(getClass().getResource(DEFAULT_IMG_PATH));

		} else if (typeOfMedia.get(0).equals("Jones Media Video tape")) {
			// If Jones VHS.
			File f = new File(BEG_VHS_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				img = new ImageIcon(BEG_VHS_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg");

			} else
				img = new ImageIcon(getClass().getResource(
						DEFAULT_IMG_PATH));
		} else if (typeOfMedia.get(0).equals("On Reserve at Jones Media")) {
			// If on reserve at Jones Media display a different picture.
			img = new ImageIcon(getClass().getResource(
					ON_RESERVE_AT_JONES));

		} else
			// Otherwise not available at Jones.
			img = new ImageIcon(getClass().getResource(
					NOT_AVAILABLE_AT_JONES));

		// Resize image according to the height given. If the height given is equal to the size of 
		// the image don't resize.
		if (height == img.getIconHeight())
			return img;
		else {
			Double newWidth = ((double) height / img.getIconHeight()) * img.getIconWidth();

			ImageIcon resizedImg = 
					new ImageIcon(img.getImage().getScaledInstance(newWidth.intValue(), height, 
							Image.SCALE_SMOOTH));
			return resizedImg;
		}
	}

	/**
	 * Method that retrieves all the information on the catalog web page 
	 * and saves it in a multidimensional ArrayList.
	 * 
	 * @return ArrayList<ArrayList<String>> contains all the information on the catalog web page as 
	 * label, value pairs
	 */
	public ArrayList<ArrayList<String>> getAllWebpageInformation(){
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: return empty array?
		}
				
		labelInfoPairs = doc.select("table.bibDetail table tbody tr");
		
		for (Element tr : labelInfoPairs){
			Elements td = tr.select("td");
		
			ArrayList<String> pair = new ArrayList<String>();
			
			pair.add(td.get(0).text());
			pair.add(td.get(1).text());
			
			data.add(pair);
		}
		
		return data;
	}
	
	/**
	 * Method that returns a medium sized ImageIcon. If the icon has not been previously created, 
	 * it creates it. Therefore the image is only created with necessary.
	 * 
	 * @returns a medium-sized ImageIcon
	 */
	public ImageIcon getMedImgIcon() {
		if (this.medImg == null)
			this.medImg = createImageIcon(MED_HEIGHT);
			
		return this.medImg;
	}

	/**
	 * Returns small ImageIcon.
	 * 
	 * @return small ImageIcon
	 */
	public ImageIcon getImgIcon() {
		if (this.smallImg == null)
			this.smallImg = createImageIcon(SMALL_HEIGHT);
		return this.smallImg;
	}

	/**
	 * Returns title of movie.
	 * 
	 * @return title of movie
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns type of media(s) (Jones Media DVD, Jones Media Video tape, Paddock DVD, etc).
	 * 
	 * @return type(s) of library item
	 */
	public ArrayList<String> getType() {
		return this.typeOfMedia;
	}

	/**
	 * Returns the item's call number or call numbers (if applicable).
	 * 
	 * @return call number(s) of item
	 */
	public ArrayList<String> getCallNumberString() {
		return this.callNumberString;
	}

	/**
	 * Returns the item's status or statuses (if applicable).
	 * 
	 * @return status(es) of item
	 */
	public ArrayList<String> getStatus() {
		return this.status;
	}
	
	/**
	 * Returns item's summary.
	 * 
	 * @returns Summary of item.
	 */
	public String getSummary() {
		return this.summary;
	}
	
	/**
	 * Returns Jones accession number. If not an item at Jones, accession number is set to -1.
	 * 
	 * @returns Jones accession number
	 */
	public int getJonesAccesionNum(){
		return this.jonesAccesionNum;
	}
	/**
	 * Returns the film's language. 
	 * 
	 * @return films language
	 */
	public String getLanguage(){
		return this.language;
	}
	
	/**
	 * Returns performers in film.
	 * 
	 * @return performers in film
	 */
	public String getPerformer(){
		return this.performer;
	}
}
