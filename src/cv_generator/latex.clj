(ns cv-generator.latex
  (:require
   [clojure.string :as string]))

(def latex-keywords-type1
  ["\\documentclass"
   "\\usepackage"
   "\\begin"
   "\\end"
   "\\geometry"
   "\\titleformat"
   "\\titlespacing*"
   "\\setlist"
   "\\hypersetup"])

(def latex-keywords-type2
  ["\\\\"
   "\\textbf"
   "\\textit"
   "\\texttt"
   "\\hline"
   "\\hfill"
   "\\section"
   "\\subsection"])

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
   "\\endcsname"
   "\\loop"])

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
  "Takes in the raw user input (recurses if not atomic) and sanitizes it for safe consumption"
  [input]
  (let [sanitize-fn #(-> % (or-undefined) (redact-dangerous-commands))]
    (cond
      (not (coll? input)) (sanitize-fn input)
      (map? input) (into {} (map (fn [[k v]] [k (sanitize-input v)]) input))
      (sequential? input) (map sanitize-input input)
      (vector? input) (map sanitize-input input))))

(defn contact-entry
  "Returns a contact link string"
  [type info]
  (let [type-string
        (cond
          (= type "github")   "\\faGithub"
          (= type "email")    "\\faEnvelopeO"
          (= type "linkedin") "\\faLinkedinSquare"
          (= type "phone")    "\\faWhatsapp"
          (= type "location") "\\faMapMarker"
          :else "")]
    (string/join ["    \\hfill " type-string " " info "\n"])))

(defn contact-info
  "Builds the CV header"
  [name title contacts]
  (string/join
   ["\\begin{center}\n"
    "    \\textbf{" name "} \\\\\n"
    "    " title " \\\\[1.3em]\n"
    (string/join (map #(contact-entry (:type %) (:info %)) contacts))
    "    \\hfill\n"
    "\\end{center}\n"]))

(defn build-latex
  "Takes all input parameters and returns the built TeX"
  [user-name user-title user-contacts]
  (let [sanitized-name (sanitize-input user-name)
        sanitized-title (sanitize-input user-title)
        sanitized-contacts (sanitize-input user-contacts)]
    (string/join
     [latex-header "\n\n"
      latex-packages "\n\n"
      latex-geometry "\n\n"
      latex-configs "\n\n"
      latex-begin-doc "\n\n"
      (contact-info sanitized-name sanitized-title sanitized-contacts) "\n"
      latex-end-doc "\n"])))
