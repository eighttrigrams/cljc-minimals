(ns server
  (:require [ring.adapter.jetty :as j]
            [compojure.core :refer [defroutes GET]]
            [ring.util.response :as response]
            [ring.middleware.resource :refer [wrap-resource]]))

(defn api-handler [{_ :body}]
  {:body "<div>Hello, world!</div>"})

(defroutes routes
  (GET "/greet" [] api-handler)
  (GET "/" [] (response/resource-response "public/index.html")))

(def app
  (-> routes
      (wrap-resource "public")))

(j/run-jetty app {:port 3000})