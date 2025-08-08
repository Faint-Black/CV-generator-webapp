# CV generator webserver application
LaTeX Curriculum Vitae document generator web application. Written in Clojure, powered by Compojure.

## Prerequisites
(Only applies for non-Docker users)

* Full Java installation
* Full Clojure installation
* Leiningen 2.0.0 or above

## Building and Running
Default port in all options is 8080, thus you may access the running server with: http://localhost:8080/

* **With Docker:**

To build and run a Docker image:
```sh
docker build -t cv-generator .
docker run -p 8080:8080 cv-generator
```

* **With Lein:**

To host the live server with hot reloading:
```sh
lein ring server-headless
```

* **With Java:**

To "compile" the project into a single standalone java archive and run it:
```sh
lein ring uberjar
java -jar path/to/project-1.2.3-standalone.jar
```

## Files and Logging
Currently, all submission and compilation logs are simply printed to stdout, and intermediary files, such as .tex and .pdf, are placed in a timestamped directory inside /tmp/cv-generator/ and served as blobs through EcmaScript.
