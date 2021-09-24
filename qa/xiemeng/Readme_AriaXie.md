## 存在问题及优化建议：
<<<<<<< HEAD
1，目前tools是单选，考虑到实际工作中，测试工程师的技能并不单一，为更方便快捷完成录入，tools可做成多选。并且，tools应可扩展，即增加“自定义”
2，实际情况是，name和tools都没有那么长，测试中输入内容过长时，页面布局会乱。可结合实际，增加长度限制。
3，web端：主键name出现重复值或缺失时，提交失败，提示“Error: Request failed with status code 400”。首先，提示语不明确，应优化。然后，再次正确提交后，错误提示仍在，应增加刷新。
4 ，app端：提交失败，无任何提示，应增加相应提示语。
5 ，server端，接口数据展示缺少分页，数据量大时，查询会影响性能。需增加分页
=======
1，目前tools是单选，考虑到实际工作中，测试工程师的技能并不单一，为更方便快捷完成录入，tools可做成多选。tools应可扩展，即增加自定义
2，实际情况是，name和tools都没有那么长，测试中输入内容过长时，页面布局会乱。可结合实际，增加长度限制。
3，web端：主键name出现重复值或缺失时，提交失败，提示“Error: Request failed with status code 400”。首先，提示语不明确，应优化。然后，再次正确提交后，错误提示仍在，应增加刷新。
4，app端：提交失败，无任何提示，应增加相应提示语。
6，server端，接口数据展示缺少分页，数据量大时，查询会影响性能。需增加分页
>>>>>>> f7476018432378f1ab1a9bf4952e2124ff9578d0

## 思路：
一，启动：
1，server端：cd  qa/server       使用命令# npm install             # npm start
2，web端：cd qa/web              使用命令# npm install             # npm start
3，app端：使用Xcode ，模拟器（未打包）

二，测试方向：
1，功能：用例设计
前提：移动网络/WIFI/弱网/断网

post~127.0.0.1:5000/toolbox/create
web/app：
1，全字段（name and tools）&& (name and tools，不重复)，点击【提交】，预期：提交成功，界面返显
2，全字段（name and tools）&&（name重复），点击【提交】，预期：因主键重复，提交失败。给与明确提示
3，缺字段 (only tools)，点击【提交】，预期：提交失败。给与明确提示
server：
1，body_data: 全字段（name and tools）&& (name and tools，不重复)，发送请求，预期：响应成功
2，body_data: 全字段（name and tools）&&（name重复），发送请求，预期：响应失败，msg提示
3，body_data: 缺字段（only name） or  (only tools) , 发送请求，预期：error，msg提示
4，body_data: 空，发送请求，预期：error，msg提示
5，body_data: 新增字段 （eg：age,sex）,  发送请求，预期：error，msg提示
备注：RedWood文件夹包含的是 接口自动化测试框架（Python）

get~127.0.0.1:5000/toolbox/options
web/app:
返显固定数据。预期：应可自定义
server:
返回固定数据。预期：应可根据post传入tools自定义数据，给与返回

get~127.0.0.1:5000/toolbox
web/app:
返显全部已提交数据，数据多或长时，影响页面美观，甚至错乱。预期：限制长度，增加分页
server:
当前请求返回 toolbox_prefs表中所有记录。预期：未来随数据量不断增大，可增加分页

2，兼容性：适配
web：主流浏览器
app：主流机型

3，性能：
数据量大时，考虑数据库读写

4，安全性：
可增加登录限制


















