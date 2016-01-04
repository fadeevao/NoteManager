package app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


@Entity
@Table(name = "note")
public class Note implements Serializable {
	
	private static final long serialVersionUID = -6886085521802906026L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name="name")
	private String name; 
	
    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;
	

	@Column(name="size")
	private int size;
	
	@Column(name="content")
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
	}
	
}
