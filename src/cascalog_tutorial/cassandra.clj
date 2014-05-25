(ns cascalog-tutorial.cassandra
  (:use
    ;clojurewerkz.cassaforte.cql
    ;clojurewerkz.cassaforte.query
    cascalog.api)
  (:require ;[clojurewerkz.cassaforte.client :as client]
    [clojure.tools.logging :as log]
    [cascalog.logic.ops :as c])
  (:import [cascading.tuple Fields]
           [cascading.scheme Scheme]
           [com.ifesdjeen.cascading.cassandra CassandraTap]
           [com.ifesdjeen.cascading.cassandra.cql3 CassandraCQL3Scheme]
           [org.apache.cassandra.thrift Column])
  )

(defn create-source-tap
  [conf]
  (let [defaults {"db.host"     "localhost"
                  "db.port"     "9160"
                  "db.keyspace" "searchly"}
        scheme (CassandraCQL3Scheme. (merge defaults conf))
        tap (CassandraTap. scheme)]
    tap))

(defn get-tap [account startdate enddate]
  (create-source-tap {"db.columnFamily"     "analytics"
                      "types"               {"account"     "UTF8Type"
                                             "date"        "LongType"
                                             "clustername" "UTF8Type"
                                             "apikey"      "UTF8Type"
                                             "esindex"     "UTF8Type"
                                             "query"       "UTF8Type"
                                             "hits"        "Int32Type"
                                             "user"        "UTF8Type"
                                             }

                      ;"source.whereClauses" (str "user = '" account "'" " AND date < " enddate)
                      "source.whereClauses" (str "account = '" account "' AND date > " startdate " AND date < " enddate)
                      ;"source.whereClauses" (str "user = '" account "'")
                      ;"source.CQLPageRowSize" "5000"
                      ;default was 10.000 and this caused occasional timeouts on map reduce side.
                      })
  )

(defn exec-topqueries [accountname start end]
  (let [tap (get-tap accountname start end)]
    (?<-
      (stdout)
      [?esindex ?query ?count]
      (tap ?account ?date _ _ ?esindex ?hits ?query _) ;should be in db order
      (= ?account accountname)
      (c/count ?count)
      (> ?date start)
      (< ?date end)
      ;(:trap (stdout))
      )
    )
  )