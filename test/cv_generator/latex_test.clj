(ns cv-generator.latex-test
  (:require
   [clojure.test :refer :all]
   [cv-generator.latex :refer :all]))

(deftest input-sanitizing
  (testing "Sanitizing arbitrary user input"
    (is (= "foobar" (sanitize-input "foobar")))
    (is (= "\\textbf{foobar}" (sanitize-input "\\textbf{foobar}")))
    (is (= "[UNDEFINED]" (sanitize-input "")))
    (is (= "[REDACTED]" (sanitize-input "\\immediate\\write18{echo pwned!}")))))
