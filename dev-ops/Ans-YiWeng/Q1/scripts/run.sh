#!/usr/bin/env bash
set -o errexit

# Post url for Q1
url=https://foo.com/bar

sh ./scripts/scan.sh
python3 ./scripts/process.py

curl -d ../log.json -k -X POST ${url} --header "Content-Type:application/json"