# Web Test Suite

如何启动服务
1. 首先要启动 server 端，进入server文件夹下 使用 yarn start
2. 再启动 Web 端，进入 Web 文件夹下，使用yarn start
3. 然后输入测试用例

测试页面包含数据展示，输入框，单项选择框

针对输入框设计测试用例:

1. 超长字符串
2. 英文数字字符
3. 汉字字符
4. 异常值字符, 例如空格，null等
5. 具有攻击性的代码字符，例如 rm -rf /, DROP TABLE toolbox_prefs, /* user */ 等

结果截图请查看当前目录下的 WebTestReport.png

测试后，发现两个问题

1. 首次进入页面，无论输入框中输入什么，单选框不主动选择，默认选择E2E，然后点击submit。
就会出现一个BUG: 前端传递的是 tools: 1, 而不是 tools: 'E2E'。 截图请查看当前目录下的 BUG01.png

2. 有超长值添加成功后，页面布局被打乱。截图请查看当前目录下的 BUG02.png


## 最后

由于以上测试都在 chrome 中完成，还需要其他浏览器兼容性测试:

1. 在PC Firefox上操作以上测试用例
2. 在MAC/iOS Safari上操作以上测试用例
3. 在Android 自带浏览器上操作以上测试用例
