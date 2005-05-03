package org.crosswire.jsword.book.search.lucene;

import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.search.Index;
import org.crosswire.jsword.passage.Key;
import org.crosswire.jsword.passage.RestrictionType;

/**
 * A blur token specifies much to blur the results of the right token.
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author DM Smith [ dmsmith555 at yahoo dot com]
 * @version $Id$
 */
public class BlurQuery extends BinaryQuery
{

    /**
     * 
     */
    public BlurQuery(Query theLeftToken, Query theRightToken, int theFactor)
    {
        super(theLeftToken, theRightToken);
        factor = theFactor;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.search.parse.Query#find(org.crosswire.jsword.book.search.Index)
     */
    public Key find(Index index) throws BookException
    {
        Key left = getLeftToken().find(index);
        Key right = getRightToken().find(index);
        right.blur(factor, RestrictionType.getDefaultBlurRestriction());
        left.retainAll(right);
        return left;
    }

    /**
     * @return the blur factor
     */
    public int getFactor()
    {
        return factor;
    }

    private int factor;
}