# ShortenDomainService

## ˵��ǰ��Ļ�
��ʵ˵���Ҳ�û��ʹ��typescript�������ҵ�������λ������˵�����ȷ��һ�ֺܼ����ļ���ѡ�ͣ����Ƕ��ݵ���ԥ֮���һ���ѡ��������С��ҵ��Ҳ���ÿ����Լ��Ƿ�߱���ʱ��ȥѧϰһ���¼���������������Ϊ�Ҳ���һ��typescriptר�ң������һᾡ�����ҵĴ��뱣�ּ򵥣���ò�Ҫ���κ��ⲿ��������ע�ذ����������������

��һ��ʹ��javascript���ڱ��Ʊ�����Ŀ��ʹ��React����ǰ�˽��棬�Ǿ�ƾ����ģ���ļ��俪ʼ�ɡ�

## �������
tinyurl��ҵ����ʵ�Ѿ��зǳ�����ķ������ҵ���ҵҲ�����Ķ���һЩ����֮����ɣ�
- https://medium.com/@sandeep4.verma/system-design-scalable-url-shortener-service-like-tinyurl-106f30f23a82
- https://www.enjoyalgorithms.com/blog/design-a-url-shortening-service-like-tiny-url
- https://www.youtube.com/watch?v=JQDHz72OA3c

google�˶���������ĸ���ܽ�һ�¾���Ҫ��һ��long urlתΪһ��short url�����Һ�������ͨ��short url����long url���˸����ڲ�ͬҵ�񳡾���Ҳ���в�ͬ��Ҫ��������������Ϊϵͳ����һ���߽磺
- ����short url��long url��ӳ�䶼��һֱ���棻
- ���ɵ�short url���Ȳ��ܳ���8��

**1.����short url��long url��ӳ��**

����ѡ��[����](!https://medium.com/@sandeep4.verma/system-design-scalable-url-shortener-service-like-tinyurl-106f30f23a82)���ᵽȫ�����תΪbase62�����ַ��ķ�����ѡ��ȫ����Ҫ������������ֵ�ԭ����Ϊ�˼��ٸ��ӶȺͺ�ʱ������id֮���������Ƿ���ڣ�ѡ��base62�ַ���ԭ����base62ֻ�������ֺ���ĸ������url����������ܽ�һ���������֡��������������ɵ�url���Ȳ�����8����������Դ���62^8��ӳ�䣬����������Ѿ��ܴ��ˡ�ͨ��ȫ��idת���õ�һ��base62 string��Ϊkey������key������short url�����Ҵ洢key��long url��ӳ�䡣


**2.�洢short url��long url��ӳ��**

�洢�����ѡ��Ҳ����Ҫ�����������������SQL��NoSQL�����ԣ������������Ƚϴ��ʱ�����������NoSQL����д���ܸ��ã����˵�ǰ�����ŶӵĴ洢�󲿷��Ѿ��л���Level DB��

**3.����ϵͳ��scalability**
�κ�ϵͳ���ݽ������ж�����������ƿ����
- �߲�����web��������ͨ�������������������Ƶ�ʱ��Ҫ����谭�������ݵķ��������籾��cookie�ȣ�Ҳ��ͨ��cace���ӿ����ݴ���
- �������ͣ�һ��ͨ���ֿ�ֱ���������������ͨ��1�����ɵ�key�ĵ�һ���ַ����ֱ����������Ч������������Ҳ��ʹ��һ̨master serverά���ֱ���Ϣ����ѯ��ʱ��ͨ��keyȥmaster server��ȷ��ʹ�����ű�TiDB�����������ַ��������ֲ�ʽ�洢��

## ShortUrlServer�ܹ�ͼ
���ĸ�����һ�����˼·������������������ļܹ�ͼ��

![short_url_server_arch](./imgs/short_url_server_arch.png)

����һ����ʵ������web api���ֱ����ڴ���short url�Ͳ�ѯlong url����ض������£�
```typescript
CreateShortUrl
Method: POST
URL: /api/create_short_url
Request Body(json): {"long_url": "xxxxx"}
Response Body(json): {"short_url": "xxxxx"}

QueryLongUrl
Method: Get
URL: /api/query_long_url/:short_url
Response Body(json): {"long url": "xxx"}
```

��Ȼͼ�е������û��ȫ��ʵ�֣�����ID Generator Server�һ���һ��function���棬����һ���źŷ��񣬿���ʹ������snowflake���㷨��Database��Load Balancer����Ҳ����һ��function���棬�����ʹ��key�ĵ�һ���ַ�������Load Balance��

## Let's Coding

�����Express�ٷ��ĵ������ݳ�ʼ�̳�����ɴ��룬Ϊ�˱��ּ������û��ʹ�ý��ּ���������Ŀ������ֻ�м���ts�ļ�����Ŀ�Ǽ����£�
```
.
|-- index.ts // express server
|-- generator.ts // ģ��һ��id���ɵ�server
|-- database.ts // ģ��db���������е����ݾ����ڴ���
|-- util.js // ���ߴ���
|-- imgs // ͼƬ��ԴĿ¼
|-- package.json // npm ��Ŀ�����ļ�
|-- package-lock.json // lock�ļ�
|-- tsconfig.json // typescript �����ļ�
|-- start.sh // ����bash�ű�
|-- test.sh // ����bash�ű�
```
���ź�����Ϊ��ͷ�й���Ҫ��������û��ʱ��ȥ��jest�ĵ�����д��Ԫ���ԣ������ұ�д��һ���򵥵�shell�ű�������curl��ģ��һ�²��ԡ�

clone���뵽����֮����԰������²������飺
- ����**bash start.sh**�Ϳ����Զ�����ts��������8080�˿�����һ��web����
- �ֶ�ִ��curl���������飻
- ��������**bash test.sh**��ͨ���ű����ԣ�
```bash
// start a server
bash start.sh

// �ֶ�ִ��curl
curl -X POST -H "Content-Type: application/json" -d '{"long_url": "${test_url}"}' http://localhost:8080/api/create_short_url
curl -X Gethttp://localhost:8080/api/query_long_url/"${long_url}"

// �ű��������ԣ������100�β��ԣ���ؽ���������create.tmp��query.tmp����ʽ����long_url short_url
bash test.sh
```

## �ܽ�

������һ��඼��ʹ��c++���п������л���typescriptȷʵ��������Ŷ�Ҳ��һ��ģ��ʹ��node����̨������ֻ����Ϊһ��httpת��ģ�飬����ͦ����˼�ġ�

��Ȼ����ʱ���ϵ�������л��кܶ಻���Ƶĵط���Ҳ��ʱ��¼������
- cache���db�е�bloom filter��û��ʵ�֣��������ֶ���д�ٵ�Ӧ�ã������������������𵽺ܴ�����ã�
- ����ȫ��idֻ�Ǽ򵥵ĵ������������ɵ�short url�������ģ������ڷֿ�ֱ�֮��Ҳ���Ǿ��ȵģ�Ӧ�õ������Ծ��ȷźţ�
- û�п�����̭���ԣ��ܶೡ�����ɵ�short url����ʱЧ�ԣ�


## ���˽���
����21�걾�Ʊ�ҵ���人����ѧ��Ŀǰ��ְ����Ѷ����wxg����ҵ�����Ŷӵ��κ�̨������Ŀǰ��1���ĺ�̨�������飬����ʮ�������ڸ����¼�����ѧϰ��̽������Ͼʱ��Ҳ������ѧϰ�������ҡ���Ȼ��ʱû��typescript��̨�����ľ��飬��������л���ҲԸ������ȥѧϰ��

���˼���blog��github�������£�
- https://mr-xingjian.github.io/ 
- https://github.com/Mr-xingJian