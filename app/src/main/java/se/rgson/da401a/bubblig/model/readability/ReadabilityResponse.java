package se.rgson.da401a.bubblig.model.readability;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class for deserializing Readablity API responses.
 */
@Root(name = "response", strict = false)
public class ReadabilityResponse {

	@Element(name = "title")
	private String title;

	@Element(name = "content")
	private String content;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

}
