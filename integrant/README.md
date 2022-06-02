# integrant

Uses [weavejester/integrant](https://github.com/weavejester/integrant) and [weavejester/integrant-repl](https://github.com/weavejester/integrant-repl).

## Getting started

Start the application with `clj -M -m main` and visit [localhost:8888](http://localhost:8888).

## REPL

Start the REPL. Then start the system via

```clojure
(prep)
:prepped
(init)
:initiated
(reset)
:reloading ()
;; change and save main.clj
(reset)
:reloading (main user)
:resumed
(halt)
:halted
```