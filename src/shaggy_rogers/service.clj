(ns shaggy-rogers.service
  (:gen-class :methods [^:static [handler [java.util.Map] java.util.Map]])
  (:require [shaggy-rogers.engine :as engine]
            [cognitect.aws.client.api :as aws]
            [shaggy-rogers.middleware.lambda :as lambda]))

(def bypass-bucket
  #{"do-not-scan-this-bucket"})

(defn handler [{:keys [detail]}]
  (let [{:keys [awsRegion eventName requestParameters]} detail
        {:keys [bucketName key]} requestParameters
        s3 (aws/client {:api :s3 :region awsRegion})]
    (if (and (= eventName "PutObject") (not (contains? bypass-bucket bucketName)))
      {:result true :findings (engine/check-s3-file bucketName key s3) :reason "Success"}
      {:result false :reason "Not a PutObject event"})))

(def -handler (comp handler lambda/->cljmap))
