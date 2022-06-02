(ns main
  (:require [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [ring.util.response :as response]))

(def config
  {:adapter/jetty {:port 8888 :handler (ig/ref :handler/greet)}
   :handler/greet  {:name "Alice"}})

(defmethod ig/init-key :adapter/jetty [_ {:keys [handler] :as opts}]
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))


(defmethod ig/halt-key! :adapter/jetty [_ server]
  (.stop server))

(defmethod ig/init-key :handler/greet [_ {:keys [name]}]
  (fn [_] (response/response (str "Hello " name))))

(defn -main [& _args]
  (ig/init config))