(ns server-http-kit
  (:require [clojure.java.io :as io]
            [org.httpkit.server :as s]))

(defn ws-handler [request]
  #_{:clj-kondo/ignore [:unresolved-symbol]}
  (s/with-channel request channel
    (let [_a (get (:headers request) "sec-websocket-key")]
      (s/on-close channel (fn [status] (println "channel closed: " status)))
      (s/on-receive channel (fn [data] (s/send! channel data))))))

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
  (s/run-server app {:port 4000}))