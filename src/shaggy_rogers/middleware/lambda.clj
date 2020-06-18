(ns shaggy-rogers.middleware.lambda
  (:import (java.util Map List)))

(defprotocol ConvertibleToClojure
  (->cljmap [o]))

(extend-protocol ConvertibleToClojure
  Map
  (->cljmap [o] (let [entries (.entrySet o)]
                  (reduce (fn [m [^String k v]]
                            (assoc m (keyword k) (->cljmap v)))
                          {} entries)))

  List
  (->cljmap [o] (vec (map ->cljmap o)))

  Object
  (->cljmap [o] o)

  nil
  (->cljmap [_] nil))
