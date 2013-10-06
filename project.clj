(defproject cascalog-tutorial "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
  [org.clojure/clojure "1.5.1"]
  [cascalog "1.10.2"]
  ]
  :profiles {:provided {:dependencies [[org.apache.hadoop/hadoop-core "1.2.1"]]}}
  :uberjar-exclusions [#"^META-INF/LICENSE" #"^META-INF/ECLIPSEF.SF"])
