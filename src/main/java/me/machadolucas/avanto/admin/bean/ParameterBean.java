package me.machadolucas.avanto.admin.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import me.machadolucas.avanto.constants.SystemParam;
import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.ParameterDAO;
import me.machadolucas.avanto.entities.Parameter;
import me.machadolucas.avanto.util.MessageUtils;

import org.primefaces.event.SlideEndEvent;

/**
 * @author machadolucas
 * 
 */
@ManagedBean
@ApplicationScoped
public class ParameterBean extends AbstractEntityBean<Parameter> {

	private static final long serialVersionUID = 3960687977045277086L;

	private static final String mainOutcome = "/admin/parameter/main.jsf?faces-redirect=true";

	private List<Parameter> filteredList = new ArrayList<Parameter>();

	private static ParameterDAO parameterDAO;

	public String getValueFromName(String name) {
		Parameter query = new Parameter();
		query.setName(name);

		int index = this.list.indexOf(query);
		if (index != -1) {
			return this.list.get(index).getValue();
		}
		return null;
	}

	private int POST_EDIT_AUTOSAVE_TIME;

	public void updateAutosaveTime(SlideEndEvent event) {
		POST_EDIT_AUTOSAVE_TIME = event.getValue();
		Parameter query = new Parameter();

		query.setName(SystemParam.POST_EDIT_AUTOSAVE_TIME.toString());
		int index = this.list.indexOf(query);
		this.list.get(index).setValue(Integer.toString(event.getValue()));
	}

	@Override
	void doInit() {
		parameterDAO = new ParameterDAO(Parameter.class,
				MorphiaDatastore.getDatabaseObject());
		this.list = parameterDAO.find().asList();

		Parameter query = new Parameter();

		query.setName(SystemParam.POST_EDIT_AUTOSAVE_TIME.toString());
		int index = this.list.indexOf(query);
		if (index != -1) {
			POST_EDIT_AUTOSAVE_TIME = Integer.parseInt(this.list.get(index)
					.getValue());
		}
	}

	@Override
	String doAdd() {
		this.bean = new Parameter();
		return NavigationOutcomeTypes.ADD;
	}

	@Override
	String doEdit() {
		return NavigationOutcomeTypes.EDIT;
	}

	@Override
	String doSave() {
		parameterDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	String doUpdate() {
		parameterDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	void doDelete() {
		if (!SystemParam.contains(bean.getName())) {
			parameterDAO.delete(bean);
			reset();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("growl", MessageUtils.createMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("operation_notpossible_title"),
					bundle.getString("operation_notpossible_message")));
		}
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
		this.list = parameterDAO.find().asList();
		this.bean = new Parameter();

	}

	/**
	 * @return the pOST_EDIT_AUTOSAVE_TIME
	 */
	public int getPOST_EDIT_AUTOSAVE_TIME() {
		return POST_EDIT_AUTOSAVE_TIME;
	}

	/**
	 * @param pOST_EDIT_AUTOSAVE_TIME
	 *            the pOST_EDIT_AUTOSAVE_TIME to set
	 */
	public void setPOST_EDIT_AUTOSAVE_TIME(int pOST_EDIT_AUTOSAVE_TIME) {
		POST_EDIT_AUTOSAVE_TIME = pOST_EDIT_AUTOSAVE_TIME;
	}

	/**
	 * @return the bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @param bundle
	 *            the bundle to set
	 */
	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the filteredList
	 */
	public List<Parameter> getFilteredList() {
		return filteredList;
	}

	/**
	 * @param filteredList
	 *            the filteredList to set
	 */
	public void setFilteredList(List<Parameter> filteredList) {
		this.filteredList = filteredList;
	}

}
