#!/bin/bash

FILE=log.txt

cat > $FILE <<- EOM
foo 1 
bar 4 
footer 3 
testline 5 
dafsd812342 9
EOM

cat log.txt | sort -n -r -k 2
