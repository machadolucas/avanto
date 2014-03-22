package me.machadolucas.avanto.view;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.AuthorDAO;
import me.machadolucas.avanto.db.dao.PostDAO;
import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Post;

@ManagedBean
@ApplicationScoped
public class BlogBean extends AbstractView {

	private static final int NUMBER_OF_LAST_POSTS = 6;

	private static final long serialVersionUID = -7583636043378780666L;

	public List<Post> filteredPostsList;

	public static List<Author> authorsList;
	public static List<Post> postsList;
	public static List<Post> lastPosts;
	PostDAO postDAO;
	AuthorDAO authorDAO;

	@Override
	void doInit() {
		postDAO = new PostDAO(Post.class, MorphiaDatastore.getDatabaseObject());
		authorDAO = new AuthorDAO(Author.class,
				MorphiaDatastore.getDatabaseObject());

		lastPosts = postDAO.findLastPosts(NUMBER_OF_LAST_POSTS);

		postsList = postDAO.findAllPublishedPosts();
		authorsList = authorDAO.find().asList();
	}

	public void reloadBean() {
		lastPosts = postDAO.findLastPosts(NUMBER_OF_LAST_POSTS);

		postsList = postDAO.findAllPublishedPosts();
		authorsList = authorDAO.find().asList();
	}

	/**
	 * @return the postsList
	 */
	public List<Post> getPostsList() {
		return postsList;
	}

	/**
	 * @param postsList
	 *            the postsList to set
	 */
	public void setPostsList(List<Post> postsList) {
		BlogBean.postsList = postsList;
	}

	/**
	 * @return the lastPosts
	 */
	public List<Post> getLastPosts() {
		return lastPosts;
	}

	/**
	 * @param lastPosts
	 *            the lastPosts to set
	 */
	public void setLastPosts(List<Post> lastPosts) {
		BlogBean.lastPosts = lastPosts;
	}

	/**
	 * @return the authorsList
	 */
	public static List<Author> getAuthorsList() {
		return authorsList;
	}

	/**
	 * @param authorsList
	 *            the authorsList to set
	 */
	public static void setAuthorsList(List<Author> authorsList) {
		BlogBean.authorsList = authorsList;
	}

	/**
	 * @return the authorsList
	 */
	public List<Author> getAuthorsListNonStatic() {
		return authorsList;
	}

	/**
	 * @param authorsList
	 *            the authorsList to set
	 */
	public void setAuthorsListNonStatic(List<Author> authorsList) {
		BlogBean.authorsList = authorsList;
	}

	/**
	 * @return the filteredPostsList
	 */
	public List<Post> getFilteredPostsList() {
		return filteredPostsList;
	}

	/**
	 * @param filteredPostsList
	 *            the filteredPostsList to set
	 */
	public void setFilteredPostsList(List<Post> filteredPostsList) {
		this.filteredPostsList = filteredPostsList;
	}

}
