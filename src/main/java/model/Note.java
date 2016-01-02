package model;
import org.joda.time.DateTime;


public class Note {
	
	private String name; 
	private DateTime createdAt;
	private int size;
	private boolean toDelete;
	
	public boolean isToDelete() {
		return toDelete;
	}

	public void setToDelete(boolean toDelete) {
		this.toDelete = toDelete;
	}

	public Note(String name,  int size) {
		this.name=name;
		this.size=size;
		this.createdAt = DateTime.now();
	}
	
	public Note() {
		this.createdAt = DateTime.now();
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
	
	
}
