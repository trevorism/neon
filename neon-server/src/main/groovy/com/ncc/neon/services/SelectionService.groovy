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

package com.ncc.neon.services

import com.ncc.neon.query.filter.FilterContainer
import com.ncc.neon.query.filter.FilterKey
import com.ncc.neon.query.filter.SelectionState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType



/**
 * Service for working with a user's current selection.
 */

@Component
@Path("/selectionservice")
class SelectionService {

    @Autowired
    SelectionState selectionState

    /**
     * Add an additional selection
     * @param container An object containing a filter key, a server generated identifier for a given widget instance,
     * and a filter which contains information about the selection,
     */
    @POST
    @Path("addselection")
    @Consumes(MediaType.APPLICATION_JSON)
    void addSelection(FilterContainer container) {
        selectionState.addFilter(container.filterKey, container.filter)
    }

    /**
     * Remove the selection for the given widget instance, identified by the filterKey
     * @param filterKey a server generated identifier for a given widget instance
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("removeselection")
    void removeSelection(FilterKey filterKey) {
        selectionState.removeFilter(filterKey)
    }

    /**
     * Replace the selection for the given filter key. If none exists, this works the same as addSelection(container)
     * @param container An object containing a filter key, a server generated identifier for a given widget instance,
     * and a filter which contains information about the selection,
     */
    @POST
    @Path("replaceselection")
    @Consumes(MediaType.APPLICATION_JSON)
    void replaceSelection(FilterContainer container) {
        removeSelection(container.filterKey)
        addSelection(container)
    }

    /**
     * Clears all selections.
     */
    @POST
    @Path("clearselection")
    void clearSelection() {
        selectionState.clearAllFilters()
    }

}
