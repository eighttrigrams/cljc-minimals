(ns env
  (:require [ring.middleware.reload :as reload]))

(def wrap-env-defaults reload/wrap-reload)