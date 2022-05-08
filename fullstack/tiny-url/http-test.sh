########################
#  api post speed test #
########################
#!/bin/bash
echo $(date "+%Y-%m-%d %H:%M:%S")
start=$(date +%s)
for (( i=1; i<=10000; i++ ))
do
    curl -d "{\"url\":\"http://www.google.com/$i\"}" \
    -H "Content-Type: application/json" \
    -X POST http://localhost:3030/api/
    printf "\n"
done
echo $(date "+%Y-%m-%d %H:%M:%S")
end=$(date +%s)
seconds=$(echo "$end - $start" | bc)
echo 'elapsed time: '$seconds' sec'
