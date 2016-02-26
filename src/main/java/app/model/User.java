package app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.Pattern;

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
	private long id;
	
	@NotNull
	@Size(min=2, max=30, message="Username must be between 2 and 30 characters long")
	@Column
	private String username;
	
	//not storing password in the db
	@NotNull
	@Size(min=5, max=20, message="Password must be between 5 and 20 characters long")
	@Pattern(regexp="[a-zA-Z0-9]+", message="Password must contain alphanumerical characters")
	@Transient
	private String password;
	
	//salt stored in db for further password check
	@Column
	private String passwordSalt;

	//password hash stored in db for password check when user next logs in 
	@Column
	private String passwordHash;
	
	
	public User() {
	}
	
	public User(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return passwordSalt;
	}

	public void setSalt(String salt) {
		this.passwordSalt = salt;
	}

	public String getHash() {
		return passwordHash;
	} 

	public void setHash(String hash) {
		this.passwordHash = hash;
	}
	
	@Override
	public String toString() {
		return "User with a name:" + username;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		
		User otherUser = (User) obj;
		if (otherUser.getUsername().equals(this.username)) {
			return true;
		}
		return false;
	}
}
