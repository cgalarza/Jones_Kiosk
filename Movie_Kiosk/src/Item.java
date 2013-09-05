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

	// Paths to Images
	public static final String DEFAULT_IMG_PATH = "Image_Not_Available.png";
	public static final String NOT_AVAILABLE_AT_JONES = "Not_Available_at_Jones.png";
	public static final String ON_RESERVE_AT_JONES = "On_Reserve_at_Jones.png";
	public static final String BEG_DVD_PATH = System.getProperty("user.home") + "/Pictures/DVD_Large_Images/";
	public static final String BEG_VHS_PATH = System.getProperty("user.home") + "/Pictures/VHS/";
	
	private String title;
	private String[] callNumberString;
	private String[] status;
	private int jonesAccesionNum; // -1 if item is not a part of the Jones
									// VHS/DVD collection
	private String[] typeOfMedia;
	private String summary;
	private String url;
	private ImageIcon smallImg; // Height of 265
	private ImageIcon medImg;   // Height of 480
	public Elements labelInfoPairs;

	/*
	 * Using the catalog link information is retrieved and stored in this
	 * object.
	 * 
	 * @param link Catalog link
	 */
	public Item(String link) {
		this.url = link;
		// If there isn't a problem loading the information
		// then get the image that corresponds with the item.
		if (loadInformation())
			smallImg = createImageIcon(265);
	}

	/*
	 * Constructor specifically for items that are going to be displayed 
	 * on the promotional screen. The information for each is pulled from
	 * a text document and then that information is sent to this constructor
	 * in order to create a Item object.
	 * 
	 * @param title Title of movie
	 * 
	 * @param jonesCallNumber Jones accession number
	 * 
	 * @param link url link to the correct catalog record
	 */

	public Item(String title, String jonesCallNumber, String link) {
		
		this.typeOfMedia = new String[1];
		this.callNumberString = new String[1];
		this.status = new String[1];
		this.title = title;
		this.callNumberString[0] = jonesCallNumber;
		this.url = link;
		this.jonesAccesionNum = Integer.parseInt(jonesCallNumber);
		this.typeOfMedia[0] = "Jones Media DVD";
		medImg = createImageIcon(475);
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
				
		// If a table is not empty then retrieve the information
		if (!table.isEmpty()){
			// Retrieves each row of table and saves the information.
			this.typeOfMedia = new String[table.size()];
			this.callNumberString = new String[table.size()];
			this.status = new String[table.size()];
			for (int i = 0; i < table.size(); i++) {
				// Retrieves the html for the row specified
				Element row = table.get(i);
	
				// Retrieving Type (Jones Media DVD/ Jones Media VHS/ other type of
				// media).
				this.typeOfMedia[i] = row.child(0).text()
						.replace(String.valueOf((char) 160), " ").trim();
				// Retrieving Accession Number.
				this.callNumberString[i] = row.child(1).ownText()
						.replace(String.valueOf((char) 160), " ").trim();
	
				// Retrieving Status.
				this.status[i] = row.child(2).text()
						.replace(String.valueOf((char) 160), " ").trim();
			}
		}
		//if it is empty check to see if it is a streamed item
		else {
			Elements streamingTable = doc.select("table.bibLinks th");
			
			this.typeOfMedia = new String[1];
			this.callNumberString = new String[1];
			this.status = new String[1];
			
			
			if (streamingTable.text().equals("Available online:")){
				this.typeOfMedia[0] = "Streaming Video";
				this.callNumberString[0] = "";
				this.status[0] ="Available Online";
			}
			else
				this.typeOfMedia[0] = "";
			
		}
		
		// If item is part of the Jones Media Collection, then its accession
		// number is retrieved. If it is not part of the collection the
		// accession number is set to -1.
		this.jonesAccesionNum = -1;
		for (int i = 0; i < typeOfMedia.length; i++){	
			if (typeOfMedia[i].equals("Jones Media DVD")
				|| typeOfMedia[i].equals("Jones Media Video tape")) {
			String[] splitCallNumString = callNumberString[i].split(" ");
			this.jonesAccesionNum = Integer.parseInt(splitCallNumString[0]);
			}
		}
		
		
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
	private ImageIcon createImageIcon() {

		ImageIcon img;

		// Getting image location (can be a url or a file location)
		if (typeOfMedia[0].equals("Jones Media DVD")) {
			File f = new File(BEG_DVD_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				img = new ImageIcon(BEG_DVD_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg");
			} else
				img = new ImageIcon(getClass().getResource(
						DEFAULT_IMG_PATH));

		} else if (typeOfMedia[0].equals("Jones Media Video tape")) {
			File f = new File(BEG_VHS_PATH
					+ Integer.toString(jonesAccesionNum) + ".jpg");
			if (f.exists()) {
				img = new ImageIcon(BEG_VHS_PATH
						+ Integer.toString(jonesAccesionNum) + ".jpg");

			} else
				img = new ImageIcon(getClass().getResource(
						DEFAULT_IMG_PATH));
		} else if (typeOfMedia[0].equals("On Reserve at Jones Media")) {
			img = new ImageIcon(getClass().getResource(
					ON_RESERVE_AT_JONES));

		} else
			img = new ImageIcon(getClass().getResource(
					NOT_AVAILABLE_AT_JONES));

		return img;
	}
	
	// This method scales the image to the given height.
	private ImageIcon createImageIcon(int height) {
		ImageIcon img = createImageIcon();

		Double newWidth = ((double) height / img.getIconHeight()) * img.getIconWidth();
		ImageIcon resizedImg = 
				new ImageIcon(img.getImage().getScaledInstance(newWidth.intValue(), height, Image.SCALE_SMOOTH));
		
		return resizedImg;
	}
	
	// Method that retrieves all the information on the webpage
	// multi-dimentional array 
	public ArrayList<ArrayList<String>> getAllWebpageInformation(){
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			// return empty array?
		}
		
		System.out.println(doc.select("table.bibDetail table tbody tr"));
		
		labelInfoPairs = doc.select("table.bibDetail table tbody tr");
		
		for (Element tr : labelInfoPairs){
			Elements td = tr.select("td");
		
			ArrayList<String> pair = new ArrayList<String>();
			
			pair.add(td.get(0).text());
			pair.add(td.get(1).text());
			
			data.add(pair);
		}
		
		System.out.println(data.toString());
		
		return data;
	}
	
	
	public ImageIcon getMedImgIcon() {
		if (medImg == null)
			medImg = createImageIcon(475);
			
		return this.medImg;
	}

	/*
	 * Returns imageIcon.
	 * 
	 * @return ImageIcon of the movie
	 */
	public ImageIcon getImgIcon() {
		return this.smallImg;
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
	 * Returns type of media (Jones Media DVD, Jones Media Video tape, Paddock
	 * DVD, etc).
	 * 
	 * @return String type of library item
	 */
	public String[] getType() {
		return this.typeOfMedia;
	}

	/*
	 * Returns the items call number.
	 * 
	 * @return string call number of item
	 */
	public String[] getCallNumberString() {
		return this.callNumberString;
	}

	/*
	 * Returns the status of the item.
	 * 
	 * @return String status of item
	 */
	public String[] getStatus() {
		return this.status;
	}

	public String getSummary() {
		return this.summary;
	}
	
	public int getJonesAccesionNum(){
		return this.jonesAccesionNum;
	}
}
