(ns main
  (:require
   [luminus.http-server :as http]
   [home :refer [routes]]
   [reitit.ring :as ring]
   [ring.adapter.undertow.middleware.session :refer [wrap-session]]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defn wrap-base [handler]
  (-> handler
      (wrap-session {:cookie-attrs {:http-only true}})
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security] false)
           (dissoc :session)))))

(def app-routes
  (ring/ring-handler
   (ring/router
    [(routes)])))

(defn -main [& _args]
  (http/start
   {:handler (wrap-base #'app-routes)
    :port 3000}))
