(ns shaggy-rogers.middleware.lambda-test
  (:require [clojure.test :refer :all]
            [shaggy-rogers.middleware.lambda :as lambda])
  (:import (java.util HashMap)))

(deftest ->cljmap
  (testing "testing ->cljmap conversion"
    (is (= {:first-key   "value"
            :second-key ["value1"
                         "value2"]}
           (lambda/->cljmap (HashMap. {"first-key" "value" "second-key" ["value1" "value2"]}))))))
