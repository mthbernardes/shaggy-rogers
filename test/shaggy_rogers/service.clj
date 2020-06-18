(ns shaggy-rogers.service
  (:require [clojure.test :refer :all]))

(def cloud-input-trail-example {:detail-type "AWS API Call via CloudTrail"
                                :time        "2020-06-16T06:37:36Z"
                                :source      "aws.s3"
                                :account     "111111111111"
                                :region      "us-east-1"
                                :id          "038c9af0-2d30-8bdb-7b7b-ca56bdcc3836"
                                :version     0
                                :resources   []
                                :detail      {:eventType           "AwsApiCall"
                                              :eventName           "PutObject"
                                              :eventCategory       "Data"
                                              :awsRegion           "us-east-1"
                                              :recipientAccountId  111111111111
                                              :additionalEventData {:SignatureVersion     "SigV4"
                                                                    :CipherSuite          "ECDHE-RSA-AES128-GCM-SHA256"
                                                                    :bytesTransferredIn   "410903.0"
                                                                    :AuthenticationMethod "QueryString"
                                                                    :x-amz-id-2           " +FQrd2FHXzT3ep0/Oc3MwDy+bTaF4KPLeLCv42RZo8Sr/LrXXvGjzpn9YAUv8Wj+Rak70rbxay4="
                                                                    :bytesTransferredOut  "0.0"}
                                              :requestID           "EC4F6C9CA7847B32"
                                              :responseElements    nil
                                              :sourceIPAddress     "127.0.0.1"
                                              :requestParameters   {:x-amz-storage-class "STANDARD"
                                                                    :X-Amz-Date          "20200616T063734Z"
                                                                    :key                 "pii.pdf"
                                                                    :x-amz-acl           "private"
                                                                    :X-Amz-Algorithm     "AWS4-HMAC-SHA256"
                                                                    :X-Amz-Expires       300
                                                                    :bucketName          "a-lot-of-documents-here"
                                                                    :Host                "a-lot-of-documents-here.s3.us-east-1.amazonaws.com"
                                                                    :X-Amz-SignedHeaders "content-md5 ;content-type;host;x-amz-acl;x-amz-storage-class}"
                                                                    :managementEvent     false
                                                                    :userIdentity        {:type           "Root"
                                                                                          :principalId    111111111111
                                                                                          :arn            "arn:aws:iam::111111111111:root"
                                                                                          :accountId      111111111111
                                                                                          :accessKeyId    "AAAAAAAAAAAAAAAAAAAA"
                                                                                          :sessionContext {:attributes {:creationDate     "2020-06-16T05:50:19Z"
                                                                                                                        :mfaAuthenticated false}}}
                                                                    :eventVersion        "1.07"
                                                                    :readOnly            false
                                                                    :userAgent           "User-agent"
                                                                    :eventTime           "2020-06-16T06:37:36Z"
                                                                    :eventSource         "s3.amazonaws.com"
                                                                    :resources           [{:type "AWS::S3::Object"
                                                                                           :ARN  "arn:aws:s3:::a-lot-of-documents-here/pii.pdf"}
                                                                                          {:accountId 111111111111
                                                                                           :type      "AWS::S3::Bucket"
                                                                                           :ARN       "arn:aws:s3:::a-lot-of-documents-here"}]
                                                                    :eventID             "b6ea9985-2345-41d0-80b3-18cf48ce542d"}}})
