(defproject shaggy-rogers "0.1.0-SNAPSHOT"
  :description "Clojure lambda which knows how to scan blob files and find sensitive information."
  :url "https://github.com/mthbernardes/shaggy-rogers"
  :dependencies [[org.clojure/clojure                "1.10.1"]
                 [org.clojure/data.json              "0.2.6"]
                 [clj-jwt                            "0.1.1"]
                 [mockfn                             "0.5.0"]
                 [clj-http                           "3.10.1"]
                 [com.amazonaws/aws-lambda-java-core "1.2.0"]
                 [com.novemberain/pantomime          "2.11.0"]
                 [com.cognitect.aws/api              "0.8.456"]
                 [com.cognitect.aws/endpoints        "1.1.11.789"]
                 [com.cognitect.aws/s3               "799.2.682.0"]]
  :aot :all)
