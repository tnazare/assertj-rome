package net.biville.florent.assertj.rome.api.entry;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import static net.biville.florent.assertj.rome.api.Assertions.assertThat;

public class EntryAssertTest_hasId_Test {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void prepare() {
		exception.handleAssertionErrors();
	}

	@Test
	public void succeeds_when_given_id_matches() throws URISyntaxException, IOException, FeedException {
		SyndEntry entry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(entry).hasId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a");
	}

	@Test
	public void fail_when_given_id_doesnt_match() throws URISyntaxException, IOException, FeedException {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting entry :\n" +
				"\"<id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\" \n" +
				"to have the following tag name: <\"id\">\n" +
				" and the following value: <\"urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa7a6846816816\">");
		SyndEntry entry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(entry).hasId("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa7a6846816816");
	}


	private SyndFeed readFeed() throws IOException, FeedException, URISyntaxException {
		try (FileReader fileReader = new FileReader(new File(this.getClass().getResource("/feed.xml").toURI()))) {
			return new SyndFeedInput().build(fileReader);
		}
	}
}
