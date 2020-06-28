(ns shaggy-rogers.engine
  (:require [shaggy-rogers.logic.engine :as engine]
            [shaggy-rogers.detectors.jwt-tokens :as detector.jwt]
            [shaggy-rogers.detectors.pii :as detector.pii]
            [shaggy-rogers.detectors.credit-card :as detector.credit-card]
            [shaggy-rogers.detectors.entropy :as detector.entropy]
            [cognitect.aws.client.api :as aws]
            [clojure.java.io :as io])
  (:import (java.io File)))

(def ^:private invoke-all-detectors
  (comp
    detector.pii/handler
    detector.jwt/handler
    detector.entropy/handler
    detector.credit-card/handler))

(defn check-s3-file [bucketName key s3]
  (let [file-path (format "/tmp/%s" key)
        file-content (-> (aws/invoke s3 {:op :GetObject :request {:Bucket bucketName :Key key}}) :Body)
        _ (clojure.java.io/copy file-content (File. file-path))
        input {:text-document (engine/extract-text-from-file file-path)}]
    (io/delete-file file-path)
    (println {:fn :check-s3-file :input input})
    (-> input
        invoke-all-detectors
        (dissoc :text-document))))