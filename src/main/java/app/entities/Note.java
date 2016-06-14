package app.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/*
 * Entity representing notes that users can create
 */

@Entity
@Table(name = "note")
public class Note implements Serializable {


	private static final long serialVersionUID = -6886085521802906026L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@NotEmpty
	@Size(min=2, max=30, message="Note name must be between 2 and 30 characters long")
	@Column(name="name")
	private String name;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;


	@Column(name="size")
	private int size;

	@NotEmpty
	@Column(name="content")
	private String content;

	@Column(name="user_id")
	private long userId;

	public Note(String name,  String content) {
		this.name=name;
		this.content=content;
		this.size=content.length();
		this.createdAt = DateTime.now();
	}
	public Note(String name,  String content, long userId) {
		this.name=name;
		this.content=content;
		this.size=content.length();
		this.createdAt = DateTime.now();
		this.userId = userId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFormattedDate(DateTime dateTime) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy, hh:mm");
		return dateTime.toString(fmt);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Note note = (Note) o;

		return new EqualsBuilder()
				.append(size, note.size)
				.append(name, note.name)
				.append(content, note.content)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(name)
				.append(size)
				.append(content)
				.toHashCode();
	}
}
