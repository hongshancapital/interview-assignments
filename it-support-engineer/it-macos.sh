#!/bin/bash

grep 'error' interview_data_set | awk -F '[: ]' '
  {
  str=$8 
  sub(".[0-9]+.+","",str)
  split(str,arr,".")
  l=length(arr)
  pname=arr[l]               #错误进程的名称
  if(pname==NULL)            #错误程序的名称
   {str=$7;sub(".[0-9]+.*","",str) ;pname=str}
  str=$8;sub(".([a-zA-Z]+.)+","",str);sub("(\\))","",str);sub("\\]","",str) 
  id[pname]=str              #错误进程id
  if(id[pname]==NULL)        #错误程序id
   {str=$7;sub("([a-zA-Z]+.)","",str);sub("]","",str); id[pname]=str}
  s=""
  for(i=10;i<NF;i++)
      s=s" "$i
  dscp[pname]=s                #错误进程的描述信息
  num[pname]++                 #统计错误进程发生的次数
  
  if(ftime[pname]==NULL)                      #记录第一次发生错误的时间
    {ftime[pname]=$3$4
    }
  ltime[pname]=$3$4            #记录最后一次发生错误的时间

  } 


  END{
  for(i in id)
 { print "{"
  printf "\"deviceName\":\"%s\",\n",$6;
  printf "\"processId\":\"%s\",\n",id[i];
  printf "\"processName\":\"%s\",\n",i;
  printf "\"description\":\"%s\",\n",dscp[i];
  printf "\"numberOfOccurrence\":\"%d\",\n",num[i];
  printf "\"timeWindows\":\"%s-%s\",\n",ftime[i],ltime[i];
  
  print "}"; 
  printf "\n\n";
 }
 } 
'
