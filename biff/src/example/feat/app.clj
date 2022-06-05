(ns example.feat.app
  (:require [com.biffweb :as biff]
            [example.ui :as ui]
            [xtdb.api :as xt]))

(defn set-foo [{:keys [params] :as req}]
  (biff/submit-tx req
                  [{:db/op :update
                    :db/doc-type :user
                    :xt/id "ID"
                    :user/foo (:foo params)}])
  {:status 303
   :headers {"location" "/app"}})

(defn bar-form [{:keys [value]}]
  (biff/form
   {:hx-post "/app/set-bar"
    :hx-swap "outerHTML"}
   [:label.block {:for "bar"} "Bar: "
    [:span.font-mono (pr-str value)]]
   [:.h-1]
   [:.flex
    [:input.w-full#bar {:type "text" :name "bar" :value value}]
    [:.w-3]
    [:button.btn {:type "submit"} "Update"]]
   [:.h-1]
   [:.text-sm.text-gray-600
    "This demonstrates updating a value with HTMX."]))

(defn set-bar [{:keys [params] :as req}]
  (biff/submit-tx req
                  [{:db/op :update
                    :db/doc-type :user
                    :xt/id "ID"
                    :user/bar (:bar params)}])
  (biff/render (bar-form {:value (:bar params)})))

(defn app [{:keys [biff/db] :as req}]
  
  (let [entity (xt/entity db "ID")]
    (when-not entity
      (biff/submit-tx req
                      [{:db/doc-type :user
                        :xt/id "ID"}])))

  (let [{:user/keys [foo bar]} (xt/entity db "ID")]
    (ui/page
     {}
     nil
     [:.h-6]
     (biff/form
      {:action "/app/set-foo"}
      [:label.block {:for "foo"} "Foo: "
       [:span.font-mono (pr-str foo)]]
      [:.h-1]
      [:.flex
       [:input.w-full#foo {:type "text" :name "foo" :value foo}]
       [:.w-3]
       [:button.btn {:type "submit"} "Update"]]
      [:.h-1]
      [:.text-sm.text-gray-600
       "This demonstrates updating a value with a plain old form."])
     [:.h-6]
     (bar-form {:value bar})
     [:.h-6])))


(def features
  {:routes ["/app" {}
            ["" {:get app}]
            ["/set-foo" {:post set-foo}]
            ["/set-bar" {:post set-bar}]]})
