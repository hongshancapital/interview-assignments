һ�����̽���
1����Ŀ������  com.cn.scdt.DomainMappingApplication
2��swagger/knife��ַ  http://localhost:8080/swagger-ui.html   
                     http://localhost:8080/doc.html#/home
3���ӿ���
     com.cn.scdt.controller.DomainMappingController
4��jacoco���Ա���    
   ��Ԫ���Ը����ʽ�ͼ.png   
5�������ࣺ
  com.cn.scdt.DomainMappingControllerTest     
          
����ʵ��˼·
1���������洢�ӿ�ʵ��˼·
�ӿ�·�ɣ�/getShortUrl
������String longUrl
���أ� ResponseDto<String>  ����json��dataΪ������
˼·�����ù���������8λ����ַ�����Ϊkey,��������Ϊvalue����(key,value)��ʽ�洢��map��,
     ������md5���ܺ���Ϊkey1,���8λ�ַ�����Ϊkey, ��(key1,key)��ʽ�洢��map��
ʵ�ֲ��裺
1)������md5����
2)�ж�map���Ƿ���ڶ�����
3)������ֱ�ӷ���
    �����ڣ����ù���������8λ����ַ�����Ϊkey��Ϊ������,��������Ϊvalue����(key,value)��ʽ�洢��map�У�
    ������md5���ܺ���Ϊkey1,���8λ�ַ�����Ϊkey, ��(key1,key)��ʽ�洢��map�У�Ȼ�󷵻ض�����

2����������ȡ�ӿ�ʵ��˼·
   mapͨ��������ֱ�ӷ��س�����
   
3���Ľ��Ż�����
   1)�����÷ֲ�ʽ�ṹ������Ŀ�������ڵ㣬��ӳ���ϵ�洢��redis��
   2)�����������key���ù���ʱ���ֹredisռ�ÿռ����޴�
   

�������ÿ��
   springboot2.3.2 + knife4j2.0.1 + swagger2.9.2 + junit + jacoco + hutool5.3.3(md5����) + logback��־
   
�ġ����ܲ���
1������jvm��СΪ100M
2������ DomainMappingApplication ����vm���� -Xmx100M -Xms100M
3������10000000����ͬ��������ת�����󣬵��洢27272��longurl�������ڴ�������ϼ�54544����ֵ��
4���޸Ĳ��ԣ������15000��ʱ��map = null�����ֶ�����System.gc()��������������ʹ��;�����ܵ�38000���