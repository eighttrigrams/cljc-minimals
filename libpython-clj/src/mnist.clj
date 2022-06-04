(ns mnist
  (:require [libpython-clj2.python
                    :refer [call-attr initialize!
                            import-module]]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn load-mnist-set [name batch-size]
  (let [test (-> name slurp str/split-lines)
        [xss ys] (reduce (fn [[xss ys] line]
                           (let [split (str/split line #",")
                                 xs    (map (fn [item] (float (/ (edn/read-string item) 255)))
                                            (drop 1 split))
                                 y     (edn/read-string (first split))]
                             [(conj xss xs) (conj ys y)]))
                         [[] []] test)]
    (map vector (partition batch-size xss) (partition batch-size ys))))

(defn train! []
  (let [mnist-module (import-module "src.mnist")
        batch-size 128
        batches (load-mnist-set "mnist_train.csv" batch-size)
        model (call-attr mnist-module "model")]
    (dotimes [epoch 15]
      (prn "Epoch: " epoch)
      (doall (map (fn [[xss ys]]
                    (call-attr mnist-module "train" model xss ys)) batches)))))

(defn -main []
  (initialize!)
  (train!))