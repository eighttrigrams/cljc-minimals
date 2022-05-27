(ns main
  (:require [com.stuartsierra.component :as component]))

(defrecord Config [conf      ; state
                   settings] ; parameters
  component/Lifecycle
  (start [this]
         (if conf
           this
           (do
             (prn "Starting Config" this)
             (assoc this :conf settings))))
  (stop  [this]
         (if-not conf
           this
           (do (prn "Stopping Config" this)
               (assoc this :conf nil)))))

(defrecord Application [config ; dependency
                        app]   ; state
  component/Lifecycle
  (start [this]
         (if app
           this
           (do
             (prn "Starting Application" this)
             (assoc this :app "APPLICATION"))))
  (stop  [this]
         (if-not app
           this
           (do (prn "Stopping Application" this)
               (assoc this :app nil)))))

(defn new-system []
  (component/system-map 
   :application
   (component/using (map->Application {}) [:config])
   :config
   (map->Config {:settings "SETTINGS"})))