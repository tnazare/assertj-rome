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

import com.google.common.base.Predicate;
import com.sun.syndication.feed.synd.SyndLink;

public class SameLinkAttributes implements Predicate<SyndLink> {

    private final String href;
    private final String type;
    private final String rel;
    private final String title;

    public SameLinkAttributes(String href, String type, String rel, String title) {
        this.href = href;
        this.type = type;
        this.rel = rel;
        this.title = title;
    }

    @Override
    public boolean apply(SyndLink link) {
        return href.equals(link.getHref())
                && type.equals(link.getType())
                && rel.equals(link.getRel())
                && title.equals(link.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
