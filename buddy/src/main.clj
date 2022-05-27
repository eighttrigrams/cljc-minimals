(ns main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            auth
            middleware))

;; this should come from an external data source
(def config {:secret      "adskjfasl jlsjf"
             :users       {"dan" "key"}
             :permissions {"dan"       "all"
                           "anonymous" "none"}})

(def wrap-permissions (middleware/wrap-permissions
                       (auth/unsign (:secret config))
                       (:permissions config)))

(defn handle-regular-request
  [req]
  (select-keys req [:permissions]))

(defn- handle-request
  [request]
  (if (= (:uri request) "/login")
    {:status 200
     :body   ((auth/login (:users config)
                          (:secret config)) request)}
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body ((wrap-permissions handle-regular-request) request)}))

(def handler
  (-> handle-request
      wrap-json-response
      (wrap-json-body {:keywords? true})))

(defn -main
  [& _args]
  (jetty/run-jetty handler {:port 3000}))