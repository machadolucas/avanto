package me.machadolucas.avanto.admin.bean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class SystemBean {

	public static MemoryMXBean memoryMXBean = ManagementFactory
			.getMemoryMXBean();

	public static List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory
			.getMemoryPoolMXBeans();

	public static Runtime runtime = Runtime.getRuntime();

	public static File[] roots = File.listRoots();

	public void voide() {

	}

	/**
	 * Get a System property
	 * 
	 * @param key
	 * @return
	 */
	public String sysProp(String key) {
		return System.getProperty(key);
	}

	/**
	 * @return the memoryMXBean
	 */
	public MemoryMXBean getMemoryMXBean() {
		return memoryMXBean;
	}

	/**
	 * @param memoryMXBean
	 *            the memoryMXBean to set
	 */
	public void setMemoryMXBean(MemoryMXBean memoryMXBean) {
		SystemBean.memoryMXBean = memoryMXBean;
	}

	/**
	 * @return the memoryPoolMXBeans
	 */
	public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
		return memoryPoolMXBeans;
	}

	/**
	 * @param memoryPoolMXBeans
	 *            the memoryPoolMXBeans to set
	 */
	public void setMemoryPoolMXBeans(List<MemoryPoolMXBean> memoryPoolMXBeans) {
		SystemBean.memoryPoolMXBeans = memoryPoolMXBeans;
	}

	/**
	 * @return the runtime
	 */
	public Runtime getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime
	 *            the runtime to set
	 */
	public void setRuntime(Runtime runtime) {
		SystemBean.runtime = runtime;
	}

	/**
	 * @return the roots
	 */
	public File[] getRoots() {
		return roots;
	}

	/**
	 * @param roots
	 *            the roots to set
	 */
	public void setRoots(File[] roots) {
		SystemBean.roots = roots;
	}

}
