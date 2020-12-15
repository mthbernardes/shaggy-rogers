(defproject shaggy-rogers "0.1.0-SNAPSHOT"
  :description "Clojure lambda which knows how to scan blob files and find sensitive information."
  :url "https://github.com/mthbernardes/shaggy-rogers"

  :repositories [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                 ["clojars" {:url "https://clojars.org/repo/"}]]

  :plugins [[lein-cloverage "1.0.13" :exclusions [org.clojure/clojure]]
            [lein-vanity "0.2.0" :exclusions [org.clojure/clojure]]
            [lein-kibit "0.1.7" :exclusions [org.clojure/clojure]]
            [lein-cljfmt "0.6.1" :exclusions [org.clojure/clojure rewrite-clj org.clojure/tools.reader org.clojure/tools.cli]]
            [jonase/eastwood "0.3.5" :exclusions [org.clojure/clojure]]
            [s3-wagon-private "1.3.2" :exclusions [commons-logging org.apache.httpcomponents/httpclient]]
            [lein-ancient "0.6.15" :exclusions [com.fasterxml.jackson.core/jackson-databind com.fasterxml.jackson.core/jackson-core]]
            [lein-nsorg "0.2.0" :exclusions [rewrite-clj]]]

  ;:pedantic? :abort

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"]
                 [clj-jwt "0.1.1"]
                 [mockfn "0.5.0"]
                 [clj-http "3.10.1"]
                 [org.apache.thrift/libthrift "0.10.0"]
                 [org.apache.kafka/kafka-clients "2.6.0"]
                 [com.amazonaws/aws-lambda-java-core "1.2.1"]
                 [com.novemberain/pantomime "2.11.0" :exclusions [org.bouncycastle/bcpkix-jdk15on org.bouncycastle/bcprov-jdk15on]]
                 [com.cognitect.aws/api "0.8.484" :exclusions [org.clojure/data.json]]
                 [com.cognitect.aws/endpoints "1.1.11.813"]
                 [com.cognitect.aws/s3 "799.2.682.0"]]
  :aot :all

  :aliases {"lint"                                       ["do" ["cljfmt" "check"] ["nsorg"] ["eastwood" "{:namespaces [:source-paths]}"]]
            "lint-fix"                                   ["do" ["cljfmt" "fix"] ["nsorg" "--replace"]]})
