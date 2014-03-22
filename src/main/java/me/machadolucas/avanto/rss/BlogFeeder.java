package me.machadolucas.avanto.rss;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.PostDAO;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.rss.entities.RssEntry;
import me.machadolucas.avanto.rss.entities.RssFeed;
import me.machadolucas.avanto.rss.entities.RssHeader;
import me.machadolucas.avanto.view.BlogBean;

public class BlogFeeder {

	@ManagedProperty(value = "#{blogBean}")
	private BlogBean blogBean;

	public String generateFeed(String serverUrl) {
		RssFeed feed = new RssFeed();

		RssHeader header = new RssHeader();
		header.setCopyright("Copyright Lucas Machado");
		header.setTitle("machadolucas blog");
		header.setDescription("Programming, Photography and any other thing");
		header.setLanguage("pt");
		header.setLink("http://blog.machadolucas.me");
		header.setPubDate(RssFeed.formatDate(Calendar.getInstance()));
		feed.setHeader(header);

		ArrayList<RssEntry> entries = new ArrayList<RssEntry>();
		RssEntry entry;

		List<Post> posts;
		if (blogBean != null) {
			posts = blogBean.getLastPosts();
		} else {
			PostDAO postDAO = new PostDAO(Post.class,
					MorphiaDatastore.getDatabaseObject());
			posts = postDAO.findLastPosts(6);
		}
		for (Post post : posts) {
			entry = new RssEntry();
			entry.setTitle(post.getTitle());
			String content = post.getContent();
			if (content.length() > 301) {
				content = content.substring(0, 300).replaceAll("<[^>]*>", " ")
						.replaceAll("<[^>]*", "")
						+ "...";
			} else {
				content = content.replaceAll("<[^>]*>", "") + "...";
			}
			entry.setDescription(content);
			entry.setGuid(serverUrl + post.getUrl());
			entry.setLink(serverUrl + post.getUrl());
			entry.setPubDate(RssFeed.formatDate(post.getDate()));
			entries.add(entry);
		}

		feed.setEntries(entries);

		try {
			ByteArrayOutputStream baos = RssWriter.writeXML(feed);
			return baos.toString("UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=== Error generating RSS feed");
			return "";
		}
	}

	public BlogBean getBlogBean() {
		return blogBean;
	}

	public void setBlogBean(BlogBean blogBean) {
		this.blogBean = blogBean;
	}
}
