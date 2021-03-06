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
import com.ncc.neon.language.QueryParser
import com.ncc.neon.query.Query
import com.ncc.neon.query.QueryExecutor
import com.ncc.neon.query.QueryResult
import com.ncc.neon.query.TableQueryResult
import org.junit.Before
import org.junit.Test


class LanguageServiceTest {

    private LanguageService languageService

    @Before
    void before() {
        languageService = new LanguageService()
        QueryExecutor executor = [execute: { query, options -> new TableQueryResult([["key1": "val1"], ["key2": 2]]) }] as QueryExecutor
        languageService.queryExecutorFactory = [getExecutor: { executor }] as QueryExecutorFactory

        QueryParser queryParser = [parse: { new Query() }] as QueryParser
        languageService.queryParser = queryParser
    }

    @Test
    void "execute query"() {
        QueryResult result = languageService.executeQuery("queryText")
        assert result.data
    }

}
