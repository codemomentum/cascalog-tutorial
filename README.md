# cascalog-tutorial

Cascalog Tutorial for the meetup

## Usage

lein uberjar

hadoop jar target/cascalog-tutorial-0.1.0-SNAPSHOT-standalone.jar clojure.main


(require '(cascalog [workflow :as w] [ops :as c]
                                  [vars :as v]))

(use 'cascalog.api)(use 'cascalog-tutorial.core)

(compute-count (hfs-textline "tmp/out1") "/mahout/input")

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
