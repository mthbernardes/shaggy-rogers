(ns shaggy-rogers.logic.engine
  (:require [clojure.string :as string]
            [pantomime.extract :as extract]))

(defn- sanitize-text [text]
  (filter (fn [line]
            (not (string/blank? line)))
          text))

(defn extract-text-from-file [filepath]
  (-> filepath
      extract/parse
      :text
      (string/split #"\n")
      sanitize-text
      string/join))