(ns cv-generator.handler
  (:require
   [cv-generator.latex :as latex]
   [cv-generator.html :as html]
   [cv-generator.compile :as texcompile]
   [clojure.string :as string]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import
   [java.time LocalDateTime]
   [java.time.format DateTimeFormatter]))

(defn get-time-string
  "Returns the current timestamp in the 2025-01-01-12h59m59s format"
  []
  (let [unparsed-time (LocalDateTime/now)
        parsing-format (DateTimeFormatter/ofPattern "yyyy-MM-dd-HH'h'mm'm'ss's'")
        parsed-time (.format unparsed-time parsing-format)]
    parsed-time))

(defn log-submit
  "Returns a log string of the JSON input"
  [json-input time]
  (str "LOG[submit] (" time "):\n" json-input))

(defn log-compile
  "Returns a log string of a compilation request"
  [json-input time]
  (str "LOG[compile] (" time "):\n" json-input))

(defroutes app-routes
  (POST "/api/submit"
        request
        (let [current-time (get-time-string)
              json-response (:body request)
              output-latex (latex/build-latex json-response)
              output-html (html/latex-to-html output-latex)]
          (do
            (println (log-submit json-response current-time))
            {:status 200
             :headers {"Content-Type" "text/html"}
             :body output-html})))
  (POST "/api/compile"
        request
        (let [current-time (get-time-string)
              json-response (:body request)
              output-latex (latex/build-latex json-response)]
          (do
            (println (log-compile json-response current-time))
            (let [comp-result (texcompile/compile-latex output-latex current-time)
                  comp-status (texcompile/compile-ok comp-result)]
              (if (:success comp-status)
                {:status 200
                 :headers {"Content-Type" "text/html"}
                 :body (:message comp-status)}
                {:status 200
                 :headers {"Content-Type" "text/html"}
                 :body (:message comp-status)})))))
  (GET "/" request (ring.util.response/redirect "/index.html"))
  (route/resources "/")
  (route/not-found {:status 404
                    :headers {"Content-Type" "text/html"}
                    :body "<b><font color=\"red\">Not Found</font></b>"}))

(def app
  (wrap-defaults
   ;; convert raw HTTP streams into JSON maps
   (wrap-json-response (wrap-json-body app-routes {:keywords? true}))
   ;; disable CSRF token validators, because i'm lazy
   (assoc-in site-defaults [:security :anti-forgery] false)))
