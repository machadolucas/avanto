package me.machadolucas.avanto.admin.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.WebAppUserDAO;
import me.machadolucas.avanto.entities.WebAppUser;
import me.machadolucas.avanto.util.PasswordHash;

@ManagedBean
@SessionScoped
public class LoginBean {

	private String email;

	private String password;

	private WebAppUser current;

	private static WebAppUserDAO webAppUserDAO;

	public LoginBean() {
		webAppUserDAO = new WebAppUserDAO(WebAppUser.class,
				MorphiaDatastore.getDatabaseObject());
		if (webAppUserDAO.count() < 1) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.dispatch("admin/init/index.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Realiza login
	 * 
	 * @return outcome
	 */
	public String login() {
		current = matchLogin(email, password);

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		if (current == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Incorrect Password!", "Try Again"));
			password = null;
			System.out.println("Unsuccessful login for user " + email
					+ " from IP address " + ipAddress);
			return (email = password = null);
		}
		System.out.println("Successful login for user " + email
				+ " from IP address " + ipAddress);
		return "/admin/index.jsf?faces-redirect=true";
	}

	private WebAppUser matchLogin(String email, String password) {

		// if (webAppUserDAO.count() < 1) {
		//
		// WebAppUser firstUser;
		// try {
		// firstUser = new WebAppUser(email,
		// PasswordHash.createHash(password), email);
		// webAppUserDAO.save(firstUser);
		// return firstUser;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
		//
		// } else {

		WebAppUser user = webAppUserDAO.findByEmail(email);

		try {
			if (user != null
					&& PasswordHash.validatePassword(password,
							user.getPassword())) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Wrong Password. Cannot authenticate");
		return null;
		// }

	}

	/**
	 * Faz logout do usurio
	 * 
	 * @return
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login.jsf?faces-redirect=true";
	}

	/**
	 * @return true se usurio est logado. Do contrrio, false
	 */
	public boolean isLoggedIn() {
		return current != null;
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
	 * @return the current
	 */
	public WebAppUser getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(WebAppUser current) {
		this.current = current;
	}

}
