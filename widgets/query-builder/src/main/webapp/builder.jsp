<!--
Copyright 2013 Next Century Corporation
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="neon" uri="http://nextcentury.com/tags/neon" %>

<!DOCTYPE html>
<html>
<head>
    <title>Query Builder</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/builder.css"/>
    <link rel="stylesheet" type="text/css" href="css/slickgrid/slick.grid.css"/>
    <link rel="stylesheet" type="text/css" href="css/smoothness/jquery-ui-1.8.16.custom.css"/>
    <!-- widgetbase.css must be included after slickgrid in order to override the slickgrid table color scheme -->
    <link rel="stylesheet" type="text/css" href="css/widgetbase.css">
    <link rel="stylesheet" type="text/css" href="<neon:neon-url/>/css/neon.css"/>

    <script src="<neon:owf-url/>/js/owf-widget.js"></script>
    <script src="<neon:neon-url/>/js/neon.js"></script>

    <!-- build:js js/query-builder.js -->
    <script src="js/tables.js"></script>
    <script src="js-lib/jquery/jquery-1.10.1.min.js"></script>
    <script src="js-lib/jquery-resize/jquery.ba-resize-1.1.min.js"></script>
    <script src="js-lib/lodash/1.3.1/lodash.min.js"></script>
    <script src="javascript/initialize.js"></script>
    <script src="javascript/builder.js"></script>
    <!-- endbuild -->

</head>
<body>
<neon:hidden-neon-server/>

<div id="queryForm">
    <h4> Enter a Query </h4>
    <textarea id="queryText" class="space-below" rows="3"></textarea>

    <div id="errorText" class="error-text space-below"></div>
    <button id="submit" class="btn" onclick="neon.queryBuilder.submit();">Submit</button>
</div>

<div id="results"></div>

</body>
</html>