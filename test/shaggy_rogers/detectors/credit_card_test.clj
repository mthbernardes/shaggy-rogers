(ns shaggy-rogers.detectors.credit-card-test
  (:require [clojure.test :refer :all]
            [shaggy-rogers.detectors.credit-card :as credit-card]))

(def ^:private document-without-credit-card
  {:text-document
   "Banana de pijamas
   descendo pela escada"})

(def ^:private document-with-credit-card
  {:text-document
   "4024007158638436
    5364918872653151
    5571390519905973
    3538328035637628
    6011909190419909660
    377803973923669"})

(def result-when-there-is-a-credit-card
  {:credit-cards  {:american-express ["377803973923669"]
                   :diners-club      ["36491887265315"
                                      "38328035637628"]
                   :discover         ["6011909190419909"]
                   :jcb              ["3538328035637628"]
                   :mastercard        ["5364918872653151"
                                      "5571390519905973"]
                   :visa             ["4024007158638436"
                                      "4918872653151"]}
   :text-document "4024007158638436
    5364918872653151
    5571390519905973
    3538328035637628
    6011909190419909660
    377803973923669"})

(deftest handler
  (testing "checking if there's a credit card number"
    (testing "when there's a credit card number"
      (is (= result-when-there-is-a-credit-card
             (credit-card/handler document-with-credit-card))))
    (testing "when there ins't a credit card number"
      (is (= document-without-credit-card
             (credit-card/handler document-without-credit-card))))))
