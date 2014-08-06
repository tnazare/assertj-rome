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
package net.biville.florent.assertj.rome.error;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLink;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.internal.StandardComparisonStrategy;

import java.util.List;

import static java.lang.String.format;

public class ShouldHaveLink extends BasicErrorMessageFactory {

    private ShouldHaveLink(SyndFeed actual, String href, String type, String rel, String title) {
        super("\nExpecting feed with links:\n%sto have the following attributes:\n\thref: <%s>\n\ttype: <%s>\n\trel: <%s>\n\ttitle: <%s>\nNone found.",
                asLink(actual),
                href,
                type,
                rel,
                title,
                StandardComparisonStrategy.instance());
    }

	private ShouldHaveLink(SyndEntry actual, String href, String type, String rel, String title) {
		super("\nExpecting entry with links:\n%sto have the following attributes:\n\thref: <%s>\n\ttype: <%s>\n\trel: <%s>\n\ttitle: <%s>\nNone found.",
				asLink(actual),
				href,
				type,
				rel,
				title,
				StandardComparisonStrategy.instance());
	}

    public static ShouldHaveLink shouldHaveLink(SyndFeed actual, String href, String type, String rel, String title) {
        return new ShouldHaveLink(actual, href, type, rel, title);
    }

	 public static ShouldHaveLink shouldHaveLink(SyndEntry actual, String href, String type, String rel, String title) {
        return new ShouldHaveLink(actual, href, type, rel, title);
    }

    private static String asLink(SyndFeed actual) {
        StringBuilder builder = new StringBuilder();
        for (SyndLink link : (List<SyndLink>) actual.getLinks()) {
            builder.append(format(
                 "\t<link href='%s' rel='%s' type='%s' title='%s' />\n",
                 link.getHref(), link.getRel(), link.getType(), link.getTitle()
            ));
        }

        return builder.toString();
    }

	 private static String asLink(SyndEntry actual) {
        StringBuilder builder = new StringBuilder();
        for (SyndLink link : (List<SyndLink>) actual.getLinks()) {
	        if(link.getHref() == null && link.getHreflang() == null
			        && link.getRel() == null && link.getTitle() == null
			        && link.getType() == null && link.getLength() == 0l ){
		        continue;
	        }
	        builder.append("\t<link ");
	        if(link.getHref() != null){
		        builder.append(format("href=\"%s\" ", link.getHref()));
	        }
	        if(link.getHreflang() != null){
		        builder.append(format("hreflang=\"%s\" ", link.getHreflang()));
	        }
	        if(link.getRel() != null){
		        builder.append(format("rel=\"%s\" ", link.getRel()));
	        }
	        if(link.getTitle() != null){
		        builder.append(format("title=\"%s\" ", link.getTitle()));
	        }
	        if(link.getType() != null){
		        builder.append(format("type=\"%s\" ", link.getType()));
	        }
	        if(link.getLength() != 0l){
		        builder.append(format("length=\"%s\" ", link.getLength()));
	        }
           builder.append("/>\n");
        }

        return builder.toString();
    }
}
