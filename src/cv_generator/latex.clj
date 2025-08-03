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
   "\\geometry"
   "\\titleformat"
   "\\titlespacing*"
   "\\setlist"
   "\\hypersetup"])

(def latex-header
  "\\documentclass[10pt]{article}")

(def latex-packages
  "% ===== PACKAGES =====
\\usepackage{geometry}
\\usepackage{enumitem}
\\usepackage{hyperref}
\\usepackage{tabularx}
\\usepackage{fontawesome}
\\usepackage{titlesec}")

(def latex-geometry
  "%% ===== PAGE GEOMETRY =====
\\geometry{
  a4paper,
  top=10mm,
  bottom=10mm,
  left=15mm,
  right=15mm
}")

(def latex-configs
  "% ===== SETUP CONFIGS =====
\\titleformat{\\section}{\\large\\bfseries\\scshape}{\\thesection}{1em}{}[\\titlerule]
\\titlespacing*{\\section}{0pt}{1.5em}{0.5em}
\\setlist[itemize]{leftmargin=*, nosep}
\\hypersetup{colorlinks=true, urlcolor=blue}")

(def latex-begin-doc
  "% +==================+
% |  DOCUMENT BEGIN  |
% +==================+
\\begin{document}")

(def latex-end-doc
  "\\end{document}")

(defn build-latex
  "Takes all input parameters and returns the built TeX"
  [user-name user-title]
  (string/join
   [latex-header "\n\n"
    latex-packages "\n\n"
    latex-geometry "\n\n"
    latex-configs "\n\n"
    latex-begin-doc "\n\n"
    "Hello, my name is " (or-undefined user-name)
    " and i am a " (or-undefined user-title) ".\n\n"
    latex-end-doc "\n"]))
