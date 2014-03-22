package me.machadolucas.avanto.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.WebAppUserDAO;
import me.machadolucas.avanto.entities.WebAppUser;
import me.machadolucas.avanto.util.MessageUtils;
import me.machadolucas.avanto.util.PasswordHash;

/**
 * @author machadolucas
 * 
 */
@ManagedBean
@SessionScoped
public class WebAppUserBean extends AbstractEntityBean<WebAppUser> {

	private static final long serialVersionUID = 3960687977045277086L;

	private static final String mainOutcome = "main?faces-redirect=true";
	private static final String editOutcome = "/admin/webappuser/edit.jsf?faces-redirect=true";

	private List<WebAppUser> filteredList = new ArrayList<WebAppUser>();

	private static WebAppUserDAO webAppUserDAO;
	private String oldPassword;
	private String newPassword;

	@Override
	void doInit() {
		webAppUserDAO = new WebAppUserDAO(WebAppUser.class,
				MorphiaDatastore.getDatabaseObject());
		this.list = webAppUserDAO.find().asList();
	}

	@Override
	String doAdd() {
		this.bean = new WebAppUser();
		return NavigationOutcomeTypes.ADD;
	}

	@Override
	String doEdit() {
		return editOutcome;
	}

	@Override
	String doSave() {
		try {
			bean.setPassword(PasswordHash.createHash(newPassword));
		} catch (Exception e) {
			e.printStackTrace();
		}
		webAppUserDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	String doUpdate() {
		if (oldPassword != null && oldPassword.length() > 0) {
			try {
				if (PasswordHash.validatePassword(oldPassword,
						bean.getPassword())) {
					if (newPassword != null && newPassword.length() > 0) {
						this.bean.setPassword(PasswordHash
								.createHash(newPassword));
					}
				} else {
					MessageUtils
							.showErrorInGrowl("Senha incorreta",
									"A senha informada como senha atual está incorreta. Por favor, verifique.");
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		webAppUserDAO.save(bean);
		reset();
		return mainOutcome;
	}

	@Override
	void doDelete() {
		webAppUserDAO.delete(bean);
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
		this.list = webAppUserDAO.find().asList();
		this.bean = new WebAppUser();

	}

	/**
	 * Método utilizado para o carregamento da tela de edição para o usuário
	 * logado.
	 * 
	 * @return o {@link String} que representa o <i>outcome</i> para a regra de
	 *         navegação definida em <a>/WEB-INF/faces-config.xml</a>
	 * @throws Exception
	 *             caso o carregamento da tela de edição falhe
	 */
	public String editCurrentUser() throws Exception {

		doInit();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		LoginBean loginHelper = (LoginBean) session.getAttribute("loginBean");

		bean = loginHelper.getCurrent();

		return edit();
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the filteredList
	 */
	public List<WebAppUser> getFilteredList() {
		return filteredList;
	}

	/**
	 * @param filteredList
	 *            the filteredList to set
	 */
	public void setFilteredList(List<WebAppUser> filteredList) {
		this.filteredList = filteredList;
	}

}
