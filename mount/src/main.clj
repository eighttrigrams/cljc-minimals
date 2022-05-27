(ns main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [mount.core :as mount]
            [env :refer [wrap-env-defaults]]
            resources))

(defn dispatch-request
  [{{q     :q} :body}]
  (resources/get-resources q))

(defn- handle-request
  [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (dispatch-request request)})

(def handler
  (-> handle-request
      wrap-env-defaults
      wrap-json-response
      (wrap-json-body {:keywords? true})))

(mount/defstate ^{:on-reload :noop} http-server
  :start
  (future (jetty/run-jetty handler {:port 3000}))
  :stop 0)

(defn -main
  [& _args]
  (prn (mount/start))
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(prn (mount/stop))))
  (deref http-server))