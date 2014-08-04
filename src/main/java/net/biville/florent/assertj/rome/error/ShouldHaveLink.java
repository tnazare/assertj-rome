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

    public static ShouldHaveLink shouldHaveLink(SyndFeed actual, String href, String type, String rel, String title) {
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
}
