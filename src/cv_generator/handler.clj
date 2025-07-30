(ns cv-generator.handler
  (:require
   [compojure.core :refer :all]
   [compojure.route :as route]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (POST "/api/submit"
        request
        (do
          (println "input text sent!")
          {:status 200
           :headers {"Content-Type" "text/html"}
           :body "Roger Roger!"}))
  (GET "/"
       request
       (do
         (println "site accessed!")
         (ring.util.response/redirect "/index.html")))
  (route/resources "/")
  (route/not-found {:status 404
                    :headers {"Content-Type" "text/html"}
                    :body "<b><font color=\"red\">Not Found</font></b>"}))

(def app
  (wrap-defaults
   app-routes
   ;; disable CSRF token validators, because i'm lazy
   (assoc-in site-defaults [:security :anti-forgery] false)))
