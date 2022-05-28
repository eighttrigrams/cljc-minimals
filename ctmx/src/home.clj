(ns home
  (:require
   [ctmx.core :as ctmx]
   [ctmx.render :as render]
   [hiccup.page :refer [html5]]))

(defn html5-response
  ([body]
   {:status 200
    :headers {"Content-Type" "text/html"}
    :body
    (html5
     [:head]
     [:body (render/walk-attrs body)]
     [:script {:src "https://unpkg.com/htmx.org@1.5.0"}])}))

(ctmx/defcomponent ^:endpoint button [req ^:int clicks]
  [:button {:hx-post "button"
            :hx-swap "outerHTML"
            :hx-vals {:clicks (inc clicks)}}
   "Clicks: " clicks])

(defn routes []
  (ctmx/make-routes
   "/"
   (fn [req]
     (html5-response
      (button req 0)))))
