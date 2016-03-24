(ns clj-workshop.parens-validator)

(defn- balancer [letters depth]
  (cond
    (< depth 0) false
    (and (> depth 0) (empty? letters)) false
    (and (= depth 0) (empty? letters)) true
    :else (let [i (case (first letters) \( 1 \) -1 0)]
            (balancer (rest letters) (+ depth i)))))

(defn parens-balanced?
  "Returns true if parens in the provided string
  are balanced, false otherwise. Ignores any other
  characters."
  [s]
  (balancer (seq s) 0))
