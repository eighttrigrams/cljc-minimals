(ns user
  #_{:clj-kondo/ignore [:unused-referred-var]}
  (:require [integrant.repl :refer [clear go halt prep init reset reset-all]]
            main))

(integrant.repl/set-prep! (constantly main/config))