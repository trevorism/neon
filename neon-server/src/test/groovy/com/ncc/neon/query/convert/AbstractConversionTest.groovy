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

package com.ncc.neon.query.convert
import com.ncc.neon.query.Query
import com.ncc.neon.query.clauses.*
import com.ncc.neon.query.filter.DataSet
import com.ncc.neon.query.filter.Filter
import com.ncc.neon.query.filter.FilterKey
import com.ncc.neon.query.filter.FilterState
import org.junit.Before
import org.junit.Test


/*
 Sets up unit tests to be run against both hive and mongo conversion strategies
*/
abstract class AbstractConversionTest {

    protected static final String DATABASE_NAME = "database"
    protected static final String TABLE_NAME = "table"
    protected static final String COLUMN_NAME = "column"
    protected static final String COLUMN_VALUE = "value"
    protected static final String FIELD_NAME = "field"
    protected static final String FIELD_NAME_2 = "field2"
    protected static final int LIMIT_AMOUNT = 5
    protected static final int SKIP_AMOUNT = 2

    protected FilterState filterState
    private Filter simpleFilter
    protected Query simpleQuery

    @Before
    void before() {
        simpleFilter = new Filter(databaseName: DATABASE_NAME, tableName: TABLE_NAME)
        simpleQuery = new Query(filter: simpleFilter)
        filterState = new FilterState()
    }

    @Test(expected = NullPointerException)
    void "test converting a query requires a filter"() {
        Query query = new Query()
        convertQuery(query)
    }

    @Test
    void "test converting a query with just a dataset populated"() {
        def query = convertQuery(simpleQuery)
        assertSimplestConvertQuery(query)
    }

    @Test
    void "test converting a query with a filter in the FilterState"() {
        givenFilterStateHasOneFilter()
        def query = convertQuery(simpleQuery)
        assertQueryWithOneFilterInFilterState(query)
    }

    @Test
    void "test converting a compound query with a filter in the FilterState"() {
        givenFilterStateHasOneFilter()
        givenQueryHasOrWhereClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithOrWhereClause(query)
    }

    @Test
    void "test select clause populated"(){
        givenQueryHasFields()
        def query = convertQuery(simpleQuery)
        assertSelectClausePopulated(query)
    }

    @Test
    void "test sort clause populated"(){
        givenQueryHasSortClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithSortClause(query)
    }

    @Test
    void "test limit clause populated"(){
        givenQueryHasLimitClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithLimitClause(query)
    }

    @Test
    void "test offset clause populated"(){
        givenQueryHasSkipClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithOffsetClause(query)
    }


    @Test
    void "test distinct clause populated"(){
        givenQueryHasDistinctClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithDistinctClause(query)
    }

    @Test
    void "test aggregate clause populated"(){
        givenQueryHasAggregateClause()
        def query = convertQuery(simpleQuery)
        assertQueryWithAggregateClause(query)
    }

    @Test
    void "test group by clause populated"(){
        givenQueryHasGroupByPopulated()
        def query = convertQuery(simpleQuery)
        assertQueryWithGroupByClauses(query)
    }

    @Test
    void "test a filter with no where clause"(){
        givenFilterStateHasAnEmptyFilter()
        def query = convertQuery(simpleQuery)
        assertQueryWithEmptyFilter(query)
    }

    protected abstract def convertQuery(query)

    protected abstract void assertSelectClausePopulated(query)

    protected abstract void assertSimplestConvertQuery(query)

    protected abstract void assertQueryWithOneFilterInFilterState(query)

    protected abstract void assertQueryWithSortClause(query)

    protected abstract void assertQueryWithLimitClause(query)

    protected abstract void assertQueryWithOffsetClause(query)

    protected abstract void assertQueryWithDistinctClause(query)

    protected abstract void assertQueryWithAggregateClause(query)

    protected abstract void assertQueryWithGroupByClauses(query)

    protected abstract void assertQueryWithOrWhereClause(query)

    protected abstract void assertQueryWithEmptyFilter(query)

    private void givenFilterStateHasAnEmptyFilter(){
        FilterKey filterKey = new FilterKey(uuid: UUID.randomUUID(), dataSet: new DataSet(databaseName: simpleFilter.databaseName, tableName: simpleFilter.tableName))
        Filter filter = new Filter(databaseName: simpleFilter.databaseName, tableName: simpleFilter.tableName)
        filterState.addFilter(filterKey, filter)
    }

    private void givenFilterStateHasOneFilter() {
        FilterKey filterKey = new FilterKey(uuid: UUID.randomUUID(), dataSet: new DataSet(databaseName: simpleFilter.databaseName, tableName: simpleFilter.tableName))
        SingularWhereClause whereClause = new SingularWhereClause(lhs: COLUMN_NAME, operator: "=", rhs: COLUMN_VALUE)
        Filter filterWithWhere = new Filter(databaseName: simpleFilter.databaseName, tableName: simpleFilter.tableName, whereClause: whereClause)
        filterState.addFilter(filterKey, filterWithWhere)
    }

    private void givenQueryHasFields() {
        simpleQuery.fields = [FIELD_NAME, FIELD_NAME_2]
    }

    private void givenQueryHasOrWhereClause() {
        SingularWhereClause clause1 = new SingularWhereClause(lhs: FIELD_NAME, operator: "=", rhs: COLUMN_VALUE)
        SingularWhereClause clause2 = new SingularWhereClause(lhs: FIELD_NAME_2, operator: "=", rhs: COLUMN_VALUE)
        OrWhereClause orWhereClause = new OrWhereClause(whereClauses: [clause1, clause2])

        simpleQuery.filter.whereClause = orWhereClause
    }

    private void givenQueryHasSortClause() {
        simpleQuery.sortClauses = [new SortClause(fieldName: FIELD_NAME, sortOrder: SortOrder.ASCENDING)]
    }

    private void givenQueryHasLimitClause() {
        simpleQuery.limitClause = new LimitClause(limit: LIMIT_AMOUNT)
    }

    private void givenQueryHasSkipClause() {
        simpleQuery.offsetClause = new OffsetClause(offset: SKIP_AMOUNT)
    }

    private void givenQueryHasDistinctClause() {
        simpleQuery.isDistinct = true
    }

    private void givenQueryHasAggregateClause() {
        simpleQuery.aggregates = [new AggregateClause(name: "${FIELD_NAME}_sum", operation: "sum", field: FIELD_NAME)]
    }

    private void givenQueryHasGroupByPopulated() {
        simpleQuery.groupByClauses = [new GroupByFieldClause(field: "${FIELD_NAME_2}"),
                new GroupByFunctionClause(name: "${FIELD_NAME}_sum", operation: "sum", field: FIELD_NAME)]
    }

}
