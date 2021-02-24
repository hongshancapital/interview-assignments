#!/usr/bin/env bash
set -o errexit

# Post url for Q1
url=https://foo.com/bar

sh ./scan.sh
python3 ./process.py

curl -d log.json -X POST ${url} --header "Content-Type:application/json"