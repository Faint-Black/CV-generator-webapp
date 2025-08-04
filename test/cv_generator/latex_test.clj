(ns cv-generator.latex-test
  (:require
   [clojure.test :refer :all]
   [cv-generator.latex :refer :all]))

(deftest input-sanitizing
  (testing "Sanitizing arbitrary user input"
    (is (= "foobar" (sanitize-input "foobar")))
    (is (= "\\textbf{foobar}" (sanitize-input "\\textbf{foobar}")))
    (is (= "[UNDEFINED]" (sanitize-input "")))
    (is (= "[REDACTED]" (sanitize-input "\\immediate\\write18{echo pwned!}")))
    (is (= {:foo "[UNDEFINED]" :bar "[REDACTED]"}
           (sanitize-input {:foo "" :bar "\\write"})))
    (is (= [{:foo "[UNDEFINED]" :bar ["[UNDEFINED]" "[UNDEFINED]" "[UNDEFINED]"]}
            {:biz "[REDACTED]" :baz "baz"}]
           (sanitize-input [{:foo "" :bar ["" "" ""]}
                            {:biz "\\write" :baz "baz"}])))))
