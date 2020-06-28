(ns shaggy-rogers.detectors.entropy-test
  (:require [clojure.test :refer :all]
            [shaggy-rogers.detectors.entropy :as entropy]))

(def ^:private document-with-high-entropy-string {:text-document "Username: admin\npassword: jJ,.vx>N\"@E&c]5k"})

(def ^:private document-without-high-entropy-string {:text-document "o rato roeu a roupa do rei de roma"})

(deftest handler
  (testing "checking if there's a high entropy string"
    (testing "when there's a high entropy string"
      (is (= {:high-entropy  [{:score 4.0
                              :word  "jJ,.vx>N\"@E&c]5k"}]
             :text-document "Username: admin\npassword: jJ,.vx>N\"@E&c]5k"}
            (entropy/handler document-with-high-entropy-string))))

    (testing "when there ins't a credit card number"
      (is (= document-without-high-entropy-string
             (entropy/handler document-without-high-entropy-string))))))
