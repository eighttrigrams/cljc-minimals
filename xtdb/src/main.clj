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

(def batch1
  [{:xt/id 1,
    :pilot/name "Johanna"
    :craft/name "X-Wing"}
   {:xt/id 2,
    :pilot/name "Lisa"
    :craft/name "Y-Wing"}
   {:xt/id 3,
    :pilot/name "Linda",
    :craft/name "X-Wing"}])

(def batch2
  [{:xt/id 1
    :pilot/name "Johanna"
    :craft/id 4}
   {:xt/id 2
    :pilot/name "Lisa"
    :craft/id 5}
   {:xt/id 3
    :pilot/name "Linda"
    :craft/id 4}
   {:xt/id 4
    :craft/wing-shape "X"
    :craft/name "X-Wing"}
   {:xt/id 5
    :craft/wing-shape "Y"
    :craft/name "Y-Wing"}])

(defn submit [node docs]
  (xt/submit-tx node (for [doc docs]
                       [:xtdb.api/put doc])))

(defn search1 [node]
  (xt/q (xt/db node) 
        '{:find  [p]
          :where [[p :craft/name "X-Wing"]
                  [(lucene-text-search "pilot\\/name: Li*") [[p]]]]}))

(defn search2 [node]
  (xt/q (xt/db node) 
        '{:find  [n wing-shape]
          :where [[c :craft/wing-shape wing-shape]
                  [c :craft/name "X-Wing"]
                  [p :craft/id c]
                  [p :pilot/name n]
                  [(lucene-text-search "pilot\\/name: Li*") [[p]]]]}))

(defn -main [& _args]
  (with-open [node (start-node)]
    (submit node batch1)
    (xt/sync node)
    (prn (xt/entity (xt/db node) 1))
    (prn (map #(xt/entity (xt/db node) (get % 0))
              (search1 node)))
    (submit node batch2)
    (xt/sync node)
    (prn (search2 node))))