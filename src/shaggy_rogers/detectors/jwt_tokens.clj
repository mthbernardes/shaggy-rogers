(ns shaggy-rogers.detectors.jwt-tokens
  (:require [clj-jwt.core  :refer :all]))

(def ^:private jwt-regex #"eyJh[A-Za-z0-9-_=]+\.[A-Za-z0-9-_=]+\.[A-Za-z0-9-_.+\/=]*")

(defn- valid-jwt? [jwt-token]
  (try
    (-> jwt-token str->jwt boolean)
    (catch Exception _
      false)))

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :jwt/handler :finding finding :text-document text-document})
  (let [jwt-tokens (->> text-document
                        (re-seq jwt-regex)
                        (filter valid-jwt?))]
    (if (empty? jwt-tokens)
      finding
      (assoc finding :jwt-detector {:tokens jwt-tokens}))))
