/*
 * Copyright 2013 Next Century Corporation
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ncc.neon.query.filter
import com.ncc.neon.cache.ImmutableValueCache
import com.ncc.neon.query.clauses.AndWhereClause
import com.ncc.neon.query.clauses.WhereClause


class FilterCache extends ImmutableValueCache<FilterKey, Filter> implements Serializable{
    private static final long serialVersionUID = - 5911927417066895555L

    /**
     * Clears all existing filters
     */
    void clearAllFilters() {
        cache.clear()
    }

    /**
     * Adds a filter to the filter state
     * @param filterKey
     * @param filter
     * @return
     */
    void addFilter(FilterKey filterKey, Filter filter) {
        def oldFilter = add(filterKey, filter)
        if(oldFilter){
            filter.whereClause = determineFilterWhereClause(oldFilter, filter)
            replace(filterKey, filter)
        }
    }

    private WhereClause determineFilterWhereClause(Filter oldFilter, Filter filter) {
        if (oldFilter.whereClause) {
            AndWhereClause andWhereClause = new AndWhereClause(whereClauses: [oldFilter.whereClause, filter.whereClause])
            return andWhereClause
        }
        return filter.whereClause
    }

    /**
     * Removes the filter
     * @param id The id of the filter generated when adding it
     * @return
     */
    void removeFilter(FilterKey filterKey) {
        remove(filterKey)
    }

    /**
     * Gets any filters that are applied to the specified dataset
     * @param dataset The current dataset
     * @return A list of filters applied to that dataset
     */
    List<Filter> getFiltersForDataset(DataSet dataSet) {
        return cache.findResults {k,v ->
            if(k.dataSet == dataSet){
                return v
            }
        }
    }

}
