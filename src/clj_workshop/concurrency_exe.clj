(ns clj-workshop.concurrency-exe
  (:require [clj-workshop.utils :refer :all]))

(defn multi-increment
  "Start threads-count threads and make each increment the shared number (with initial value 0) inc-count times.
  Return the value of the shared number"
  [threads-count inc-count]
  (let [counter (atom 0)
        futures (map
                  (fn [_] (future (dotimes [n inc-count] (swap! counter inc))))
                  (range threads-count))]
    (doall futures)
    (doseq [f futures] @f)
    @counter))

(defn bank-simulation
  "Start threads-count threads and make each execute txn-count transactions where random amount of money
  is transferred between two accounts.
  Returns the sum of money from both accounts"
  [threads-count txn-count account1-init-val account2-init-val]
  (let [acc1-bal (ref account1-init-val)
        acc2-bal (ref account2-init-val)
        futures (map (fn [_]
                       (future
                         (dotimes [n txn-count]
                           (let [x (- (rand-int 9999) 5000)]
                             (dosync
                               (alter acc1-bal - x)
                               (alter acc2-bal + x))))))
                      (range threads-count))]
    (doall futures)
    (doseq [f futures] @f)
    (+ @acc1-bal @acc2-bal)))
