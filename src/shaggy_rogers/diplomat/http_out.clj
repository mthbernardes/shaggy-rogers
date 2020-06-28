(ns shaggy-rogers.diplomat.http-out
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

;; It may not work correctly because this trumail api
;; may not work without an API token
(defn valid-email? [email]
  (let [{:keys [deliverable]
         :or {deliverable true}} (try
                                   (-> (format "https://api.trumail.io/v2/lookupsa/json?email=%s" email)
                                       client/get
                                       :body
                                       (json/read-str :key-fn keyword))
                                   (catch Exception _
                                     (println {:log ::validate-email? :status :failed})))]
    deliverable))