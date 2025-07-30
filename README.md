# CV-generator-webapp
LaTeX Curriculum Vitae document generator server application. Written in Clojure, powered by Compojure.

## Prerequisites
(Only applies for non-Docker users)

* Full Java installation
* Full Clojure installation
* Leiningen 2.0.0 or above

## Running
Default port in all options is 8080, thus you may access the running server with: http://localhost:8080/

* **With docker:**

You can may also build and run a Docker image:
```sh
docker build -t cv-generator .
docker run -p 8080:8080 cv-generator
```

* **With lein:**

To host the live server with hot reloading, use:
```sh
lein ring server-headless
```

* **With lein + java:**

You may "compile" the project into a single standalone java archive:
```sh
lein ring uberjar
java -jar path/to/project-1.2.3-standalone.jar
```
