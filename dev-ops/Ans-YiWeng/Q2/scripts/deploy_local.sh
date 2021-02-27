#!/usr/bin/env bash
set -o errexit
set -o nounset

if [ "$#" -ne 3 ]; then
  echo 'Usage: ./deploy_local.sh <app> <version> <container_name>'
  exit 1
fi

echo "Start container..."
app=${1}
version=${2}
container_name=${3}

# Find a free test port from 3000-65000
port=$(netstat -aln | awk '
  $6 == "LISTEN" {
    if ($4 ~ "[.:][0-9]+$") {
      split($4, a, /[:.]/);
      port = a[length(a)];
      p[port] = 1
    }
  }
  END {
    for (i = 3000; i < 65000 && p[i]; i++){};
    if (i == 65000) {exit};
    print i
  }
')

docker run --name "${container_name}" -p "${port}:8080" -td "${app}:${version}"
sleep 3s
output=$(curl "http://localhost:${port}/${app}/actuator/health")

echo "Container created:...${output}"
