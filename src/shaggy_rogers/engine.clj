(ns shaggy-rogers.engine
  (:require [clojure.string :as string]
            [pantomime.extract :as extract]
            [shaggy-rogers.detectors.jwt-tokens :as detector.jwt]
            [shaggy-rogers.detectors.pii :as detector.pii]
            [cognitect.aws.client.api :as aws])
  (:import (java.io File)))

(defn- sanitize-text [text]
  (filter (fn [line]
            (not (string/blank? line)))
          text))

(defn- extract-text-from-file [filepath]
  (-> filepath
      extract/parse
      :text
      (string/split #"\n")
      sanitize-text
      string/join))

(def invoke-all-detectors
  (comp
    detector.pii/handler
    detector.jwt/handler))

(defn check-s3-file [bucketName key s3]
  (let [file-path (format "/tmp/%s" key)
        file-content (-> (aws/invoke s3 {:op :GetObject :request {:Bucket bucketName :Key key}}) :Body)
        _ (clojure.java.io/copy file-content (File. file-path))
        input {:text-document (extract-text-from-file file-path)}]
    (println {:fn :check-s3-file :input input})
    (-> input
        invoke-all-detectors
        (dissoc :text-document))))
