(ns cv-generator.html
  (:require
   [cv-generator.latex :as latex]
   [clojure.string :as string]))

(def html-style
  "<style>
span.keyword {
    color: blue;
    font-weight: bold;
}
span.comment {
    color: gray;
    font-weight: bold;
}
span.undefined {
    background-color: silver;
    border: 1px solid black;
}
span.redacted {
    background-color: yellow;
    border: 1px solid black;
}
</style>")

(defn wrap-in-span
  "Wraps a string in a span of a selected class"
  [input class]
  (string/join ["<span class=\"" class "\">" input "</span>"]))

(defn latex-to-html-prepend-style
  "Adds the hardcoded class styles to the start of the HTML"
  [input]
  (string/join [html-style "\n\n" input]))

(defn latex-to-html-wrap-pre
  "Wraps the input in a <pre> tag"
  [input]
  (string/join ["<pre>" input "</pre>"]))

(defn latex-to-html-color-keywords
  "Adds font colors to all LaTeX keywords"
  [input]
  (reduce
   (fn [s kw] (string/replace s kw (wrap-in-span kw "keyword")))
   input
   latex/latex-keywords))

(defn latex-to-html-color-comments
  "Adds font colors to all LaTeX comments"
  [input]
  (string/replace input #"%.*" #(wrap-in-span % "comment")))

(defn latex-to-html-color-undefined
  "Adds font colors to all missing field inputs"
  [input]
  (string/replace input "[UNDEFINED]" (wrap-in-span "[UNDEFINED]" "undefined")))

(defn latex-to-html-color-redacted
  "Adds font colors to all post-sanitized inputs"
  [input]
  (string/replace input "[REDACTED]" (wrap-in-span "[REDACTED]" "redacted")))

(defn latex-to-html
  "Converts the TeX string into an HTML string"
  [input]
  (-> input
      (latex-to-html-wrap-pre)
      (latex-to-html-prepend-style)
      (latex-to-html-color-keywords)
      (latex-to-html-color-comments)
      (latex-to-html-color-undefined)
      (latex-to-html-color-redacted)))
