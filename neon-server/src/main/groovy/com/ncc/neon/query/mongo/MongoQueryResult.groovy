package com.ncc.neon.query.mongo

import com.ncc.neon.query.QueryResult
import com.ncc.neon.query.Row
import com.ncc.neon.query.DefaultRow

/*
 * ************************************************************************
 * Copyright (c), 2013 Next Century Corporation. All Rights Reserved.
 *
 * This software code is the exclusive property of Next Century Corporation and is
 * protected by United States and International laws relating to the protection
 * of intellectual property.  Distribution of this software code by or to an
 * unauthorized party, or removal of any of these notices, is strictly
 * prohibited and punishable by law.
 *
 * UNLESS PROVIDED OTHERWISE IN A LICENSE AGREEMENT GOVERNING THE USE OF THIS
 * SOFTWARE, TO WHICH YOU ARE AN AUTHORIZED PARTY, THIS SOFTWARE CODE HAS BEEN
 * ACQUIRED BY YOU "AS IS" AND WITHOUT WARRANTY OF ANY KIND.  ANY USE BY YOU OF
 * THIS SOFTWARE CODE IS AT YOUR OWN RISK.  ALL WARRANTIES OF ANY KIND, EITHER
 * EXPRESSED OR IMPLIED, INCLUDING, WITHOUT LIMITATION, IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, ARE HEREBY EXPRESSLY
 * DISCLAIMED.
 *
 * PROPRIETARY AND CONFIDENTIAL TRADE SECRET MATERIAL NOT FOR DISCLOSURE OUTSIDE
 * OF NEXT CENTURY CORPORATION EXCEPT BY PRIOR WRITTEN PERMISSION AND WHEN
 * RECIPIENT IS UNDER OBLIGATION TO MAINTAIN SECRECY.
 */

class MongoQueryResult implements QueryResult {

    /** the underlying iterable wrapped by the this result */
    private def mongoIterable

    @Override
    Iterator<Row> iterator() {
        // wrap the result in a Row and set the current row
        def delegate = mongoIterable.iterator()
        return new MongoQueryIterator(delegate)
    }

    @Override
    String toJson() {
        return MongoUtils.serialize(mongoIterable)
    }

    private static class MongoQueryIterator implements Iterator<Row>{

        private final Iterator delegate

        public MongoQueryIterator(Iterator delegate){
            this.delegate = delegate
        }

        @Override
        boolean hasNext() {
            return delegate.hasNext()
        }

        @Override
        Row next() {
            return new DefaultRow(defaultRow: delegate.next())
        }

        @Override
        void remove() {
            delegate.remove()
        }
    }
}
