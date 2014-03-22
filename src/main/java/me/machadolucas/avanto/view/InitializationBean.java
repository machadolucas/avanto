package me.machadolucas.avanto.view;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class InitializationBean extends AbstractView {

	private static final long serialVersionUID = 1499687615522628664L;

	private static boolean initialized;

	@Override
	void doInit() {
	}

	/**
	 * @return the initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * @param initialized
	 *            the initialized to set
	 */
	public void setInitialized(boolean initialized) {
		InitializationBean.initialized = initialized;
	}

}
