(ns shaggy-rogers.engine-test
  (:require [clojure.test :refer :all]
            [mockfn.macros :as mfn.macro]
            [shaggy-rogers.engine :as engine]
            [cognitect.aws.client.api :as aws]))

(deftest check-s3-file
  (let [s3-client '..s3..]
    (testing "testing a file in s3 with sensitive data"
      (mfn.macro/providing
        [(aws/invoke s3-client {:op :GetObject :request {:Bucket "cool-balde-full-of-files" :Key "file.pdf"}}) {:Body "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIn0.rTCH8cLoGxAm_xw68z-zXVKi9ie6xJn9tnVWjd_9ftE"}]
        (is (= {:jwt-detector
                {:tokens ["eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIn0.rTCH8cLoGxAm_xw68z-zXVKi9ie6xJn9tnVWjd_9ftE"]}}
               (engine/check-s3-file "cool-balde-full-of-files" "file.pdf" s3-client)))))

    (testing "testing a file in s3 without any sensitive data"
      (mfn.macro/providing
        [(aws/invoke s3-client {:op :GetObject :request {:Bucket "cool-balde-full-of-files" :Key "file.pdf"}}) {:Body "bananaa"}]
        (is (= {}
               (engine/check-s3-file "cool-balde-full-of-files" "file.pdf" s3-client)))))))