1(ns cascalog-tutorial.stats
  	)

(defn box-kernel [x]
  (if (<= x 1)
    0.5
    0)
  )


(def kernel box-kernel)

(defn kde [dataset]
  (map #(kernel %1) dataset)
  )