(ns example.schema
  (:require [malli.core :as malc]
            [malli.registry :as malr]))

(def schema
  {:user/id :string
   :user/foo :string
   :user/bar :string
   :user [:map {:closed true}
          [:xt/id :user/id]
          [:user/foo {:optional true}]
          [:user/bar {:optional true}]]})

(def malli-opts {:registry (malr/composite-registry malc/default-registry schema)})
