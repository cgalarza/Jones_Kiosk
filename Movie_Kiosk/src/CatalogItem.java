
public class CatalogItem {
	
	private String title;
	private int jonesNumber;
	private String avalibility;
	private String typeOfMedia;
	private String description;
	private int year;
	private String url;
	
	public CatalogItem(String title, int jones, String aval, String media, String desc, int y){
		this.title = title;
		this.jonesNumber = jones;
		this.avalibility = aval;
		this.typeOfMedia = media;
		this.description = desc;
		this.year = y;
	}
	
	public CatalogItem(String link){
		this.url = link;
		this.title = getTitle();
	}
	
	public String getTitle(){
		
		
		return title;
		
	}
}
