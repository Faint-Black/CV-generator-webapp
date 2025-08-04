(ns cv-generator.handler
   (:require
    [cv-generator.latex :as latex]
    [cv-generator.html :as html]
    [clojure.string :as string]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
   (:import
    [java.time LocalDateTime]
    [java.time.format DateTimeFormatter]))

(defn log-input
  "Returns a log string of the JSON input"
  [json-input]
  (let [unparsed-time (LocalDateTime/now)
        parsing-format (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm")
        parsed-time (.format unparsed-time parsing-format)]
    (str "LOG (" parsed-time "):\n" json-input)))

(defroutes app-routes
  (POST "/api/submit"
        request
        (let [json-response (:body request)
              user-name (:name json-response)
              user-title (:title json-response)
              user-contacts (:contacts json-response)
              output-latex (latex/build-latex user-name user-title user-contacts)
              output-html (html/latex-to-html output-latex)]
          (do
            (println (log-input json-response))
            {:status 200
             :headers {"Content-Type" "text/html"}
             :body output-html})))
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
