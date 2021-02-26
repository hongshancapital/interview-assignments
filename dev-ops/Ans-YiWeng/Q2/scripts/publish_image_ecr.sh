#!/usr/bin/env bash
set -o errexit

if [ "$#" -ne 4 ]; then
  echo 'Usage: ./publish_image_ecr.sh  <app> <version> <repo_id> <region>'
  exit 1
fi

app=${1}
version=${2}
repo_id=${3}
region=${4}


aws ecr get-login-password  --region "${region}" | docker login --username AWS --password-stdin "${repo_id}"
docker tag "${app}:${version}" "${repo_id}/${app}:${version}"
docker push "${repo_id}/${app}:${version}"