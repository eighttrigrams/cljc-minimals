(ns main
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [selmer.parser :as tmpl]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response content-type]]))

(defn render-page
  [req]
  (let [data (:params req)
        view (:view req "home")
        html (tmpl/render-file (str "views/" view ".html") data)]
    (-> (response (tmpl/render-file "layouts/page.html"
                                         (assoc data :body [:safe html])))
        (content-type "text/html"))))

(defn get-items
  [req]
  (let [items ["a" "b" "c"]]
    (-> req
        (assoc-in [:params :items] items)
        (assoc :view "list"))))

(defn render-page-middleware
  [handler]
  (fn [req]
    (let [resp (handler req)]
      (render-page resp))))

(defroutes my-handler
  (GET  "/" [] (constantly (response "ok")))
  (GET  "/list" [] get-items)
   (route/resources "/"))
  (route/not-found "Not Found")

(defn -main
  [& _args]
  (run-jetty (-> my-handler render-page-middleware)
             {:port 8888}))
