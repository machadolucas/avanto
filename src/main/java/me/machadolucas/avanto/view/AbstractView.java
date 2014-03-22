package me.machadolucas.avanto.view;

import java.io.Serializable;

public abstract class AbstractView implements Serializable {

	private static final long serialVersionUID = -307535884400355136L;

	/**
	 * Construtor padro da view
	 */
	public AbstractView() {
		super();
		doInit();
	}

	abstract void doInit();

}
