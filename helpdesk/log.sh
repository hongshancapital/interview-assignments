#!/bin/sh
error_json=$(python log.py)
curl -X POST "https://foo.com/bar" -H "accept: application/json" -d @err_json.json