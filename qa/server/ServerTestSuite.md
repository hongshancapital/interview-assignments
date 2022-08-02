# Server Test Suite

主要针对/toolbox/create接口进行测试，/toolbox/options接口返回固定枚举值、/toolbox接口查询返回list故只做正向校验

testsuit路径为：
server/src/testcase/create_api.test.ts 
server/src/testcase/options_api.test.ts 
server/src/testcase/tools_api.test.ts 
git
Buglist：
/toolbox/create接口
1.服务端未对tools入参是否在枚举范围内做校验 bug严重程度3级
2.添加重复name信息时，返回给前段用户名已存在的友好提示 bug严重程度4级
3.create接口返回未通过健壮性校验的具体errMsg，如name参数值为空等 bug严重程度4级



接口测试思路
1.接口健壮性校验
1）必需参数值为空
2）必需参数值为null
3）必需参数缺失
4）入参参数值参数类型校验
5）参数最大长度校验（因sqlite表中name与tools两个字段均为text型，且最大长度支持到2^31-1(2,147,483,647)个字符，故接口用例中未针对长度设计用例）
6）参数值（tools）传入非枚举范围内参数，服务端应做校验
7）入参传入特殊字符（常用分隔符、数据库相关特殊字符、空格等）

2.业务流程校验
1）正向流程，添加全新用户，添加成功
2）异常流程
i.添加name已存在数据库中的参数值，接口返回用户已存在
ii.添加name已存在数据库中，但tools不同的数据，接口返回用户已存在

安全性
1.sql注入，name输入数据删除sql（delete from toolbox_prefs where name='吴先生' or 1=1 -- and tools='Headless Chrome'）

性能
100并发持续3分钟，关注90%响应时间、tps、错误率、服务器CPU及内存变化
