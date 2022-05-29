# next-jdbc

Demonstrates usage of [seancorfield/next-jdbc](https://github.com/seancorfield/next-jdbc).
Uses a `postgres` database as persistent storage.

You will need to have a running `postgres` or otherwise `docker` installed to run this example. For running postgres within docker, use the following command.

```bash
$ docker run \
--rm -it \
-p 4444:5432 \
--volume postgres-data:/var/lib/postgresql/data \
--name postgres \
-e POSTGRES_USER=dan \
-e POSTGRES_DB=exampledb \
-e POSTGRES_PASSWORD=key \
postgres:11.3
```

To run the example, use

    $ clj -M -m main

To have a peek into the db after running the example use

    $ psql -h localhost -p 4444 -U dan -d exampledb # will ask for password (use 'key')
