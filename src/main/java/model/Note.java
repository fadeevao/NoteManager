package model;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Note {
	
	private String name; 
	private DateTime createdAt;
	private int size;
	private String content;
	
	public Note(String name,  String content) {
		this.name=name;
		this.content=content;
		this.size=content.length();
		this.createdAt = DateTime.now();
	}
	
	public Note() {
		this.createdAt = DateTime.now();
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getFormattedDate(DateTime dateTime) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy, hh:mm");
		return dateTime.toString(fmt);
		//return dateTime.getHourOfDay()+":"+dateTime.getMinuteOfDay()+" "+dateTime.getDayOfYear()+"/"+dateTime.getMonthOfYear()+"/"+dateTime.getYear();
	}
	

	
}
