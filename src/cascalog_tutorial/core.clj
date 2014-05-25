(ns cascalog-tutorial.core  
	(:use cascalog.api
		[cascalog.more-taps])
  	(:require [cascalog [vars :as v] [ops :as c]])
  	(:gen-class)
  	)

(defn count-ratings [output-tap in-dir]
    (?<- output-tap 
    	[?count] 
    	((hfs-delimited in-dir :delimiter ",") ?user ?item ?preference _)
		(c/count ?count)
	))


(defn movies-joined [output-tap ratings-dir movies-dir]
    (?<- output-tap 
    	[?user ?preference ?name ?category] 
    	((hfs-delimited ratings-dir :delimiter ",") ?user ?item ?preference _)
    	((hfs-delimited movies-dir :delimiter "::") ?item ?name ?category)
    	;(:trap (stdout))
		(c/count ?count)
	)
	)

(defn average-rating-for-each-movie [output-tap ratings-dir movies-dir]
    (?<- output-tap 
    	[?name ?avg ?count] 
    	((hfs-delimited ratings-dir :delimiter ",") ?user ?item ?preference _)
    	((hfs-delimited movies-dir :delimiter "::") ?item ?name ?category)
    	;(:trap (stdout))
    	(read-string ?preference :> ?pref)
		(c/count ?count)
		(c/sum ?pref :> ?sum)
   		(div ?sum ?count :> ?avg)
	)
	)

(defmapcatop split-category [cat-str] 
	(seq (clojure.string/split cat-str #"\|"))
)

(defn lowercase [w] (.toLowerCase w))

(defn average-rating-for-each-category [output-tap ratings-dir movies-dir]
    (?<- output-tap 
    	[?cat ?count ?avg] 
    	((hfs-delimited ratings-dir :delimiter ",") ?user ?item ?preference _)
    	((hfs-delimited movies-dir :delimiter "::") ?item ?name ?category)
    	;(:trap (stdout))
    	(read-string ?preference :> ?pref)
    	(split-category ?category :> ?CAT)
    	(lowercase ?CAT :> ?cat)
		(c/count ?count)
		(c/sum ?pref :> ?sum)
        (div ?sum ?count :> ?avg)
	)
	)

(defn -main [in-dir output-dir]
  (count-ratings (hfs-textline output-dir) in-dir))