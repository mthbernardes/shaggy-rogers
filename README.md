# shaggy-rogers

Clojure lambda which knows how to scan (blob files)[https://tika.apache.org/0.9/formats.html#Supported_Document_Formats] and find sensitive information.

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
