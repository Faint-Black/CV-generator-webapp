(ns cv-generator.handler
  (:require
   [clojure.string :as string]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (POST "/api/submit"
        request
        (let [input-text (:text (:body request))
              output-text (string/upper-case input-text)]
          {:status 200
           :headers {"Content-Type" "text/html"}
           :body output-text}))
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
