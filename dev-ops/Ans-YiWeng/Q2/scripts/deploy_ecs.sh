#!/usr/bin/env bash
set -o errexit

if [ "$#" -ne 4 ]; then
  echo 'Usage: ./deploy_ecs.sh  <app> <version> <repo_id> <region>'
  exit 1
fi

BASE_NAME=${1}
IMG_VERSION=${2}
REPO_ID=${3}
REGION=${4}

# Define variables
TASK_FAMILY="${BASE_NAME}TaskDefinition"
CLUSTER_NAME=aws-fargate

IMAGE_ID="${REPO_ID}/${BASE_NAME}:${IMG_VERSION}"
IMGAGE_PACEHOLDER="<IMGAGE_VERSION>"

CONTAINER_DEFINITION_FILE=$(cat "./deployments/${BASE_NAME}_task_definition.json")
CONTAINER_DEFINITION="${CONTAINER_DEFINITION_FILE//$IMGAGE_PACEHOLDER/$IMAGE_ID}"

ROLE_ARN=$(aws iam get-role --role-name aws-fargate-resources-roles-TaskRole --query 'Role.Arn' --output text)

export TASK_VERSION=$(aws ecs register-task-definition \
                              --cli-input-json "$CONTAINER_DEFINITION" \
                              --family "$TASK_FAMILY" \
                              --region "$REGION" \
                              --task-role-arn "$ROLE_ARN" \
                              --execution-role-arn "$ROLE_ARN" \
                              --query 'taskDefinition.revision'\
                              --output text)

echo "Registered ECS Task Definition: " "$TASK_VERSION"
if [ -n "$TASK_VERSION" ]; then
    echo "Update ECS Cluster: " $CLUSTER_NAME
    echo "Service: " "$BASE_NAME"
    echo "Task Definition: " "$TASK_FAMILY:$TASK_VERSION"

    DEPLOYED_SERVICE=$(aws ecs update-service \
                                --cluster $CLUSTER_NAME \
                                --service "$BASE_NAME" \
                                --task-definition "$TASK_FAMILY:$TASK_VERSION" \
                                --region "$REGION" \
                                --query 'service.serviceName'\
                                --output text)
    echo "Deployment of $DEPLOYED_SERVICE complete"
else
    echo "exit: No task definition"
    exit;
fi
