package me.machadolucas.avanto.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

/**
 * @author machadolucas
 *
 */
/**
 * @author machadolucas
 * 
 */
@XmlRootElement
@Entity("posts")
public class Post extends AbstractEntity {

	private static final long serialVersionUID = 6550612743777387735L;

	@Id
	private ObjectId id;

	private String locale = new String();
	private String identificator = new String();
	private String title = new String();
	private String content = new String();

	private List<String> tags = new LinkedList<String>();
	private Date date = new Date();
	private String status = new String();

	/**
	 * If the post is a post about a serie of other posts - a set
	 */
	private boolean serie;

	@Reference
	private List<Post> seriePosts = new ArrayList<Post>();

	@Reference
	private Author author = new Author();

	@Transient
	private static final String contextUrl = FacesContext.getCurrentInstance()
			.getExternalContext().getRequestContextPath();

	public String getUrl() {
		return contextUrl + "/post/" + identificator + "/";
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the identificator
	 */
	public String getIdentificator() {
		return identificator;
	}

	/**
	 * @param identificator
	 *            the identificator to set
	 */
	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	/**
	 * @return the content
	 */
	public String getResumedContent() {
		int amountOfChars = 300;
		if (content.length() < 300) {
			amountOfChars = content.length();
		}
		return content.substring(0, amountOfChars);

		// TODO Analyze for HTML tags
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identificator == null) ? 0 : identificator.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (identificator == null) {
			if (other.identificator != null)
				return false;
		} else if (!identificator.equals(other.identificator))
			return false;
		return true;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		if (author == null) {
			author = new Author();
		}
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * @return the serie
	 */
	public boolean isSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(boolean serie) {
		this.serie = serie;
	}

	/**
	 * @return the seriePosts
	 */
	public List<Post> getSeriePosts() {
		return seriePosts;
	}

	/**
	 * @param seriePosts
	 *            the seriePosts to set
	 */
	public void setSeriePosts(List<Post> seriePosts) {
		this.seriePosts = seriePosts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Post [identificator=" + identificator + "]";
	}

}
