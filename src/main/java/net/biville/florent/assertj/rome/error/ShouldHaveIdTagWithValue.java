package net.biville.florent.assertj.rome.error;

import com.sun.syndication.feed.synd.SyndEntry;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.internal.StandardComparisonStrategy;

import static java.lang.String.format;

public class ShouldHaveIdTagWithValue extends BasicErrorMessageFactory{


	private ShouldHaveIdTagWithValue(SyndEntry entry, String tagName, String value) {
		super("\nExpecting entry :\n%s \nto have the following tag name: <%s>\n and the following value: <%s>",
				asIdTag(entry),
				tagName,
				value,
				StandardComparisonStrategy.instance());
	}

	public static ShouldHaveIdTagWithValue shouldHaveIdTagWithValue(SyndEntry entry, String tagName, String value){
		return new ShouldHaveIdTagWithValue(entry,tagName, value);
	}

	private static String asIdTag(SyndEntry entry) {
		return (entry.getUri() != null) ? format("<id>%s</id>", entry.getUri()) : "";
	}
}
