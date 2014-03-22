package me.machadolucas.avanto.admin.bean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;

import me.machadolucas.avanto.entities.AbstractEntity;
import me.machadolucas.avanto.util.MessageUtils;

import org.primefaces.context.RequestContext;

public abstract class AbstractEntityBean<T extends AbstractEntity> implements
		Serializable {

	private static final long serialVersionUID = 6222001013594481011L;

	/**
	 * Construtor padrão da view
	 */
	public AbstractEntityBean() {
		super();
		init();
	}

	@ManagedProperty("#{msg}")
	protected ResourceBundle bundle;

	protected T bean;

	protected List<T> list;

	abstract void doInit();

	/**
	 * Inicializa a view, carregando o que preciso para a tela main
	 */
	private void init() {
		// System.out.println("initializing view");
		doInit();
	}

	abstract String doAdd();

	/**
	 * Prepara e carrega a tela para adição
	 * 
	 * @return outcome para tela de adição
	 */
	public String add() {
		// System.out.println("initializing add page");
		return doAdd();
	}

	abstract String doEdit();

	/**
	 * Prepara e carrega a tela para edição
	 * 
	 * @return outcome para tela de edição
	 */
	public String edit() {
		// System.out.println("initializing edit page");
		String outcome = null;
		try {
			if (this.bean != null) {

				try {
					outcome = doEdit();
					if (outcome == null) {
						outcome = "edit";
					}
				} catch (Exception e) {
					System.out
							.println("doEdit() execution has thrown an exception...");
					throw e;
				}

			} else {
				MessageUtils.showWarnInForm(bundle.getString("warning_title"),
						bundle.getString("edit_selection_required_message"));
			}
		} catch (Throwable throwable) {
			MessageUtils.showFatalInGrowl(bundle.getString("error_title"),
					bundle.getString("unexpected_error_message"));
			throwable.printStackTrace();
		}

		return outcome;
	}

	abstract String doSave();

	/**
	 * Salva um item recém criado
	 * 
	 * @return outcome
	 */
	public String save() {
		System.out.println("trying to insert entity");
		return doSave();
	}

	abstract String doUpdate();

	/**
	 * Atualiza um item após uma edição
	 * 
	 * @return outcome
	 */
	public String update() {
		System.out.println("trying to update entity");
		return doUpdate();
	}

	abstract void doDelete();

	/**
	 * Remove o item do sistema
	 */
	public void delete() {
		System.out.println("trying to delete entity");
		doDelete();
	}

	/**
	 * Executa as lógicas de negócio associadas para o instante do cancelamento
	 * do salvamento da visão.
	 * 
	 * @return o <i>outcome</i> para a regra de navegao
	 */
	public abstract String cancel();

	/**
	 * Executa as lógicas de negócio associadas para o instante do carregamento
	 * da tela principal
	 * 
	 * @return o <i>outcome</i> para a regra de navegao
	 */
	public abstract String loadMain();

	/**
	 * Executa as lógicas de negócio associadas para o instante do carregamento
	 * da tela principal
	 * 
	 * @return o <i>outcome</i> para a regra de navegao
	 */
	public abstract void reset();

	/**
	 * Mostra diálogo de confirmação de exclusão se houver bean selecionado; do
	 * contrário, mostra mensagem de aviso dizendo que a seleção é requerida
	 * 
	 * @param actionEvent
	 */
	public void dialogIfSelected(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean canContinue = false;
		if (this.bean != null) {
			canContinue = true;
		} else {
			MessageUtils.showWarnInForm(bundle.getString("warning_title"),
					bundle.getString("delete_selection_required_message"));
		}

		context.addCallbackParam("canContinue", canContinue);
	}

	/**
	 * Executa código <i>java script</i>.
	 * 
	 * @param script
	 *            o {@link String} que representa o código a ser executado
	 */
	protected static void executeJS(String script) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (requestContext != null) {
			requestContext.execute(script);
		} else {
			System.out.println("could not execute javascript '" + script
					+ "' because the request context is null");
		}
	}

	/**
	 * @return the bean
	 */
	public T getBean() {
		return bean;
	}

	/**
	 * @param bean
	 *            the bean to set
	 */
	public void setBean(T bean) {
		this.bean = bean;
	}

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
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
}
