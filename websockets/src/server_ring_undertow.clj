(ns server-ring-undertow
  (:require [clojure.java.io :as io]
            [ring.adapter.undertow :refer [run-undertow]]
            [ring.adapter.undertow.websocket :as ws]
            [ring.middleware.resource :refer [wrap-resource]]))

(defn ws-handler [_request]
  {:undertow/websocket
   {:on-open (fn [{:keys [_channel]}] (println "WS open!"))
    :on-message (fn [{:keys [channel data]}] (ws/send data channel))
    :on-close   (fn [{:keys [_channel _ws-channel]}] (println "WS closed!"))}})

(defn resource-handler [req]
  {:status 200
   :body (slurp (io/resource
                 (if (= "/" (:uri req))
                   "index.html"
                   (subs (:uri req) 1))))})

(defn app [req]
  (if (= "/ws" (:uri req))
    (ws-handler req)
    (resource-handler req)))

(defn -main [& _args]
  (run-undertow (wrap-resource app "public") {:port 4000}))