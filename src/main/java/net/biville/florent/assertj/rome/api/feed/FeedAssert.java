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

import com.google.common.collect.FluentIterable;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLink;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static net.biville.florent.assertj.rome.error.ShouldHaveLink.shouldHaveLink;

public class FeedAssert extends AbstractAssert<FeedAssert, SyndFeed> {

    public FeedAssert(SyndFeed feed) {
        super(feed, FeedAssert.class);
    }

    /**
     * Verifies that the actual parameters (<code>href</code>, <code>type</code>, <code>rel</code>, <code>title</code>)
     * match the actual link attributes<br/>
     * <p>
     * Example:
     *
     * <pre>
     * SyndFeed feed = ...;
     * assertThat(feed).hasLink(
     *      &quot;http://www.atom.org&quot;,
     *      &quot;text/html&quot;,
     *      &quot;related&quot;,
     *      &quot;ATOM: Ancien Teachings of the Masters&quot;
     * );
     * </pre>
     *
     * If any of the aforementioned parameters is {@code null}, an {@link IllegalArgumentException} is thrown.
     * <p>
     *
     * @param href the href attribute to look for in the actual {@link SyndFeed}
     * @param type the type attribute to look for in the actual {@link SyndFeed}
     * @param rel the rel attribute to look for in the actual {@link SyndFeed}
     * @param title the title attribute to look for in the actual {@link SyndFeed}
     * @return this {@link FeedAssert} for assertions chaining
     *
     * @throws IllegalArgumentException if any of the aforementioned parameters is {@code null}.
     * @throws AssertionError if the actual {@link SyndFeed} attributes do not match the arguments
     */

    public FeedAssert hasLink(String href, String type, String rel, String title) {
        Objects.instance().assertNotNull(info, actual);

        notNull("href", href);
        notNull("type", type);
        notNull("rel", rel);
        notNull("title", title);

        List<SyndLink> links = this.actual.getLinks();

        SameLinkAttributes predicate = new SameLinkAttributes(href, type, rel, title);
        if (!matchingByAttributes(links, predicate)) {
            throw Failures.instance().failure(info, shouldHaveLink(actual, href, type, rel, title));
        }

        return this;
    }

    private void notNull(final String name, Object value) {
        checkArgument(value != null, format("hasLink assertion expects %s not to be null", name));
    }

    private boolean matchingByAttributes(List<SyndLink> links, SameLinkAttributes predicate) {
        return FluentIterable.from(links).firstMatch(predicate).isPresent();
    }
}
