# DevOps Assignment Q2
## Automatically execute unit tests before merging into master or main branch
Please refer to .git/hooks/prepare-commit-msg.
Note: `git merge` uses fast-forward merge by default. The prepare-commit-msg hook will not be invoked for fast-forward merge. This behavior can be suppressed with the --no-ff option.
## Create infrastructure and service by using AWS CDK
Please refer to [this repo](/q2-aws-cdk).
## Automatically deploy the application by using GitHub Action
Please refer to the comments at the beginning of .github/workflows/aws.yml for the steps.
## Monitor application logs and send alarm
Please refer to [this repo](/q2-aws-cdk).
The AWS CDK stack sends the application logs to CloudWatch and also sends Emails if ERROR is found in the log by using AWS SNS.
