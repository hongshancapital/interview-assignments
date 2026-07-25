# 工程简介
本程序为url的长短域名转换程序，暂未考虑长域名转换为短域名时输入的字符串不是http(s)的情况。

# 设计说明
1.计算url的hash，由于短域名最多允许显示8位字符，故计算长hash时使用64位算法，并在此基础上检查是否有碰撞和碰撞次数；
2.每个hash32(32位hash)最多允许有2^12次碰撞(2^12位2个64进制数字)，超出则该短域名代表其他的长域名；
3.为便于碰撞处理，每个hash32转成6个64进制字符，不够的左补0。第一个新计算的hash32的短域名为6位字符，之后产生的有7到8位；
4.6个64进制数字可以表达36位数字，超出了32位无符号数字的表达范围，故32位数字内不会有碰撞。


# 延伸阅读


注意.在eclipse里运行项目，需要安装lombok插件

swagger 访问地址: http://localhost:8080/transfer/swagger-ui.html#



测试方法说明：
1.获取短域名测试，如：
        输入：https://github.com/scdt-china/interview-assignments/pull/604/commits/570951e0dbeff536c4733af5d63be61951179b5d
       输出：https://tu.com/2WnNY3
       
2.获取长域名输入测试，要先获取对应的短域名，不使用计算出的短域名获取长域名返回状态为-1(查询失败)，如：
        输入：https://tu.com/2WnNY3
        输出：https://github.com/scdt-china/interview-assignments/pull/604/commits/570951e0dbeff536c4733af5d63be61951179b5d