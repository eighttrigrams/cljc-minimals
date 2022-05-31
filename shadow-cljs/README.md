# shadow-cljs

- Based on [shadow-cljs/quickstart-browser](https://github.com/shadow-cljs/quickstart-browser)
- In addition to it it has a simple [ring](https://github.com/ring-clojure/ring)- and [compojure](https://github.com/weavejester/compojure)-based backend
and also shows  how packaging of the complete app can be done
- It is an ideal complement to the [day8/re-frame-template](https://github.com/day8/re-frame-template) which is also uses `shadow-cljs` and then [reagent](https://github.com/reagent-project/reagent) and [re-frame](https://github.com/day8/re-frame)

## Getting started

Prepare

```bash
$ npm i
```
and run

```bash
1$ clj -M -m server
2$ npx shadow-cljs watch app
visit localhost:8280
```

On the js console you should be able to see that json data from the backend got fetched. During development the backend runs at
`localhost:3000` and requests from the frontend get send its
way by using shadow-cljs's `:proxy-url`.

## Package and run

    $ npx shadow-cljs release app
    $ clj -M -m uberdeps.uberjar --target server.jar
    $ java -cp server.jar clojure.main -m server
    visit localhost:3000
