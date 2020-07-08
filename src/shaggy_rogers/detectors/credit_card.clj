(ns shaggy-rogers.detectors.credit-card)

(def ^:private credit-card-regexes
  {:visa #"4[0-9]{12}(?:[0-9]{3})?"
   :mastercard #"(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}"
   :american-express #"3[47][0-9]{13}"
   :diners-club #"3(?:0[0-5]|[68][0-9])[0-9]{11}"
   :discover #"6(?:011|5[0-9]{2})[0-9]{12}"
   :jcb #"(?:2131|1800|35\d{3})\d{11}"})

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :jwt/handler :finding finding :text-document text-document})
  (let [matches (->> credit-card-regexes
                     (map (fn [[regex-name regex-value]]
                            (if-let [match (re-seq regex-value text-document)]
                              {regex-name match})))
                     (reduce merge))]
    (if (empty? matches)
      finding
      (assoc finding :credit-cards matches))))
