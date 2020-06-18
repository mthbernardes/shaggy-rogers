(ns shaggy-rogers.detectors.pii
  (:require [clojure.string :as string]))

(defn- calculate-cpf-checksum [digit digits]
  (-> (apply + (map * digits (range (inc digit) 1 -1)))
      (mod 11)
      (#(if (< % 2) 0 (- 11 %)))))

(defn- validate-cpf [cpf]
  (let [cpf (string/replace cpf #"[\-|\.]" "")
        digits (->> cpf (re-seq #"\d") (map #(Integer/parseInt %)))
        blacklisted? (partial re-find #"^12345678909|(\d)\1{10}$")
        formatted? (partial re-find #"^\d{11}$")
        checksum-ok? (fn [digit digits] (= (calculate-cpf-checksum digit digits) (nth digits digit)))]
    (and
      (not (blacklisted? cpf))
      (formatted? cpf)
      (checksum-ok? 9 digits)
      (checksum-ok? 10 digits))))

(defn- find-cpf-by-regex [text-document]
  (re-seq #"[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}" text-document))

(defn- find-cpfs [founded-pii text-document]
  (if-let [cpfs (some->> text-document
                  (find-cpf-by-regex)
                  (filter validate-cpf)
                  set)]
    (assoc founded-pii :cpfs cpfs)))

(defn- collect-all-piis [text-document]
  (-> {}
      (find-cpfs text-document)))

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn :pii/handler :finding finding :text-document text-document})
  (if-let [pii (collect-all-piis text-document)]
    (assoc finding :pii-detector pii)
    finding))