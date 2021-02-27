#!/usr/bin/env bash
set -o errexit
set -o nounset

if [ "$#" -ne 2 ]; then
  echo 'Usage: ./delete_ecr_images.sh <app> <region>'
  exit 1
fi

APP=${1}
REGION=${2}


# Delete ecr images #
aws ecr list-images \
  --region $REGION \
  --repository-name $APP | \
jq -r ' .imageIds[] | [ .imageDigest ] | @tsv ' | \
  while IFS=$'\t' read -r imageDigest; do
    aws ecr batch-delete-image \
      --region $REGION \
      --repository-name $APP \
      --image-ids imageDigest=$imageDigest
  done
