# CV-generator-webapp
LaTeX Curriculum Vitae document generator server application. Written in Clojure, powered by Compojure.

## Prerequisites
* Full Java installation
* Full Clojure installation
* Leiningen 2.0.0 or above

## Running
To host the live server with hot reloading, use:
```sh
lein ring server-headless
```

Alternatively, you may "compile" the project into a single standalone java archive:
```sh
lein ring uberjar
java -jar path/to/project-1.2.3-standalone.jar
```

Default port is 8080, so access the server with: http://localhost:8080/

