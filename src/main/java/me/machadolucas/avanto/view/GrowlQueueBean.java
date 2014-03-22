package me.machadolucas.avanto.view;

import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GrowlQueueBean {

	public static List<FacesMessage> queue = new LinkedList<FacesMessage>();

	/**
	 * @return the queue
	 */
	public List<FacesMessage> getQueue() {
		return queue;
	}

	/**
	 * @param queue
	 *            the queue to set
	 */
	public void setQueue(List<FacesMessage> queue) {
		GrowlQueueBean.queue = queue;
	}

	public void checkQueueAndDisplay() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			while (queue.size() > 0) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, queue.get(0));
				queue.remove(0);
			}
		}
	}

}
