package se.rgson.da401a.bubblig.bubbla;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class representing an article from the Bubb.la RSS feed.
 */
@Root(name = "item", strict = false)
public class BubblaArticle {

	@Element(name = "guid")
	private int id;

	@Element(name = "title")
	private String title;

	//@Element(name = "link")
	private String url;

	public int getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getURL() {
		return url;
	}

}
