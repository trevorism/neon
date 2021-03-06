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
    <title>Map</title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/widgetbase.css">
    <link rel="stylesheet" type="text/css" href="css/map.css">
    <link rel="stylesheet" type="text/css" href="<neon:neon-url/>/css/neon.css">

    <script src="<neon:owf-url/>/js/owf-widget.js"></script>
    <script src="<neon:neon-url/>/js/neon.js"></script>

    <!-- build:js js/map.js -->
    <script src="js-lib/d3/d3.v3.min.js"></script>
    <script src="js-lib/openlayers/OpenLayers.debug.js"></script>
    <script src="js-lib/heatmap/heatmap.js"></script>
    <script src="js-lib/heatmap/heatmap-openlayers.js"></script>
    <script src="js/toggle.js"></script>
    <script src="js/dropdown.js"></script>
    <script src="javascript/coremap.js"></script>
    <!-- endbuild -->

    <!-- build:js js/mapwidgetutils.js -->
    <script src="javascript/mapwidgetutils.js"></script>
    <!-- endbuild -->

    <!-- build:js js/mapwidget.js -->
    <script src="javascript/mapwidget.js"></script>
    <!-- endbuild -->

</head>
<body>
    <neon:hidden-neon-server/>
    <div class="functions-bar">
        <div id="functions-toggle-id" class="toggle"><label class="options-label">Functions</label><img src="img/arrow.png" class="toggle-image"/></div>
        <div id="functions-panel" class="options">
            <div class="control-group">
                <label class="control-label" for="points">Layers</label>

                <label class="radio inline control-label">
                    <input id="points" type="radio" name="layer-group" value="points" checked>Default</input>
                </label>
                <label class="radio inline control-label">
                    <input id="heatmap" type="radio" name="layer-group" value="heatmap">Density</input>
                </label>
            </div>
            <div class="control-group">
                <div class="checkbox" style="width:100px">
                    <label>
                        <input id="auto-filter" type="checkbox" value="autoFilter">Auto Filter</input>
                    </label>
                </div>
            </div>
            <div class="control-group">
                <div class="btn-group">
                    <button class="btn-small map-button" id="map-redraw-button">Redraw Map</button>
                </div>
            </div>
            <div class="control-group">
                <div class="btn-group">
                    <button class="btn-small map-button" id="map-reset-button">Reset Map</button>
                </div>
            </div>
        </div>
    </div>

    <div id="options-panel" class="options">
        <div class="controls-row">
            <div class="control-group">
                <label class="control-label" for="latitude">Latitude Field</label>

                <div class="controls">
                    <select id="latitude" class="configuration-dropdown"></select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="longitude">Longitude Field</label>

                <div class="controls">
                    <select id="longitude" class="configuration-dropdown"></select>
                </div>
            </div>

            <div id="size-by-group" class="control-group">
                <label class="control-label" for="size-by">Size By</label>

                <div class="controls">
                    <select id="size-by" class="configuration-dropdown"></select>
                </div>
            </div>

            <div id="color-by-group" class="control-group">
                <label class="control-label" for="color-by">Color By</label>

                <div class="controls">
                    <select id="color-by" class="configuration-dropdown"></select>
                </div>
            </div>

        </div>
    </div>

    <div id="map" class="map-location"></div>

</body>
</html>