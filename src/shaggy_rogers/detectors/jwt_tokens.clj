(ns shaggy-rogers.detectors.jwt-tokens)

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :jwt/handler :finding finding :text-document text-document})
  (if-let [jwt-tokens (re-seq #"eyJh[A-Za-z0-9-_=]+\.[A-Za-z0-9-_=]+\.[A-Za-z0-9-_.+\/=]*" text-document)]
    (assoc finding :jwt-detector {:tokens jwt-tokens})
    finding))
