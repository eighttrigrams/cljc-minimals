(ns main
  (:gen-class)
  (:require [db.people :as people]
            [clojure.java.jdbc]))

(def db
  {:dbtype "postgresql"
   :classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :dbname "exampledb"
   :user "dan"
   :password "key"
   :port 4444
   :hostname "127.0.0.1"})

(defn -main []
  (people/drop-people-table-if-exists db)
  (people/create-people-table db)
  (people/insert-person db {:name "Lisa" :pet "Dog"})
  (people/insert-person db {:name "Linda" :pet "Cat"})
  (prn (map #(select-keys % [:name :pet]) (people/list-people db))))
