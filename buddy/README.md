# buddy

An api using [funcool/buddy-auth](https://github.com/funcool/buddy-auth) for jwt authentication.
A rights management system can be developed using this example as a starting point.

To start the server, run

    $ clj -M -m main

Then post

```bash
$ curl -XPOST -H 'Content-Type: application/json'\
  -d '{ "name": "dan", "pass": "key" }'\
  localhost:3000/login
eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiZGFuIn0.evk61798xhDZxw1ZPDr7ff_TGRNyqS3vgNbPZXe4O9E
```

and you'll receive a token. Passing in the wrong credentials will yield nothing.

Use the token you received to show your permissions:

```bash
$ curl -H \
  'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiZGFuIn0.evk61798xhDZxw1ZPDr7ff_TGRNyqS3vgNbPZXe4O9E'\
  localhost:3000
{"permissions":"all"}
```

Note that using a wrong token (try modifying or omitting it) will say permissions are "none", 
while "all" permissions will be the response to presenting a correct token.