package se.rgson.da401a.bubblig.model.bubbla;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Support class for deserializing the RSS feed.
 */
@Root(name = "rss", strict = false)
class BubblaRSS {

	@Element(name = "channel")
	private BubblaChannel channel;

	public BubblaChannel getChannel() {
		return channel;
	}

}
