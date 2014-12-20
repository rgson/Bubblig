package se.rgson.da401a.bubblig.model.bubbla;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Support class for deserializing the RSS feed.
 */
@Root(name = "channel", strict = false)
class BubblaChannel {

	@ElementList(name = "item", inline = true)
	private List<BubblaArticle> articles;

	public List<BubblaArticle> getArticles() {
		return articles;
	}

}