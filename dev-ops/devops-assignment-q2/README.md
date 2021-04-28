# DevOps Assignment Q2

## Structure Chart
![Structure Chart](/dev-ops/devops-assignment-q2/docs/img/structure-chart.png)

## Automatically execute unit tests before merging into master or main branch

Please add the following code into *.git/hooks/prepare-commit-msg*.
```bash
COMMIT_MSG_FILE=$1
COMMIT_SOURCE=$2
SHA1=$3

if [ "${COMMIT_SOURCE}" != "merge" ]
then exit 0
fi

current_branch=`git branch | grep '*' | sed 's/* //'`
if [[ "${current_branch}" != "master" && "${current_branch}" != "main" ]]
then exit 0
fi

echo "Preparing to merge to master..."
pytest=`source .venv/Scripts/activate && pytest`
pytest_result=$?
if [ "${pytest_result}" != 0 ]
then
  echo "${pytest}"
  echo `git merge --abort`
  echo "Merge canceled due to test failures."
  exit 1
fi
```

Note: `git merge` uses fast-forward merge by default. The prepare-commit-msg hook will not be invoked for fast-forward merge. This behavior can be suppressed with the --no-ff option.

## Create infrastructure and service by using AWS CDK

Please refer to [this folder](/dev-ops/devops-assignment-q2/q2-aws-cdk).

## Automatically deploy the application by using GitHub Action

Please refer to the comments at the beginning of .github/workflows/aws.yml for the steps.

## Monitor application logs and send alarm

Please refer to [this folder](/dev-ops/devops-assignment-q2/q2-aws-cdk).
The AWS CDK stack sends the application logs to CloudWatch and also sends Emails if ERROR is found in the log by using AWS SNS.
