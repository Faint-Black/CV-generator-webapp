(ns cv-generator.compile
  (:require
   [clojure.string :as string]
   [clojure.java.shell :refer [sh]]
   [babashka.fs :as fs]))

(defn compile-ok
  "Determines if the compilation occured succesfully"
  [compilation-status]
  (let [shell-output (:shell-result compilation-status)
        exit-status (:exit shell-output)
        stdout (:out shell-output)
        stderr (:err shell-output)]
    (if (= exit-status 0)
      {:success true
       :message stdout}
      {:success false
       :message stderr})))

(defn compile-latex
  "Takes in a TeX string and compiles it with the tectonic engine, stores intermediates in /tmp directory, returns the terminal output"
  [built-latex timestamp]
  (let [dirname (string/join ["/tmp/cv-generator/compile-" timestamp])
        dir (fs/create-dirs dirname)
        file (fs/file dir "cv.tex")
        _ (spit file built-latex)]
    {:shell-result (sh "tectonic" "--untrusted" "cv.tex" :dir dirname)
     :input-file (string/join [dirname "/cv.tex"])
     :output-file (string/join [dirname "/cv.pdf"])}))

