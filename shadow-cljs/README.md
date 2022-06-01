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
```

For the sake of example, two options are now given. First you can visit the running app in a browser at `localhost:8280`, second you
can visit it at `localhost:3000`. In both cases you should be able to see in the js console that json data from the backend got fetched. Note also that in both cases hot-code-reloading via shadow-cls works just fine.

The first option (port 8280) demonstrates the use of shadow-cljs's `:proxy-url` configuration option (see [shadow-cljs.edn](./shadow-cljs.edn))

```clojure
:dev-http
{8280 {:root      "resources/public"
       :proxy-url "http://localhost:3000"}}
```

Calls from the frontend to the api `localhost:3000/api` get delegated to the backend. Removing the `:dev-http` configuration would leave us in any case with option 2.

The second option (port 3000) provides the api as well as the static resources, which include the output of shadow-cljs. In fact that is 
exactly how the app behaves when packaged.

## Package and run

Do the following:

    $ npx shadow-cljs release app
    $ clj -M -m uberdeps.uberjar --target server.jar
    $ java -cp server.jar clojure.main -m server
    visit localhost:3000

## Clean

Run

    $ rm -rf resources/public/js/compiled