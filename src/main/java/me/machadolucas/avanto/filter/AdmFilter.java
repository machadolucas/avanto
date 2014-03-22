package me.machadolucas.avanto.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.machadolucas.avanto.admin.bean.LoginBean;

/**
 * Login filter of admin pages
 * 
 * @author lucas.machado
 * 
 */
@WebFilter("/admin/*")
public class AdmFilter implements Serializable, Filter {

	private static final long serialVersionUID = -4949771056732781272L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		LoginBean loginBean = (LoginBean) req.getSession().getAttribute(
				"loginBean");

		if (loginBean == null || !loginBean.isLoggedIn()) {
			// Login
			res.sendRedirect(req.getContextPath() + "/login.jsf");
		} else {
			// Continuar para redirecionamento...
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}