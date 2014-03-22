package me.machadolucas.avanto.view;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.PostDAO;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.util.MessageUtils;

@ManagedBean
@RequestScoped
public class IndividualPostBean extends AbstractView {

	private static final long serialVersionUID = 1315586204589812573L;

	@ManagedProperty(value = "#{param.id}")
	private String postId;

	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;

	private Post actual = new Post();

	@Override
	void doInit() {

	}

	@PostConstruct
	public void postConstruct() {

		if (postId != null && postId.length() > 0) {

			Post p = new Post();
			p.setIdentificator(postId);

			if (BlogBean.postsList == null) {
				PostDAO postDAO = new PostDAO(Post.class,
						MorphiaDatastore.getDatabaseObject());
				BlogBean.postsList = postDAO.find().asList();
			}
			int postIndex = BlogBean.postsList.indexOf(p);
			if (postIndex == -1) {
				try {

					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/");
					GrowlQueueBean.queue.add(MessageUtils.createMessage(
							FacesMessage.SEVERITY_ERROR,
							bundle.getString("post_not_found_title"),
							bundle.getString("post_not_found_message")));
				} catch (IOException e) {
					System.err
							.println("Error redirecting to blog home from unknown post id "
									+ postId);
					e.printStackTrace();
				}
			} else {

				actual = BlogBean.postsList.get(BlogBean.postsList.indexOf(p));
			}
		}
	}

	/**
	 * @return the postId
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * @return the actual
	 */
	public Post getActual() {
		return actual;
	}

	/**
	 * @param actual
	 *            the actual to set
	 */
	public void setActual(Post actual) {
		this.actual = actual;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}
