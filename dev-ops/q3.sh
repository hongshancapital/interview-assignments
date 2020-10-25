#!/bin/bash
if [[ -z $1 ]]; then
	echo "Usage: $basename $0 q3Example.txt"
	exit 65
fi
sort -k 2 -nr $1
