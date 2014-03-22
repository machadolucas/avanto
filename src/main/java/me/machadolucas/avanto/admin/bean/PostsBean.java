package me.machadolucas.avanto.admin.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import me.machadolucas.avanto.constants.SystemParam;
import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.AuthorDAO;
import me.machadolucas.avanto.db.dao.PostDAO;
import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.util.MessageUtils;
import me.machadolucas.avanto.util.SlugGenerator;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

/**
 * @author lucas.machado
 * 
 */
@ManagedBean
@SessionScoped
public class PostsBean extends AbstractEntityBean<Post> {

	private static final long serialVersionUID = 8483148704399392487L;

	private static final String mainOutcome = "/admin/posts/main.jsf?faces-redirect=true";

	private List<Post> filteredList = new ArrayList<Post>();

	private static PostDAO postDAO;
	private static AuthorDAO authorDAO;

	@ManagedProperty(value = "#{parameterBean}")
	private ParameterBean parameterBean;

	private DualListModel<Post> postseries = new DualListModel<Post>();

	private List<Author> authors;
	private boolean autosaving;
	private boolean updateSlug;

	private StreamedContent exportedXML;

	private void reloadDualModelPostSeries() {
		postseries.setSource(list);
		if (bean.isSerie()) {
			postseries.setTarget(bean.getSeriePosts());
			List<Post> filtering = new ArrayList<>(postseries.getSource());
			filtering.removeAll(bean.getSeriePosts());
			postseries.setSource(filtering);
		} else {
			postseries.setTarget(new ArrayList<Post>());
		}
	}

	public void autosave() {
		if (bean.isSerie()) {
			bean.setSeriePosts(postseries.getTarget());
		}
		postDAO.save(bean);
	}

	public void enableAutosave() {
		autosaving = true;
		MessageUtils.showInfoInForm("Info",
				bundle.getString("admin_posts_autosave_on"));
	}

	public void disableAutosave() {
		autosaving = false;
		MessageUtils.showInfoInForm("Info",
				bundle.getString("admin_posts_autosave_off"));
	}

	public String getAutosaveTime() {
		String result = parameterBean
				.getValueFromName(SystemParam.POST_EDIT_AUTOSAVE_TIME
						.toString());
		if (result == null) {
			return "30";
		}
		return result;
	}

	@Override
	void doInit() {
		postDAO = new PostDAO(Post.class, MorphiaDatastore.getDatabaseObject());
		authorDAO = new AuthorDAO(Author.class,
				MorphiaDatastore.getDatabaseObject());

		this.list = postDAO.find().asList();
		this.authors = authorDAO.find().asList();
	}

	@Override
	String doAdd() {
		this.bean = new Post();
		reloadDualModelPostSeries();
		return NavigationOutcomeTypes.ADD;
	}

	@Override
	String doEdit() {
		reloadDualModelPostSeries();
		return NavigationOutcomeTypes.EDIT;
	}

	public void importXML(FileUploadEvent event) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Post.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			bean = (Post) jaxbUnmarshaller.unmarshal(event.getFile()
					.getInputstream());

			bean.setAuthor(null);
			bean.setStatus("draft");
			System.out.println("Saving imported entity from XML:"
					+ bean.getIdentificator());
			postDAO.save(bean);
			reset();
		} catch (JAXBException e) {
			System.err.println("=== Error parsing XML to entity");
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("=== Error uploading XML file");
			e.printStackTrace();
		}

	}

	public void exportXML() {
		if (this.bean == null || this.bean.getIdentificator() == null
				|| this.bean.getIdentificator().equals("")) {
			MessageUtils.showWarnInForm(bundle.getString("warning_title"),
					bundle.getString("export_selection_required_message"));
		} else {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Post.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				jaxbMarshaller.marshal(bean, output);
			} catch (Exception e) {

			}
			ByteArrayInputStream input = new ByteArrayInputStream(
					output.toByteArray());
			exportedXML = new DefaultStreamedContent(input, "application/xml",
					bean.getIdentificator() + ".xml");
		}
	}

	@Override
	String doSave() {
		bean.setIdentificator(SlugGenerator.slugify(bean.getTitle()));
		if (bean.isSerie()) {
			bean.setSeriePosts(postseries.getTarget());
		}
		postDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	String doUpdate() {
		if (updateSlug) {
			bean.setIdentificator(SlugGenerator.slugify(bean.getTitle()));
		}
		if (bean.isSerie()) {
			bean.setSeriePosts(postseries.getTarget());
		}
		postDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	void doDelete() {
		postDAO.delete(bean);
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
		this.list = postDAO.find().asList();
		this.bean = new Post();
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	/**
	 * @return the autosaving
	 */
	public boolean isAutosaving() {
		return autosaving;
	}

	/**
	 * @param autosaving
	 *            the autosaving to set
	 */
	public void setAutosaving(boolean autosaving) {
		this.autosaving = autosaving;
	}

	/**
	 * @return the updateSlug
	 */
	public boolean isUpdateSlug() {
		return updateSlug;
	}

	/**
	 * @param updateSlug
	 *            the updateSlug to set
	 */
	public void setUpdateSlug(boolean updateSlug) {
		this.updateSlug = updateSlug;
	}

	public StreamedContent getExportedXML() {
		return exportedXML;
	}

	public void setExportedXML(StreamedContent exportedXML) {
		this.exportedXML = exportedXML;
	}

	/**
	 * @return the parameterBean
	 */
	public ParameterBean getParameterBean() {
		return parameterBean;
	}

	/**
	 * @param parameterBean
	 *            the parameterBean to set
	 */
	public void setParameterBean(ParameterBean parameterBean) {
		this.parameterBean = parameterBean;
	}

	/**
	 * @return the postseries
	 */
	public DualListModel<Post> getPostseries() {
		return postseries;
	}

	/**
	 * @param postseries
	 *            the postseries to set
	 */
	public void setPostseries(DualListModel<Post> postseries) {
		this.postseries = postseries;
	}

	/**
	 * @return the filteredList
	 */
	public List<Post> getFilteredList() {
		return filteredList;
	}

	/**
	 * @param filteredList
	 *            the filteredList to set
	 */
	public void setFilteredList(List<Post> filteredList) {
		this.filteredList = filteredList;
	}

}
