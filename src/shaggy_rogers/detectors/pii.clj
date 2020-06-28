(ns shaggy-rogers.detectors.pii
  (:require [shaggy-rogers.logic.detectors.pii :as logic.pii]))

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :pii/handler :finding finding :text-document text-document})
  (let [pii (logic.pii/collect-all-piis text-document)]
    (if (empty? pii)
      finding
      (assoc finding :pii-detector pii))))