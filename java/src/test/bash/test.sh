#!/bin/bash
#-x

host=127.0.0.1

function testGen() {
  sum=$1
  echo -e "test genShortUrl -- status:200,sum:\t\r\c"
  s200=0
  s400=0
  s500=0
  for ((i = 1; i <= ${sum}; i++)); do
    #echo "http://${host}:9000/api/shorturl/gen?url=http://js.ixingk.org/${i}"

    statuscode=$(curl -s -I -L http://${host}:9000/api/shorturl/gen?url=http://js.ixingk.org/${i}|/bin/grep 'HTTP'|awk '{print $2}')
    #echo -ne "\b\r"
    case $statuscode in
    200)
      s200=$(echo "${s200}+1" | bc -q)
      echo -e "\t\t\t\t\t\e[1;32m${s200}\e[0m\r\c"
      ;;
    400)
      s400=$(echo "${s400}+1" | bc -q)
      echo -e "\t\t\t\t\t\tstatus:400, sum: \e[1;32m${s400}\e[0m\r\c"
      ;;
    500)
      s500=$(echo "${s500}+1" | bc -q)
      echo -e "\t\t\t\t\t\t\t\t\t\tstatus:500, sum: \e[1;32m${s500}\e[0m\r\c"
      ;;
#    301)
#      echo -ne "\t $statuscode"
#      ;;
#    302)
#      echo -ne "\t $statuscode"
#      ;;
#    404)
#      echo -ne "\t $statuscode"
#      ;;
#    500)
#      s500=$(echo "${s500}+1" | bc -q)
#      echo -ne "\tstatus:$statuscode, sum:${s500} \c"
#      ;;
    default)
      ;;
    esac
  done
}

function testGet() {
  sum=$1
  s200=0
  s400=0
  s500=0
  nums="0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

  echo -e "test getUrl ${sum}*62 times: stutus:200, sum: \t\r\c"
  for ((j = 1; j <=${sum}; j++)); do
    for i in ${nums}; do
      statuscode=$(curl -s -I -L http://${host}:9000/api/shorturl/geturl?s=AAAA000${i}|/bin/grep 'HTTP'|awk '{print $2}')
      #curl http://${host}:9000/api/shorturl/geturl?shorturl=AAAA000${i}
      case $statuscode in
    	200)
      		s200=$(echo "${s200}+1" | bc -q)
      		echo -e "\t\t\t\t\t\t\e[1;32m${s200}\e[0m\r\c"
      		;;
      400)
          s400=$(echo "${s400}+1" | bc -q)
          echo -e "\t\t\t\t\t\t\tstatus:400, sum: \e[1;32m${s400}\e[0m\r\c"
      		;;
      500)
          s500=$(echo "${s500}+1" | bc -q)
          echo -e "\t\t\t\t\t\t\t\t\t\t\tstatus:500, sum: \e[1;32m${s500}\e[0m\r\c"
      		;;
    	default)
      		;;
       esac
  	done
  done
}

#times testGen
