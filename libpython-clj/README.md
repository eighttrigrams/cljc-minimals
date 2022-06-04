# libpython-clj

Demonstrates how to use [keras](https://keras.io/) with **Clojure**, using [clj-python/libpython-clj](https://github.com/clj-python/libpython-clj).

Training batches are preprocessed in *Clojure* and get send one after another
to the Keras model running inside the *Python* subsystem.

## Getting started

Install the Python package `tensorflow`.

Download `mnist_test.csv` and `mnist_train.csv` from [here](https://pjreddie.com/projects/mnist-in-csv/) and put them into the top level directory.

Run

    $ clj -M -m mnist