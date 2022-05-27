(ns resources
  (:require [clojure.string :as str]
            [mount.core :as mount]))

(mount/defstate resources
  :start [{:id   1
           :name "one"}
          {:id   2
           :name "two"}
          {:id   3
           :name "three"}]
  :stop 0)

(defn get-resources [q]
  {:result (->> resources
                (remove #(when (not= q "")
                           (not (str/starts-with? (:name %) q)))))})