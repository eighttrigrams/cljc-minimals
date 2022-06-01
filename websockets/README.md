# shadow-cljs - browser quickstart

Demonstrates how to use websockets with [http-kit](https://github.com/http-kit/http-kit) and [ring-undertow-adapter](https://github.com/luminus-framework/ring-undertow-adapter).

## Preparations

    $ npm i

## Getting started

    1$ clj -X:run-http-kit
    1$ clj -X:run-ring-undertow # alternatively
    3$ npx shadow-cljs watch app

Open [http://localhost:4000](http://localhost:4000). 

## Packaging

    $ npx shadow-cljs release app
    $ clojure -M -m uberdeps.uberjar --target websockets.jar
    $ java -cp websockets.jar clojure.main -m server-http-kit
    $ java -cp websockets.jar clojure.main -m server-ring-undertow # alternatively
