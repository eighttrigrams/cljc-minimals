# mount

A json api using mount

## TODOs

- Describe the project and its components and goals here
- Allow to (gracefully) shutdown only server with `(mount.core/stop [#'main/http-server])`
- Mention how reload works and how one can test it (resources.clj!)
- Mention dev/env.clj and prod/env.clj and that this idea came from luminus

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

## Usage

Post `{ "q": "" }` to `localhost:3000` to see available and matching resources.
