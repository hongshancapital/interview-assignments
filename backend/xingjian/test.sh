#!/bin/bash

keys_arr=()

echo '' > create.tmp
echo '' > query.tmp

for i in {0..100}
do
    rand_str=$(cat /dev/urandom | LC_ALL=C tr -dc 'a-zA-Z0-9' | fold -w 50 | head -n 1)
    ouput=$(curl -X POST -H "Content-Type: application/json" -d "{\"long_url\": \"${rand_str}\"}" -s http://localhost:8080/api/create_short_url && echo '')
    short_url=$(echo $ouput | awk -F : '{print($2)}' | awk -F \" '{print($2)}')
    keys_arr+=("$short_url")
    echo $rand_str $short_url >> create.tmp
done

for key in ${keys_arr[@]}
do
    val=$(curl -X GET -s http://localhost:8080/api/query_long_url/${key} && echo '')
    long_url=$(echo $val | awk -F : '{print($2)}' | awk -F \" '{print($2)}')
    echo $long_url $key >> query.tmp
done

md5sum create.tmp
md5sum query.tmp
