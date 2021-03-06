## Overview
The neon framework provides a SQL-like query language that can be used by javascript frameworks to execute queries
  against different types of databases. The queries are executed on the server, and neon translates its query language
  into database specific queries.

Neon also provides an interaction framework that allows different visualizations (widgets) to communicate with one
   another. For example, selecting a point on a map widget might select that point in a table.


## Building neon

Neon contains a mix of groovy and javascript code and uses different build tools for the different languages:

* gradle (or gradlew) can be used to build the groovy code
* npm/nodejs/grunt are used to build the javascript code
* the gradle build files provide wrapper tasks for executing grunt tasks

### setup instructions for npm/nodejs (Ubuntu)

* install nodejs - [https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager](https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager) and npm [https://github.com/isaacs/npm](https://github.com/isaacs/npm)
* update npm to the latest version by running `sudo npm -g update npm`

### Test tasks

Several tasks exist for running tests:

* test - runs groovy unit tests
* integrationTest - runs groovy integration tests (requires mongodb and/or shark). Note: To run mongo only tests, use the flag `-DintegrationTest.single=Mongo` or `-DintegrationTest.single=Hive` for shark tests only
* acceptanceTest - runs end to end acceptance tests (requires mongodb)
* gruntjs runs all javascript verification tasks - unit test, jshint, etc


note: all projects share a common javascript configuration, so the package.json file is dynamically generated from the package.json.template in the top level neon folder

## Project structure

The neon project is broken down into several sub projects. The neon-server subproject includes the neon server and its client API.

Each widget is a separate subproject which is stored in the widgets folder.