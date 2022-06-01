(ns client)

(defonce sock (atom nil))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (-> js/document
      (.getElementById "b1")
      (.-onclick)
      (set! (fn [_e]
              (when @sock (.send @sock "Hi")))))
  (js/console.log "start!!"))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init!!")
  (let [socket (js/WebSocket. (str "ws://" js/location.host "/ws"))]
    (set! (.-onopen socket) (fn [_e] #__))
    (set! (.-onmessage socket) (fn [_e]
                                 (-> js/document
                                       (.getElementById "h1")
                                       (.-innerHTML)
                                       (set! (.-data _e)))))
                                 
    (reset! sock socket)
  (start)))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
