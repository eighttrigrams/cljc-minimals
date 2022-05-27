(ns main
  (:require [xtdb.api :as xt]))

(defn start-node []
  (xt/start-node 
   {:xtdb/index-store         
    {:kv-store {:xtdb/module 'xtdb.lmdb/->kv-store
                :db-dir      "data/index"}}
    :xtdb/document-store     
    {:kv-store {:xtdb/module 'xtdb.lmdb/->kv-store
                :db-dir      "data/docs"}}
    :xtdb/tx-log             
    {:kv-store {:xtdb/module 'xtdb.lmdb/->kv-store
                :db-dir      "data/log"}}
    :xtdb.lucene/lucene-store
    {:db-dir  "data/lucene"
     :indexer 'xtdb.lucene.multi-field/->indexer}}))

(def docs
  [{:pilot "Johanna",
    :craft "X-Wing"
    :xt/id 1}
   {:pilot "Lisa",
    :craft "Y-Wing"
    :xt/id 2}
   {:pilot "Linda",
    :craft "X-Wing",
    :xt/id 3}])

(defn submit [node docs]
  (xt/submit-tx node (for [doc docs]
                       [:xtdb.api/put doc])))

(defn search-text [node]
  (xt/q (xt/db node) '{:find [xt-id]
                       :where [[xt-id :craft "X-Wing"]
                               [(lucene-text-search "pilot: Li*") [[xt-id]]]]}))

(defn -main [& _args]
  (with-open [node (start-node)]
    (submit node docs)
    (let [results (map #(xt/entity (xt/db node) (get % 0)) 
                       (search-text node))]
      (prn results))))