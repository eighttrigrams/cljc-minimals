# component

Demonstrates the usage of the library `stuartsierra/component`.

- Source and documentation can be found here: [stuartsierra/component](https://github.com/stuartsierra/component)
- This example is derived from [seancorfield/usermanager-example](https://github.com/seancorfield/usermanager-example)
- See also ["Reloaded"-Workflow](https://cognitect.com/blog/2013/06/04/clojure-workflow-reloaded)

The purpose of this workflow is to 
orderly clean up the namespaces during a REPL session
after changes have been made. Orderly in this case means that not only are changed symbols recognized, but also unused ones eliminated. 

Within a REPL session,
after a file has been changed one would call `(reset)` and then continue working.

## Getting started

Fire up a REPL

    $ clj
    user=> (reset)
    Starts Config and Application
    user=> (reset)
    Stops Config and Application,
    then restarts Config and Application
    user=> (stop)
    Stops Config and Application
    user=> (stop)
    Does nothing
    user=> (start)
    Starts Config and Application

Now make some change to one or more namespaces and observe the changes taking effect after calling `(reset)`.