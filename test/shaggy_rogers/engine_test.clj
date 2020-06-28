(ns shaggy-rogers.engine-test
  (:require [clojure.test :refer :all]
            [mockfn.macros :as mfn.macro]
            [shaggy-rogers.engine :as engine]
            [cognitect.aws.client.api :as aws]))

(def ^:private document-with-sensitive-data
  "Manually confirming a SHA-256 JWT Signature\nLet's take that the same JWT as above and remove the signature and the second dot, leaving only the Header and the Payload part. That would look something like this:\n\neyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9. \nNow if you copy/paste this string to an online HMAC SHA-256 tool like this one, and use the password secret, we get back the JWT signature!\n\n")

(deftest check-s3-file
  (let [s3-client '..s3..]
    (testing "testing a file in s3 with sensitive data"
      (mfn.macro/providing
        [(aws/invoke s3-client {:op :GetObject :request {:Bucket "cool-balde-full-of-files" :Key "document-with-pii.pdf"}}) {:Body document-with-sensitive-data}]
        (is (= {:high-entropy [{:score 5.194392237281236
                                :word  "this:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9."}]
                :jwt-detector {:tokens ["eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9."]}}
               (engine/check-s3-file "cool-balde-full-of-files" "document-with-pii.pdf" s3-client)))))

    (testing "testing a file in s3 without any sensitive data"
      (mfn.macro/providing
        [(aws/invoke s3-client {:op :GetObject :request {:Bucket "cool-balde-full-of-files" :Key "document-without-pii.pdf"}}) {:Body "bananaa"}]00
        (is (= {}
               (engine/check-s3-file "cool-balde-full-of-files" "document-without-pii.pdf" s3-client)))))))