package me.machadolucas.avanto.entities;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Bean de um usuário que está logado no sistema
 * 
 * @author lucas.machado
 * 
 */
@Entity("systemusers")
public class WebAppUser extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1799294098213167723L;

	@Id
	private ObjectId id;

	private String email;
	private String password;
	private String name;

	public WebAppUser() {
	}

	/**
	 * @param email
	 * @param password
	 * @param name
	 */
	public WebAppUser(String email, String password, String name) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WebAppUserBean [username=" + email + ", mailAddress=" + name
				+ "]";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

}