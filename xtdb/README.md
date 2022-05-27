# xtdb

This demonstrates the usage of [xtdb](https://github.com/xtdb/xtdb) with a persistent (not in-memory) storage. The selected underlying datastores are `lmdb` and `lucene`, the latter of which is used in the example to demonstrate full-text search.

Run

    $ clj -M -m main

The example data gets persisted in the `data` folder, so delete
it to reset the db before you run `main` again.

