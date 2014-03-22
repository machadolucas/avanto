package me.machadolucas.avanto.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.machadolucas.avanto.rss.BlogFeeder;

/**
 * Servlet to output RSS of the blog
 * 
 * @author machadolucas
 * 
 */
@WebServlet("/rss")
public class RssServlet extends HttpServlet {

	private static final long serialVersionUID = 5853566468491878835L;

	private static BlogFeeder blogFeeder = new BlogFeeder();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/rss+xml");
		response.setCharacterEncoding("UTF-8");
		response.setBufferSize(10240);
		PrintWriter out = response.getWriter();

		String rss = blogFeeder.generateFeed(getURL(request));
		if (rss.equals("")) {
			out.println("Error loading RSS feed");
		} else {
			out.println(rss);
		}
		out.close();
	}

	public static String getURL(HttpServletRequest req) {

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80

		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		return url.toString();
	}
}
