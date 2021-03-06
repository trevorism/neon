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

var neon = neon || {};

neon.queryBuilder = (function () {
    var table = new tables.Table('#results', {data: []});
    var numberOfRows = 0;

    var clientId;
    neon.ready(function(){
       clientId =  neon.eventing.messaging.getInstanceId();
       restoreState();
    });

    function restoreState(){
        neon.query.getWidgetInitialization(neon.widget.QUERY_BUILDER, function(data){
            if(data && data.query){
                $('#queryText').val(data.query);
                submitQueryToServer();
            }
        });

        neon.query.getSavedState(clientId, function(data){
            $('#queryText').val(data);
            submitQueryToServer();
        });
    }

    function resizeHeightOfResultDivAndRefreshGridLayout() {
        $("#results").height(function () {
            var windowHeight = $(window).height();
            var formHeight = $("#queryForm").height();
            //36 == margin-top(20) + (2 * padding (8)).
            var containerHeight = windowHeight - formHeight - 36;
            //Header row (25), data rows (25 each), and 16 for padding
            var rowsHeight = (numberOfRows + 1) * 25 + 16;
            if (rowsHeight > containerHeight) {
                return containerHeight;
            }
            return rowsHeight;
        });
        table.refreshLayout();
    }

    function onSuccessfulQuery(data) {
        $('#results').empty();
        numberOfRows = data.data.length;
        if (numberOfRows === 0) {
            neon.queryBuilder.displayError("No results found.");
            return;
        }

        neon.queryBuilder.layoutResults();
        table = new tables.Table('#results', {data: data.data, gridOptions: {fullWidthRows: true}}).draw();

        var queryText = $('#queryText').val();
        neon.query.saveState(clientId, queryText);
    }

    function onQueryError(xhr, status, msg) {
        neon.queryBuilder.displayError(msg);
    }

    function submitQueryToServer() {
        $("#errorText").empty();
        var queryText = $('#queryText').val();
        neon.query.submitTextQuery(queryText, onSuccessfulQuery, onQueryError);
    }

    return {
        displayError: function (text) {
            $("#errorText").append(text);
        },
        layoutResults: resizeHeightOfResultDivAndRefreshGridLayout,
        submit: submitQueryToServer
    };
})();

