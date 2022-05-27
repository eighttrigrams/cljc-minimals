# mount

A json api using [tolitius/mount](https://github.com/tolitius/mount). This example is derived from a [Luminus](https://github.com/luminus-framework/luminus) template project.

A couple of things I liked particularly about this setup and 
replicated them from scratch. One thing is the combination
of `wrap-reload` and `mount`, which makes it possible that
on each request not only get modified sources recompiled, but
also the necessary subsystems restarted, which I found very neat!
Another thing was how in the `dev` folder namespace variants
are introduced for the different environments which make it that
`wrap-reload` is only part of the middleware pipeline in dev mode.

## Getting started

This

    $ clj -X:run

will run a webserver at `localhost:3000`.

### Develop

Run

    $ clj -M:dev

This

```
(require 'main)
(require 'mount.core)
(mount.core/start)
```

will get you a webserver running at `localhost:3000`.

Next, query the api with

```bash
$ curl -XPOST -H 'Content-Type: application/json' -d '{"q": ""}' localhost:3000
```

Now, change one of the resource names in `src/resources.clj` and see
that the result will reflect that change on the next call. You'll also be
able to observe that the resources component will have restarted.
