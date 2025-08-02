(ns cv-generator.latex
  (:require
   [clojure.string :as string]))

(defn or-undefined
  "Returns the string 'UNDEFINED' if the input string is empty"
  [input]
  (if (string/blank? input)
    "UNDEFINED"
    input))

(def latex-keywords
  ["\\documentclass"
   "\\usepackage"
   "\\begin"
   "\\end"
   "\\titleformat"
   "\\titlespacing*"
   "\\setlist"])

(def latex-header
  "\\documentclass[10pt]{article}")

(def latex-packages
"% ===== PACKAGES =====
\\usepackage[margin=0.5in]{geometry}
\\usepackage{enumitem}
\\usepackage[colorlinks=true, pdfborder={0,0,0}, urlcolor=blue]{hyperref}
\\usepackage{tabularx}
\\usepackage{fontawesome}
\\usepackage{titlesec}")

(def latex-configs
  "% ===== CONFIGS =====
\\titleformat{\\section}{\\large\\bfseries\\scshape}{\\thesection}{1em}{}[\\titlerule]
\\titlespacing*{\\section}{0pt}{1.5em}{0.5em}
\\setlist[itemize]{leftmargin=*, nosep}")

(def latex-begin-doc
  "% +==================+
% |  DOCUMENT BEGIN  |
% +==================+
\\begin{document}")

(def latex-end-doc
  "\\end{document}")

(defn build-latex
  "Takes all input parameters and returns the built TeX"
  [user-name]
  (string/join
   [latex-header "\n\n"
    latex-packages "\n\n"
    latex-configs "\n\n"
    latex-begin-doc "\n"
    "    Hello, my name is: " (or-undefined user-name) "\n"
    latex-end-doc "\n"]))

(def html-style
  "<style>
.keyword {
    color: blue;
    font-weight: bold;
}
.comment {
    opacity: 0.5;
    font-weight: bold;
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
   latex-keywords))

(defn latex-to-html-color-comments
  "Adds font colors to all LaTeX comments"
  [input]
  (string/replace input #"%.*" #(wrap-in-span % "comment")))

(defn latex-to-html
  "Converts the TeX string into an HTML string"
  [input]
  (-> input
      (latex-to-html-wrap-pre)
      (latex-to-html-prepend-style)
      (latex-to-html-color-keywords)
      (latex-to-html-color-comments)))
