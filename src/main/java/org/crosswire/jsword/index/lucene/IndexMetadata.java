/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2007-2013
 *     The copyright to this program is held by it's authors.
 *
 */
package org.crosswire.jsword.index.lucene;

import java.io.IOException;

import org.crosswire.common.util.PropertyMap;
import org.crosswire.common.util.ResourceUtil;
import org.crosswire.jsword.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A singleton that Reads and Maintains IndexMetadata from properties file All
 * version number in the properties file must be float.
 * 
 * @see gnu.lgpl.License for license details.<br>
 *      The copyright to this program is held by it's authors.
 * @author Sijo Cherian [sijocherian at yahoo dot com]
 */
public final class IndexMetadata {
    private IndexMetadata() {
        try {
            props = ResourceUtil.getProperties(getClass());
        } catch (IOException e) {
            log.error("Property file read error", e);
        }
    }

    /**
     * All access to IndexMetadata is through this single instance.
     * 
     * @return the singleton instance
     */
    public static IndexMetadata instance() {
        return myInstance;
    }
    //default Installed IndexVersion: index version that is installed/available in the Client's index folders
    //todo get Installed ver from the IndexFolder location
    public float getInstalledIndexVersion() {
        String value = props.get(INDEX_VERSION, "1.1"); //todo At some point default should be 1.2
        return Float.parseFloat(value);
    }

    public float getLuceneVersion() {
        return Float.parseFloat(props.get(LUCENE_VERSION));
    }

    //Default Latest IndexVersion : Default version number of Latest indexing schema: PerBook index version must be equal or greater than this
    public float getLatestIndexVersion() {
        String value = props.get(LATEST_INDEX_VERSION, "1.2");
        return Float.parseFloat(value);
    }

    public float getLatestIndexVersion(Book b) {
        if(b==null) return getLatestIndexVersion();

        String value = props.get(PREFIX_LATEST_INDEX_VERSION_BOOK_OVERRIDE+b.getBookMetaData().getInitials(),
                        props.get(LATEST_INDEX_VERSION) );
        return Float.parseFloat(value);
    }
    /*public float getInstalledIndexVersion(Book b) {
        if(b==null) return getInstalledIndexVersion();

        String value = props.get(PREFIX_INSTALLED_INDEX_VERSION_BOOK_OVERRIDE +b.getBookMetaData().getInitials(),
                props.get(INDEX_VERSION) );
        return Float.parseFloat(value);
    }*/

    public static final String INDEX_VERSION = "Installed.Index.Version";
    public static final String LATEST_INDEX_VERSION = "Latest.Index.Version";
    public static final String LUCENE_VERSION = "Lucene.Version";

    @Deprecated
    /* use latest version*/
    public static final float INDEX_VERSION_1_1 = 1.1f;
    public static final float INDEX_VERSION_1_2 = 1.2f;

    public static final String PREFIX_LATEST_INDEX_VERSION_BOOK_OVERRIDE = "Latest.Index.Version.Book.";
    public static final String PREFIX_INSTALLED_INDEX_VERSION_BOOK_OVERRIDE = "Installed.Index.Version.Book.";
    private static final Logger log = LoggerFactory.getLogger(IndexMetadata.class);
    private static IndexMetadata myInstance = new IndexMetadata();
    private PropertyMap props;
}
