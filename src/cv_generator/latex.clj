(ns cv-generator.latex
  (:require
   [clojure.string :as string]))

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

;; for the sneaky little buggers...
(def latex-dangerous-user-commands
  ["\\input"
   "\\include"
   "\\includegraphics"
   "\\usepackage"
   "\\openin"
   "\\read"
   "\\write"
   "\\closein"
   "\\closeout"
   "\\message"
   "\\errmessage"
   "\\csname"
   "\\endcsname"])

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

(defn or-undefined
  "Returns the string 'UNDEFINED' if the input string is empty"
  [input]
  (if (string/blank? input)
    "[UNDEFINED]"
    input))

(defn redact-dangerous-commands
  "Redacts any dangerous custom LaTeX command"
  [input]
  (if (some (partial string/includes? input) latex-dangerous-user-commands)
    "[REDACTED]"
    input))

(defn sanitize-input
  "Takes in the raw user input and sanitizes it for safe consumption"
  [input]
  (-> input
      (or-undefined)
      (redact-dangerous-commands)))

(defn build-latex
  "Takes all input parameters and returns the built TeX"
  [user-name user-title]
  (string/join
   [latex-header "\n\n"
    latex-packages "\n\n"
    latex-geometry "\n\n"
    latex-configs "\n\n"
    latex-begin-doc "\n\n"
    "Hello, my name is " (sanitize-input user-name)
    " and i am a " (sanitize-input user-title) ".\n\n"
    latex-end-doc "\n"]))
