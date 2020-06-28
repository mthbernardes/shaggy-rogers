# shaggy-rogers
![image](https://user-images.githubusercontent.com/12648924/84968381-b85b8f00-b0ec-11ea-988b-709af25c97f0.png)

Clojure lambda which knows how to scan [blob files](https://tika.apache.org/0.9/formats.html#Supported_Document_Formats) and find sensitive information.

## Installation

### Deploy shaggy-rogers
- Create a role for this lambda allowing it to read all files in all buckets.
- Create a cloudtrail log on your account so every file uploaded to s3 will be logged.
- Create a bucket which will be used to store the lambda artifact. Then configure this bucket on `scripts/manager`
- Edit the `scripts/manage` script and add you account information and the correct `iam-role`
- Deploy the lambda with this command: `bash scripts/manage deploy`
- Create a cloudtrail trigger to your lambda.

### Update shaggy-rogers
`bash scripts/manage deploy`

### Detectors
- Credit card numbers
- JWT Tokens
- PII (CPF and emails)
- Shannon's entropy

### How to create an detector?
- Create your detector in the following path `src/shaggy_rogers/detectors/`.
- Add your detector handler on the def `invoke-all-detectors` inside the `src/shaggy_rogers/engine.clj` file.

#### Detector template
Simple example of a detector which finds the word banana in the files.

```clojure
(ns shaggy.rogers.detector.bananas)

(defn handler [{:keys [text-document] :as finding}]
  (println {:fn ::handler :finding finding :text-document text-document})
  (let [bananas (->> text-document
                    (re-seq #"banana"))]
    (if (empty? bananas)
      finding
      (assoc finding :banana-detector {:bananas bananas}))))
```
