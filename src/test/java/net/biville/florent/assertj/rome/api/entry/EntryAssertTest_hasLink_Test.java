/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2014-2014 the original author or authors.
 */
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

public class EntryAssertTest_hasLink_Test {


	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void prepare() {
		exception.handleAssertionErrors();
	}

	@Test
	public void succeeds_when_given_link_matches() throws URISyntaxException, IOException, FeedException {
		SyndEntry entry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(entry).hasLink("http://example.org/2003/12/13/atom03", "text/html", "alternate", "Robots");
	}

	@Test
	public void fails_on_null_entry() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting actual not to be null");
		SyndEntry entry = null;

		assertThat(entry).hasLink("href", "type", "rel", "title");
	}

	@Test
	public void fails_on_null_href() throws URISyntaxException, IOException, FeedException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("hasLink assertion expects href not to be null");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(syndEntry).hasLink(null, "type", "rel", "title");
	}

	@Test
	public void fails_on_null_type() throws URISyntaxException, IOException, FeedException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("hasLink assertion expects type not to be null");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(syndEntry).hasLink("href", null, "rel", "title");
	}

	@Test
	public void fails_on_null_rel() throws URISyntaxException, IOException, FeedException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("hasLink assertion expects rel not to be null");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(syndEntry).hasLink("href", "type", null, "title");
	}

	@Test
	public void fails_on_null_title() throws URISyntaxException, IOException, FeedException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("hasLink assertion expects title not to be null");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);
		assertThat(syndEntry).hasLink("href", "type", "rel", null);
	}

	@Test
	public void fails_on_different_href() throws URISyntaxException, IOException, FeedException {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting entry with links:\n" +
				"\"\t<link href=\"http://example.org/2003/12/13/atom03\" rel=\"alternate\" title=\"Robots\" type=\"text/html\" />\n" +
				"\"to have the following attributes:\n" +
				"\thref: <\"http://example.biz/\">\n" +
				"\ttype: <\"text/html\">\n" +
				"\trel: <\"alternate\">\n" +
				"\ttitle: <\"Robots\">\n" +
				"None found.");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);

		assertThat(syndEntry).hasLink("http://example.biz/", "text/html", "alternate", "Robots");
	}

	@Test
	public void fails_on_different_type() throws URISyntaxException, IOException, FeedException {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting entry with links:\n" +
				"\"\t<link href=\"http://example.org/2003/12/13/atom03\" rel=\"alternate\" title=\"Robots\" type=\"text/html\" />\n" +
				"\"to have the following attributes:\n" +
				"\thref: <\"http://example.org/2003/12/13/atom03\">\n" +
				"\ttype: <\"text/css\">\n" +
				"\trel: <\"alternate\">\n" +
				"\ttitle: <\"Robots\">\n" +
				"None found.");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);

		assertThat(syndEntry).hasLink("http://example.org/2003/12/13/atom03", "text/css", "alternate", "Robots");
	}

	@Test
	public void fails_on_different_rel() throws URISyntaxException, IOException, FeedException {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting entry with links:\n" +
				"\"\t<link href=\"http://example.org/2003/12/13/atom03\" rel=\"alternate\" title=\"Robots\" type=\"text/html\" />\n" +
				"\"to have the following attributes:\n" +
				"\thref: <\"http://example.org/2003/12/13/atom03\">\n" +
				"\ttype: <\"text/html\">\n" +
				"\trel: <\"inline\">\n" +
				"\ttitle: <\"Robots\">\n" +
				"None found.");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);

		assertThat(syndEntry).hasLink("http://example.org/2003/12/13/atom03", "text/html", "inline", "Robots");
	}

	@Test
	public void fails_on_different_title() throws URISyntaxException, IOException, FeedException {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expecting entry with links:\n" +
				"\"\t<link href=\"http://example.org/2003/12/13/atom03\" rel=\"alternate\" title=\"Robots\" type=\"text/html\" />\n" +
				"\"to have the following attributes:\n" +
				"\thref: <\"http://example.org/2003/12/13/atom03\">\n" +
				"\ttype: <\"text/html\">\n" +
				"\trel: <\"alternate\">\n" +
				"\ttitle: <\"Robot amok\">\n" +
				"None found.");

		SyndEntry syndEntry = (SyndEntry) readFeed().getEntries().get(0);

		assertThat(syndEntry).hasLink("http://example.org/2003/12/13/atom03", "text/html", "alternate", "Robot amok");
	}


	private SyndFeed readFeed() throws IOException, FeedException, URISyntaxException {
		try (FileReader fileReader = new FileReader(new File(this.getClass().getResource("/feed.xml").toURI()))) {
			return new SyndFeedInput().build(fileReader);
		}
	}
}
