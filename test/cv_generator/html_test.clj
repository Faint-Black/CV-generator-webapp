(ns cv-generator.html-test
  (:require
   [clojure.test :refer :all]
   [cv-generator.html :refer :all]))

(deftest span-wrapping
  (testing "HTML wrapping in <span> tag"
    (is (= "<span class=\"bar\">foo</span>" (wrap-in-span "foo" "bar")))
    (is (= "<span class=\"keyword\">\\begin</span>" (wrap-in-span "\\begin" "keyword")))))
