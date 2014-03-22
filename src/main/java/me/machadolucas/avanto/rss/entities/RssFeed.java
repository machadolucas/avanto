package me.machadolucas.avanto.rss.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RssFeed {
	private RssHeader header;
	private List<RssEntry> entries;

	public void setHeader(RssHeader header) {
		this.header = header;
	}

	public void setEntries(List<RssEntry> entries) {
		this.entries = entries;
	}

	public RssHeader getHeader() {
		return header;
	}

	public List<RssEntry> getEntries() {
		return entries;
	}

	public static String formatDate(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		return sdf.format(cal.getTime());
	}

	public static String formatDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		return sdf.format(cal.getTime());
	}
}
