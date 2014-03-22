package me.machadolucas.avanto.view;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.AuthorDAO;
import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.util.MessageUtils;

@ManagedBean
@RequestScoped
public class IndividualAuthorBean extends AbstractView {

	private static final long serialVersionUID = 1315586204589812573L;

	@ManagedProperty(value = "#{param.id}")
	private String authorId;

	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;

	private Author actual = new Author();

	private List<Post> authorPosts;

	@Override
	void doInit() {

	}

	@PostConstruct
	public void postConstruct() {

		if (authorId != null && authorId.length() > 0) {

			Author p = new Author();
			p.setIdentificator(authorId);

			if (BlogBean.getAuthorsList() == null) {
				AuthorDAO AuthorDAO = new AuthorDAO(Author.class,
						MorphiaDatastore.getDatabaseObject());
				BlogBean.authorsList = AuthorDAO.find().asList();
			}

			int AuthorIndex = BlogBean.getAuthorsList().indexOf(p);
			if (AuthorIndex == -1) {
				try {

					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/");
					GrowlQueueBean.queue.add(MessageUtils.createMessage(
							FacesMessage.SEVERITY_ERROR,
							bundle.getString("author_not_found_title"),
							bundle.getString("author_not_found_message")));
				} catch (IOException e) {
					System.err
							.println("Error redirecting to blog home from unknown Author id "
									+ authorId);
					e.printStackTrace();
				}
			} else {

				actual = BlogBean.getAuthorsList().get(
						BlogBean.getAuthorsList().indexOf(p));

				authorPosts = filterPosts(BlogBean.postsList, actual);
			}
		}
	}

	private List<Post> filterPosts(List<Post> postsList, Author actual2) {
		List<Post> posts = new LinkedList<Post>();

		for (Post p : postsList) {
			if (p.getAuthor().equals(actual2)) {
				posts.add(p);
			}
		}
		return posts;
	}

	/**
	 * @return the actual
	 */
	public Author getActual() {
		return actual;
	}

	/**
	 * @param actual
	 *            the actual to set
	 */
	public void setActual(Author actual) {
		this.actual = actual;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public List<Post> getAuthorPosts() {
		return authorPosts;
	}

	public void setAuthorPosts(List<Post> authorPosts) {
		this.authorPosts = authorPosts;
	}

}
