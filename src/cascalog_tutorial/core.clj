(ns cascalog-tutorial.core  
	(:use cascalog.api
		[cascalog.more-taps])
  	(:require [cascalog [vars :as v] [ops :as c]])
  	(:gen-class)
  	)



(defn textline-parsed [dir num-fields]
  (let [outargs (v/gen-nullable-vars num-fields)
        source (hfs-textline dir)]
    (<- outargs (source ?line) (c/re-parse [#"[^\s]+,"] ?line :>> outargs) (:distinct false))))

(defn mahout-data [dir] (textline-parsed dir 3))

(defn compute-count [output-tap in-dir]
    (?<- output-tap 
    	[?count] 
    	((hfs-delimited in-dir :delimiter ",") ?user ?item ?preference _)
		(c/count ?count)
	))


(defn movies-joined [output-tap ratings-dir movies-dir]
    (?<- output-tap 
    	[?user ?preference ?name] 
    	((hfs-delimited ratings-dir :delimiter ",") ?user ?item ?preference _)
    	((hfs-delimited movies-dir :delimiter "::") ?item ?name ?category)
    	;(:trap (stdout))
		(c/count ?count)
	)
	)

(defn -main [in-dir output-dir]
  (compute-count (hfs-textline output-dir) in-dir))