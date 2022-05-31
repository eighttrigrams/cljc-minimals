(ns main
  (:require [next.jdbc :as jdbc]
            [clojure.set :as set]))

(def ds
  {:dbtype    "postgresql"
   :dbname    "exampledb"
   :user      "dan"
   :password  "key"
   :port      4444
   :hostname  "127.0.0.1"})

(defn reset-people-table []
  (jdbc/execute! ds 
                 ["DROP TABLE IF EXISTS pets;
                   DROP TABLE IF EXISTS people"])
  (jdbc/execute! ds 
                 ["CREATE TABLE people (id SERIAL PRIMARY KEY, 
                   age smallint, 
                   name varchar(32))"])
  (jdbc/execute! ds
                 ["CREATE TABLE pets (id SERIAL, 
                   name varchar(32),
                   owner_id INT NOT NULL,
                   CONSTRAINT fk_person FOREIGN KEY(owner_id)
                   REFERENCES people(id)
                   )"]))

(defn add-person [name age]
  (jdbc/execute! ds 
                 [(str "INSERT INTO people(name, age) 
                        VALUES('" name "'," age ")")] 
                 {:return-keys true}))

(defn add-pet [person-id name]
  (jdbc/execute! ds
                 [(str "INSERT INTO pets(name, owner_id) 
                        VALUES('" name "'," person-id ")")]
                 {:return-keys true}))

(defn list-people []
  (jdbc/execute! ds ["SELECT * FROM people"]))

(defn list-pets []
  (jdbc/execute! ds ["SELECT * FROM pets JOIN people
                      ON pets.owner_id = people.id"]))

(defn -main [& _args]
  (reset-people-table)
  (let [lisa  (:people/id (first (add-person "Lisa" 28)))
        linda (:people/id (first (add-person "Linda" 29)))]
    (add-pet lisa "Dog")
    (add-pet linda "Cat")
    (prn 
     (->> 
      (list-people)
      (map #(select-keys % [:people/name :people/age]))
      (map #(set/rename-keys % {:people/name :person/name
                                :people/age  :person/age}))))
    (prn
     (->> 
      (list-pets)
      (map #(select-keys % [:pets/name :people/name]))
      (map #(set/rename-keys % {:pets/name   :pet/name
                                :people/name :role/owner}))))))