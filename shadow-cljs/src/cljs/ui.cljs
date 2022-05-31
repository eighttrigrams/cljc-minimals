(ns ui
  (:require [ajax.core :refer [POST]]))

(defn fetch []
  (POST "/api" {:body (.stringify js/JSON (clj->js {:msg "hi"}))
                :headers {"Content-Type" "application/json"}
                :handler (fn [resp] (prn "Response from backend:" resp))
                :error-handler (fn [resp] (prn "Error response:" resp))}))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (fetch)
  (js/console.log "start"))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
