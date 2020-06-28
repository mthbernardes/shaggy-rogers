(ns shaggy-rogers.logic.detectors.entropy)

;; https://gist.github.com/agarman/dbb3a6649497c2eaa0353ada9f6639ae
(defn- log2 [n] (/ (Math/log n) (Math/log 2)))

(defn calculate
  "Calculate Shannon entropy for xs. Higher value is greater entropy.
  Values returned between 0.0 & 8.0"
  [xs]
  (let [freqs (vals (frequencies xs))
        cnt (reduce + freqs)
        calc #(let [p (double (/ % cnt))]
                (* p (log2 p)))]
    (reduce - 0 (map calc freqs))))