(ns shaggy-rogers.detectors.entropy
  (:require [clojure.string :as string]))

(def ^:private entropy-score 3.59)

(defn- log2 [n] (/ (Math/log n) (Math/log 2)))

(defn- entropy
  "Calculate Shannon entropy for xs. Higher value is greater entropy.
  Values returned between 0.0 & 8.0"
  [xs]
  (let [freqs (vals (frequencies xs))
        cnt (reduce + freqs)
        calc #(let [p (double (/ % cnt))]
                (* p (log2 p)))]
    (reduce - 0 (map calc freqs))))

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :jwt/handler :finding finding :text-document text-document})
  (let [matches (->> (string/replace text-document "\t" "")
                     string/split-lines
                     (map #(string/split % #" "))
                     flatten
                     (map (fn [word]
                            (let [entropy-value (entropy word)]
                              (if (< entropy-score entropy-value)
                                {:word word :score entropy-value}))))
                     (filter identity))]
    (if (empty? matches)
      finding
      (assoc finding :high-entropy matches))))
