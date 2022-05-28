(ns db.people
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "db/people.sql")
