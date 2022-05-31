(ns server
  (:require [ring.adapter.jetty :as j]
            [compojure.core :as c]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.json :as json]))

(defn api-handler [{{msg :msg} :body}]
  {:body {:echo msg}})

(defn wrap-api [handler]
  (-> handler 
      json/wrap-json-response 
      (json/wrap-json-body {:keywords? true})))

(c/defroutes app
  (c/POST "/api" [] (wrap-api api-handler))
  (c/GET "/" [] (resp/redirect "/index.html"))
  (route/resources "/"))

(j/run-jetty app {:port 3000})