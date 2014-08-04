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
package net.biville.florent.assertj.rome.api.feed;

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

public class FeedAssertTest_hasLink_Test {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void prepare() {
        exception.handleAssertionErrors();
    }

    @Test
    public void succeeds_when_given_link_matches() throws URISyntaxException, IOException, FeedException {
        SyndFeed feed = readFeed();

        assertThat(feed).hasLink("http://example.org/", "text/html", "related", "Example");
    }

    @Test
    public void fails_on_null_feed() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Expecting actual not to be null");

        assertThat(null).hasLink("href", "type", "rel", "title");
    }

    @Test
    public void fails_on_null_href() throws URISyntaxException, IOException, FeedException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("hasLink assertion expects href not to be null");

        SyndFeed syndFeed = readFeed();
        assertThat(syndFeed).hasLink(null, "type", "rel", "title");
    }

    @Test
    public void fails_on_null_type() throws URISyntaxException, IOException, FeedException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("hasLink assertion expects type not to be null");

        SyndFeed syndFeed = readFeed();
        assertThat(syndFeed).hasLink("href", null, "rel", "title");
    }

    @Test
    public void fails_on_null_rel() throws URISyntaxException, IOException, FeedException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("hasLink assertion expects rel not to be null");

        SyndFeed syndFeed = readFeed();
        assertThat(syndFeed).hasLink("href", "type", null, "title");
    }

    @Test
    public void fails_on_null_title() throws URISyntaxException, IOException, FeedException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("hasLink assertion expects title not to be null");

        SyndFeed syndFeed = readFeed();
        assertThat(syndFeed).hasLink("href", "type", "rel", null);
    }

    @Test
    public void fails_on_different_href() throws URISyntaxException, IOException, FeedException {
        exception.expect(AssertionError.class);
        exception.expectMessage("Expecting feed with links:\n" +
            "\"\t<link href='http://example.org/' rel='related' type='text/html' title='Example' />\n" +
            "\"to have the following attributes:\n" +
            "\thref: <\"http://example.biz/\">\n" +
            "\ttype: <\"text/html\">\n" +
            "\trel: <\"related\">\n" +
            "\ttitle: <\"Example\">\n" +
            "None found.");

        SyndFeed feed = readFeed();

        assertThat(feed).hasLink("http://example.biz/", "text/html", "related", "Example");
    }

    @Test
    public void fails_on_different_type() throws URISyntaxException, IOException, FeedException {
        exception.expect(AssertionError.class);
        exception.expectMessage("Expecting feed with links:\n" +
            "\"\t<link href='http://example.org/' rel='related' type='text/html' title='Example' />\n" +
            "\"to have the following attributes:\n" +
            "\thref: <\"http://example.org/\">\n" +
            "\ttype: <\"text/css\">\n" +
            "\trel: <\"related\">\n" +
            "\ttitle: <\"Example\">\n" +
            "None found.");

        SyndFeed feed = readFeed();

        assertThat(feed).hasLink("http://example.org/", "text/css", "related", "Example");
    }

    @Test
    public void fails_on_different_rel() throws URISyntaxException, IOException, FeedException {
        exception.expect(AssertionError.class);
        exception.expectMessage("Expecting feed with links:\n" +
            "\"\t<link href='http://example.org/' rel='related' type='text/html' title='Example' />\n" +
            "\"to have the following attributes:\n" +
            "\thref: <\"http://example.org/\">\n" +
            "\ttype: <\"text/css\">\n" +
            "\trel: <\"inline\">\n" +
            "\ttitle: <\"Example\">\n" +
            "None found.");

        SyndFeed feed = readFeed();

        assertThat(feed).hasLink("http://example.org/", "text/css", "inline", "Example");
    }

    @Test
    public void fails_on_different_title() throws URISyntaxException, IOException, FeedException {
        exception.expect(AssertionError.class);
        exception.expectMessage("Expecting feed with links:\n" +
            "\"\t<link href='http://example.org/' rel='related' type='text/html' title='Example' />\n" +
            "\"to have the following attributes:\n" +
            "\thref: <\"http://example.org/\">\n" +
            "\ttype: <\"text/css\">\n" +
            "\trel: <\"inline\">\n" +
            "\ttitle: <\"Exemple\">\n" +
            "None found.");

        SyndFeed feed = readFeed();

        assertThat(feed).hasLink("http://example.org/", "text/css", "inline", "Exemple");
    }

    private SyndFeed readFeed() throws IOException, FeedException, URISyntaxException {
        try (FileReader fileReader = new FileReader(new File(this.getClass().getResource("/feed.xml").toURI()))) {
            return new SyndFeedInput().build(fileReader);
        }
    }
}