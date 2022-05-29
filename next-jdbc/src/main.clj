(ns main
  (:require [next.jdbc :as jdbc]))

(def ds
  {:dbtype    "postgresql"
   :dbname    "exampledb"
   :user      "dan"
   :password  "key"
   :port      4444
   :hostname  "127.0.0.1"})

(defn reset-people-table []
  (jdbc/execute! ds ["
DROP TABLE IF EXISTS people
"])

  (jdbc/execute! ds ["
CREATE TABLE people (
  id SERIAL,
  name varchar(32),
  pet varchar(32)
)"]))

(defn add-person [name pet]
  (jdbc/execute! ds [(str "
INSERT INTO people(name, pet)
VALUES('" name "','" pet "')")
]))

(defn list-people []
  (jdbc/execute! ds ["select * from people"]))

(defn -main [& _args]
  (reset-people-table)
  (add-person "Lisa" "Dog")
  (add-person "Linda" "Cat")
  (prn
   (map #(select-keys % [:people/name :people/pet]) 
        (list-people))))