package app.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
 * Database entity representing logged in user
 */
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = -2404620089244371886L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Size(min=2, max=30, message="Username must be between 2 and 30 characters long")
	@Column
	private String name;

	//password hash stored in db for password check when user next logs in 
	@Column
	private String passwordHash;


	//hardcoded role as there is just one
	@Column
	private String role = "USER";
	
	
	public User() {}

	public User(String name) {
		this.name = name;
	}
	
	public User(String name, String passwordHash) {
		this.name = name;
		this.passwordHash = passwordHash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHash() {
		return passwordHash;
	} 

	public void setHash(String hash) {
		this.passwordHash = hash;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return new EqualsBuilder()
				.append(id, user.id)
				.append(name, user.name)
				.append(passwordHash, user.passwordHash)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(name)
				.append(passwordHash)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "User with a name:" + name;
	}


}
