package me.machadolucas.avanto.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.AuthorDAO;
import me.machadolucas.avanto.db.dao.PostDAO;
import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.util.SlugGenerator;

/**
 * @author machadolucas
 * 
 */
@ManagedBean
@SessionScoped
public class AuthorBean extends AbstractEntityBean<Author> {

	private static final long serialVersionUID = 3960687977045277086L;

	private static final String mainOutcome = "/admin/author/main.jsf?faces-redirect=true";

	private List<Author> filteredList = new ArrayList<Author>();

	private static AuthorDAO authorDAO;
	private static PostDAO postDAO;

	@Override
	void doInit() {
		authorDAO = new AuthorDAO(Author.class,
				MorphiaDatastore.getDatabaseObject());
		postDAO = new PostDAO(Post.class, MorphiaDatastore.getDatabaseObject());
		this.list = authorDAO.find().asList();
	}

	@Override
	String doAdd() {
		this.bean = new Author();
		return NavigationOutcomeTypes.ADD;
	}

	@Override
	String doEdit() {
		return NavigationOutcomeTypes.EDIT;
	}

	@Override
	String doSave() {
		bean.setIdentificator(SlugGenerator.slugify(bean.getName()));
		authorDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	String doUpdate() {
		authorDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	void doDelete() {
		postDAO.deleteAuthorReferences(bean);
		authorDAO.delete(bean);
		reset();
	}

	@Override
	public String cancel() {
		reset();
		return mainOutcome;
	}

	@Override
	public String loadMain() {
		reset();
		return mainOutcome;
	}

	@Override
	public void reset() {
		this.list = authorDAO.find().asList();
		this.bean = new Author();

	}

	/**
	 * @return the filteredList
	 */
	public List<Author> getFilteredList() {
		return filteredList;
	}

	/**
	 * @param filteredList
	 *            the filteredList to set
	 */
	public void setFilteredList(List<Author> filteredList) {
		this.filteredList = filteredList;
	}

}
