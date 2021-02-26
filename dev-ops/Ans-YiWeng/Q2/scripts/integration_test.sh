#!/usr/bin/env bash
set -o errexit
set -o nounset

if [ "$#" -ne 2 ]; then
  echo 'Usage: ./integration_test.sh <app> <version>'
  exit 1
fi

echo "Start integration test..."
exit_code=0
app=${1}
version=${2}
expectation='{"status":"UP"}'

# Cleanup function
cleanup () {
  echo "Terminating test container..."
  docker rm -f integration-test
  exit $exit_code
}

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

docker run --name integration-test -p "${port}:8080" -td "${app}:${version}"
sleep 3s
output=$(curl "http://localhost:${port}/${app}/actuator/health")

# Test /actuator/health
if [ "${output}" = "${expectation}" ]
then
        echo "Integration test passed..."
else
        echo "Integration test failed..."
        exit_code=1
fi

trap cleanup EXIT ERR INT TERM

exit_code=$?