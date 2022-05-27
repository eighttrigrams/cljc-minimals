(ns middleware
  (:require [clojure.string :as str]))

(defn wrap-permissions [unsign:fn permissions]
  (fn [handler]
    (fn [request]
      (let [user (try
                   (let [auth-header (get (:headers request) "authorization")]
                     (:user (unsign:fn (str/replace auth-header "Bearer " ""))))
                   (catch Exception _e "anonymous"))
            request (assoc request :permissions (get permissions user))]
        (handler request)))))