(ns shaggy-rogers.detectors.entropy
  (:require [clojure.string :as string]
            [shaggy-rogers.logic.detectors.entropy :as logic.entropy]))

(def ^:private entropy-score 3.59)

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :jwt/handler :finding finding :text-document text-document})
  (let [matches (->> (string/replace text-document "\t" "")
                     string/split-lines
                     (map #(string/split % #" "))
                     flatten
                     (map (fn [word]
                            (let [entropy-value (logic.entropy/calculate word)]
                              (if (< entropy-score entropy-value)
                                {:word word :score entropy-value}))))
                     (filter identity))]
    (if (empty? matches)
      finding
      (assoc finding :high-entropy matches))))
