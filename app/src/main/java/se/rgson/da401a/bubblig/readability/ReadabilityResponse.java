package se.rgson.da401a.bubblig.readability;

import android.text.Html;
import android.text.Spanned;

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

	/**
	 * Returns the parsed data as an HTML-formatted Spanned object, for use within a TextView.
	 *
	 * @return The title and content, formatted from HTML.
	 */
	public Spanned getParsedHtml() {
		return Html.fromHtml("<h1>" + title + "</h1>" + content);
	}

}
