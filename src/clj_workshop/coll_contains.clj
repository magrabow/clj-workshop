(ns clj-workshop.coll-contains)

(defn coll-contains?
  "Returns true if the provided coll contains the given x value"
  [coll x]
  (cond
    (empty? coll) false
    (= x (first coll)) true
    :else (coll-contains? (rest coll) x)))
