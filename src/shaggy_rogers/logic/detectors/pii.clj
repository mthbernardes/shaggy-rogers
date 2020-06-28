(ns shaggy-rogers.logic.detectors.pii
  (:require [clojure.string :as string]
            [shaggy-rogers.diplomat.http-out :as http-out]))

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

(defn- find-email-by-regex [text-document]
  (re-seq #"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])" text-document))

(defn- find-cpfs [founded-pii text-document]
  (let [cpfs (some->> text-document
                      find-cpf-by-regex
                      (filter validate-cpf)
                      set)]
    (if (empty? cpfs)
      founded-pii
      (assoc founded-pii :cpfs cpfs))))

(defn- find-emails [founded-pii text-document]
  (let [emails (some->> text-document
                        find-email-by-regex
                        (filter http-out/valid-email?)
                        set)]
    (if (empty? emails)
      founded-pii
      (assoc founded-pii :emails emails))))

(defn collect-all-piis [text-document]
  (-> {}
      (find-cpfs text-document)
      (find-emails text-document)))
