#!/bin/bash

# ./Q3_sort_lines.sh filename

filename=$1
cat $1 | sort -t " " -k 2

