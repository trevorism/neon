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

package com.ncc.neon.transform
import com.ncc.neon.query.QueryResult
import com.ncc.neon.query.TableQueryResult


class SalaryTransformer implements Transformer{

    @Override
    QueryResult convert(QueryResult queryResult, def params) {
        List<Map<String,Object>> data = queryResult.data
        data.each { Map<String,Object> rows ->
            rows.put("salary", rows.get("salary") * 1.1)
        }
        return new TableQueryResult(data)
    }

    @Override
    String getName() {
        return SalaryTransformer.name
    }
}
