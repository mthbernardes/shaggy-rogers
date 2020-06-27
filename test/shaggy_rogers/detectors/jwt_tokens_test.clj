(ns shaggy-rogers.detectors.jwt-tokens-test
  (:require [clojure.test :refer :all]
            [shaggy-rogers.detectors.jwt-tokens :as jwt-tokens]))

(deftest handler
  (testing "testing handler fn"
    (testing "When there's a jwt token in the document"
      (let [token "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            finding {:text-document token}
            result (assoc-in finding [:jwt-detector :tokens] [token])]
        (is (= result
               (jwt-tokens/handler finding)))))

    (testing "When there's a broke jwt token in the document"
      (let [token "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            finding {:text-document token}]
        (is (= finding
               (jwt-tokens/handler finding)))))

    (testing "When there isn't a jwt token in the document"
      (let [finding {:text-document "bananan"}]
        (is (= finding
               (jwt-tokens/handler finding)))))))