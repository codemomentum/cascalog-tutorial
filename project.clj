(defproject cascalog-tutorial "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                  [org.clojure/clojure "1.5.1"]
                  [cascalog/cascalog-core "2.0.0"]
                  [cascading-cassandra "1.0.0-SNAPSHOT"]
                  [cassandra-hadoop "0.1.6-SNAPSHOT"
                   :exclusions [org.apache.avro/avro]]
                  ]
  ;:profiles {:provided {:dependencies [[org.apache.hadoop/hadoop-core "2.0.0-cdh4.2.0"]]}}
  :uberjar-exclusions [#"^META-INF/LICENSE" #"^META-INF/ECLIPSEF.SF"]
  :repositories [ ["cloudera"  {:url "https://repository.cloudera.com/artifactory/cloudera-repos/"}]
                  ["cloudera2" {:url "https://repository.cloudera.com/artifactory/cdh-releases-rcs/"}]
                  ["cloudera3" {:url "https://repository.cloudera.com/artifactory/libs-release-local/"}]
                  ["cloudera4" {:url "https://repository.cloudera.com/artifactory/libs-release/"}]
                  ; ["nexus" {:url "http://10.138.48.48:8083/nexus/content/groups/public/"}]
                  ["conjars" {:url "http://conjars.org/repo"}]]
  )
