import re
import sqlite3
import json
import http.client,urllib.parse
import requests
from requests.packages.urllib3.exceptions import InsecureRequestWarning 
import time



list_js=['deviceName','processId','processName','description','timeWindow','numberOfOccurrence']


log_db = sqlite3.connect('./log.db')
cursor = log_db.cursor()
#建表
cursor.execute('DROP TABLE IF EXISTS log')

sql='''CREATE TABLE log(
   ID INTEGER  PRIMARY KEY	AUTOINCREMENT NOT NULL, 
   NAME	varchar(20)	NOT NULL,
   PID	varchar(8)	NOT NULL,
   PNAME varchar(60) NOT NULL,
   P_SHORT_DESC varchar(20) NOT NULL,
   PDESC varchar(1000) NOT NULL,
   HTIME char(9) NOT NULL
)'''

cursor.execute(sql)
 

with open(file=r"interview_data_set") as f:

	errr_hour=[]
	err_type_list=[]
	hour_24="00"
	get_error_num=0
	errr_hour_num=0
	
	
	for line in f.readlines():
		if (line.find('error') != -1):
			linestr = line.split(" ")
			for i in range(0,len(linestr)):
				#print(linestr[i],end=" ")
				get_deviceName=linestr[3]
				#2 time
				#3 devicename
				#4 name pid
				
				get_time=linestr[2][0:2]
				prn_time=get_time+"00-"+str(int(get_time)+1).rjust(2,"0")+"00"
				##print (get_time,prn_time)

				get_pid=" ".join(re.findall('\d+',linestr[5]))
				##print(get_pid)
				
				regex=re.compile('[^a-zA-Z.]')
				
				get_pname=regex.sub('',linestr[5])
				
				#print(get_pname)
				
				err_desc=" ".join(linestr[6:])
				#if (err_desc in err_type_list):
				#	pass
				#else:
				#	err_type_list.append(err_desc)
				#print (err_desc)
				#print(linestr[2],linestr[3],linestr[4],linestr[5],err_desc)
		
			
			if (get_time>hour_24):
				hour_24=get_time
				errr_hour_num=get_error_num
				get_error_num=1
				
			else:
				hour_24=get_time
				errr_hour_num=get_error_num
				
			get_error_num=get_error_num+1
			
			short_desc=err_desc[0:20]
			##$print (get_deviceName,get_pid,get_pname,short_desc,err_desc,prn_time)
			ins_sql="insert into log (NAME,PID,PNAME,P_SHORT_DESC,PDESC,HTIME) values('"+get_deviceName+"','"+get_pid+"','"+get_pname+"','"+short_desc+"','"+err_desc+"','"+prn_time+"')"
			
			cursor.execute(ins_sql)

	hour_list=[]
	for set_hour in range(24):
		hour_list.append((str(set_hour).rjust(2,"0")))	
		##print (set_hour,str(set_hour).rjust(2,"0"))
	
	#print (hour_list)
	for get_hour in range(24):
		pass
		##print (get_hour,hour_list[get_hour])
		
	#print (*err_type_list)
	
	
	
	
f.close()	


log_db.commit()

search_sql="select name,pid,pname,pdesc,htime,count(P_SHORT_DESC) from log group by HTIME,P_SHORT_DESC,name"
cursor.execute(search_sql)

result = cursor.fetchall()

#print(len(result))



def loop_msg():
	for send_i in range(0,len(result)):
		dict_all = dict(zip(list_js, result[send_i]))
		comm_str=json.dumps(dict_all)
		##print (comm_str)
		#
		#post
		#url = 'https://192.168.31.113,80'
		url = 'https://foo.com/bar'
		headers = {
			'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36'
		}
		requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
        
		response = requests.post(url=url, headers=headers, json=comm_str, verify=False)
		page_text = response.text
        
		#print(send)
		print ("send",send_i,"OK")
		response.close()
		time.sleep (2)


loop_msg()

log_db.commit()
log_db.close()	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	