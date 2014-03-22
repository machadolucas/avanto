package me.machadolucas.avanto.admin.bean;

/**
 * Interface que representa os tipos de <i>outcome</i>.
 * 
 */
public interface NavigationOutcomeTypes {

	/**
	 * Representa o <i>outcome</i> de listagem.<br />
	 * Geralmente, associado  pginas <quote>index.xhtml</quote>.
	 */
	public static String DEFAULT = "main";

	/**
	 * Representa o <i>outcome</i> de adio.<br />
	 * Geralmente, associado  pginas <quote>add.xhtml</quote>.
	 */
	public static String ADD = "add";

	/**
	 * Representa o <i>outcome</i> de edio.<br />
	 * Geralmente, associado  pginas <quote>edit.xhtml</quote>.
	 */
	public static String EDIT = "edit";

}