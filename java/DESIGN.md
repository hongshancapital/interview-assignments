- 可通过阿里语雀文档阅读（便捷直观）:https://www.yuque.com/docs/share/3d7f9c1d-3a27-4147-a679-b2d4dc31dc1e?# 《短链服务设计思路与方案》
- 笔者简历：https://www.500d.me/cvresume/2157627102/
#短链服务设计思路与方案
##需求描述
实现短域名服务（细节可以百度/谷歌）
撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息。
- 短域名读取接口：接受短域名信息，返回长域名信息。
##限制
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；<br>
- 映射数据存储在JVM内存即可，防止内存溢出；
##递交作业内容
- 源代码(按照生产级的要求编写整洁的代码，使用gitignore过滤掉非必要的提交文件，如class文件)<br>
- Jacoco单元测试覆盖率截图(行覆盖率和分支覆盖率85%+)
- 文档：完整的设计思路、架构设计图以及所做的假设(Markdown格式)
##加分项
- 系统性能测试方案以及测试结果
##需求分析
从需求来看，要实现一个长域名和短域名相互转换功能，本质是实现了一个映射函数 f:X->Y，该映射函数必选满足两个特点：
```
1.如果x1 != x2，则f(x1) != f(x2);
2.对应每一个y，能都够找到一个唯一的一个x，使得y = f(x);
```
短址的长度假设为 N 位，而每一位是由 [a - z, A - Z, 0 - 9] 总共 62 个字母组成的，所以6位的话，总共会有 62^6 ~= 568亿种组合，基本上够用了。解决思路可以利用进制之间的转换。因为我们总共有 62 个字母，我们可以自创一种进制，叫做 62 进制。其规则如下：
```
0  → a
1  → b
...
25 → z
26 → 0
...
60 → Y 
61 → Z

public ArrayList<Integer> base62(int id) {
	
	ArrayList<Integer> value = new ArrayList<Integer>();
	while (id > 0) {
		int remainder = id % 62;
		value.add(remainder);
		id = id / 62;
	}
	
	return value;
}
```
对于每一个长地址，我们可以根据它的ID，得到一个N位的 62 进制数，这个N位的 62 进制数就是我们的短址。举例说明：对于 ID = 138，通过 base62(138), 我们得到 value = [14, 2]。根据上面的对应规则表，我们可以得到其对应的短址为：aaaabn 。
通过分析生成短链现在问题核心变成了如何生成唯一ID（分布式环境下）。
##前期调研
###行业调研
当前在一些社交媒体，用户增长、广告投放中有大量短链需求，将长连接转化为短连接使得链接变得清爽，用户点击率更高，同时能规避原始链接中一些关键词、域名屏蔽等。
常见微博、微信等社交软件中，比如微博限制字数为140，如果需要包含连接，但是这个连接非常长，将会占用非常大的篇幅。比较常见的短网址服务：
- 微博：http://t.cn
- 谷歌：https://goo.gl/
- 百度：http://dwz.cn/

也有新兴的垂直领域短链服务商，如bit.ly 已经是 Twitter 默认的网址缩短服务，这使得 bit.ly 成为了最受欢迎的网址缩短服务，大部分的Twitter客户端和服务如都采用 bit.ly 作为网址缩短服务之一，所以 bit.ly 使用的非常广泛，并将服务打包进行售卖。
###方案调研
短网址和长链接是一一对应的，核心问题是实现分布式唯一ID发号器，参考各类实现方案可归纳为两种类型实现方式：DB自增主键发号器、本地算法。下面介绍下一些调研的方案结果：
####DB自增主键发号器
建立短网址和长链接的对应关系，并产生一个唯一不变的ID，常用数据库自动增长的主键，类似以下结构：

| id  | long_url | short_url |
|  ----  | ----  | ----  |
| 自动增长   | 长URL | 短URL |

- 生成62位的字母表（26个小写字母+26个大写字母+10个数字） 
- 找到每一个长地址对应的ID，生成一个N位的62进制数 
- 根据该62进制数，查找字母表，生成短网址
映射关系可以存储在DB或者Redis，可以自增发号或者批量发号，具体参考业务场景是否需要考虑查询效率、延迟、并发等。该方法能保证唯一，且可逆，但是随着自增ID越来越长，短网址会越来越长，且方案需要DB存储，在需求要求并不吻合。

####本地算法
#####UUID
优势：
- 本地化生成，不依赖外部服务；
- 实现算法简单，无性能瓶颈；
- 提供多种版本，适用于不同场合；没有容量上限。

不足：

- 不适合主键：UUID输出较长，不便于存储；递增性差，除了Timed-Based版本外，其他版本无法保持趋势递增。
- Random-Based UUID理论上会产生重复ID。
- Time-Based UUID可反解得到时间戳、节点标识，如果Node使用MAC地址，存在一定的网络安全隐患。
#####压缩算法
对长链接进行压缩，生成唯一的短网址，例如生成6位短网址算法如下：

- 生成62位的字母表（26个小写字母+26个大写字母+10个数字）
- 对长链接md5生成32位字符串，分为4段，每段8个字节
- 每段转换为16进制，并和0x3fffffff(30位1)与操作，只处理30位，取6的倍数
- 30位分成6份，每5位数字作为字母表的索引，获取一个字符
- 循环6次，生成一个6位的字符串
- 32位md5字符串，可以生成4份6位串，取任意一个即可作为短网址

```
// 要使用生成 URL 的字符    
       String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,    
              "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,    
              "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,    
              "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,    
              "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,    
              "U" , "V" , "W" , "X" , "Y" , "Z"    
     
       };    
       // 对传入网址进行 MD5 加密    
       String sMD5EncryptResult = ( new CMyEncrypt()).md5(key + url);    
       String hex = sMD5EncryptResult;    
     
       String[] resUrl = new String[4];    
       for ( int i = 0; i < 4; i++) {    
     
           // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算    
           String sTempSubString = hex.substring(i * 8, i * 8 + 8);    
     
           // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界    
           long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);    
           String outChars = "" ;    
           for ( int j = 0; j < 6; j++) {    
              // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引    
              long index = 0x0000003D & lHexLong;    
              // 把取得的字符相加    
              outChars += chars[( int ) index];    
              // 每次循环按位右移 5 位    
              lHexLong = lHexLong >> 5;    
           }    
           // 把字符串存入对应索引的输出数组    
           resUrl[i] = outChars;    
       }    
       return resUrl;    
    }  
```
此算法可能存在碰撞，如果作为基础服务，且无法估量调用量的情况下，需要考虑。
#####SnowFlake算法
SnowFlake算法生成id的结果是一个64bit大小的整数，它的结构如下图：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647786316694-17d811df-3def-490f-bcdc-b3e9d2801bf8.png)
说明：
1. 首位无效符：第一个 bit 作为符号位，因为我们生成的都是正数，所以第一个 bit 统一都是 0。
2. 时间戳：占用 41 bit ，精确到毫秒。41位最好可以表示2^41-1毫秒，转化成单位年为 69 年。
3. 机器编码：占用10bit，其中高位 5 bit 是数据中心 ID，低位 5 bit 是工作节点 ID，最多可以容纳 1024 个节点。
4. 序列号：占用12bit，每个节点每毫秒0开始不断累加，最多可以累加到4095，一共可以产生 4096 个ID。

优势：
- 本地化生成，算法简单，效率高
- 适合主键字段：时间戳位于ID的高位，毫秒内自增序列在低位，ID趋势递增；
- 长度8个字节，适合数据库存储。
   
不足：
- 依赖机器时钟，如果时钟错误比如时钟不同步、时钟回拨，会产生重复ID
- 应用在容器化部署后，每次重启后ip地址信息经常变动，基于ip生成的Worker ID可能会重复
- ID容量局限性：时间偏移量支持2^41ms≈69年，可以在算法中自定义起始时间，年限略短，一般够用。

####参考链接
https://www.kerstner.at/2012/07/shortening-strings-using-base-62-encoding/
http://blog.csdn.net/beiyeqingteng/article/details/7706010
http://www.jianshu.com/p/d1cb7a51e7e5
https://stackoverflow.com/questions/742013/how-do-i-create-a-url-shortener
https://www.cnblogs.com/sunyuweb/p/11673001.html
https://blog.csdn.net/lq18050010830/article/details/89845790

##设计方案
###整体架构
![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647871490478-69463988-c0c0-4a67-9b77-53b97d3d2548.png)
####方案说明
1. 负载均衡策略
- 外部请求进入，可以按请求来源系统进行负载策略，策略可以实现调用方应用级别隔离，负载是路由到应用对应的任意一台机器。
- 好处是应用启动参数可以根据调用方定制，因为提供一个对外服务，可与调用方约定QPS、短链接需求量级等信息，不同业务按重要程度也有不同限流策略。
2. 短链接转长连接
- 发起生成短链接请求时，应用服务器接收请求，执行本地算法，先从通过长连接从缓存中获取，如果获取到则直接返回，此处防止重复发号，且防止极端情况下可能存在一个长连接就把发号器耗尽。
- 本地采用发号器组模式，因为发号器会存在加锁，采用发号器组类似分段锁机制能在一定程度提高性能。此处采用对长连接md5取后两位转十进制对发号器组数量取余，保证同一个链接每次请求都进入同一个发号器。
- 缓存不存在，则采用本地算法生成8位短链接，使用类SnowFlake算法由调用系统标识、机器号、发号器编号、ID编号组成，转码依赖本地十进制转换62进制算法（由[a-z][0-9][A-Z]共62位字符编码）。
3. JVM内存存储
- 最终数据要求存储在本地JVM中，采用主流缓存框架caffeine简易实现。
- 为防止JVM溢出，估算一次存储（长连接+短链接字符串长度大概为100来算）平均占约500个字节，限制缓存大小1G以下，大约估算最多存储200w条。
- 且短链接和长连接相互映射都存储下来，此处是为了防止重复发号和实现数据分片。
4. 长连接获取短链接
- 同一个调用方机器通过负载策略打到同一个应用集群的随机一台机器，如果本地缓存中可以获取则可直接从本地JVM缓存中读取返回。
- 若本地不存在，则可以转发至对应数据分片服务器。
   
####展望
1. JVM内存放不下则需数据分片
- 如架构图中黄色着色部分，如果量级很大，JVM本地存储无法满足，必须要进行数据分片存储，此时存储缓存时可按短链分片存储，当读取短链对应长连接时也需要进行分片路由。 
2. 可使用注册中心
- 本次实现机器号是测试配置在启动参数中，实际生产中从注册中心（ZK、nacos等）获取，应用服务器启动时进行注册，并获取到机器号。
3. 产品化对接流程
- 可实现对外开放的服务对接平台，要求填写相应的配置参数，可通过产品化方式对外发放应用系统编号标识，和后续应用部署流程。
4. 宕机后已发号段不重复
- 当前宕机后服务重启号段还会从头开始发放、可异步追加写入磁盘，记录机器号、发号器编码对应发号当前水位，应用重启时，可从磁盘读从上次宕机前水位再进行发号。当发号器超出限定阈值可进行异步消息报警。
##工程架构说明
- controller:API入口
- exception：应用异常封装
- generator：ID生成器
- model：包含API接口模板、接口请求、响应、配置等模型
- service：缓存和短链接服务接口和实现
- utils：工具类，实现进制转换
- test：单元自测用例
  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647869916805-14dce75f-6e65-49cd-ad45-678c9a3a66de.png)
###细节实现
####IDGenetator（ID生成器）
```
public class SimpleIDGenerator implements IDGenerator {
    /**
     * 发号器编号
     */
    private Long no;

    /**
     * 发号器起始编号
     */
    private Long currentIdNum;

    /**
     * 发号器id最大值
     */
    private Long maxIdNum;

    // 构造函数
    public SimpleIDGenerator(Long no, Long currentIdNum, Long maxIdNum) {
        this.no = no;
        this.currentIdNum = currentIdNum;
        this.maxIdNum = maxIdNum;
    }

    @Override
    public synchronized Long getNextId() {
        if (currentIdNum > maxIdNum) {
            log.error("there is no available id for IDGenerator which no = {}");
            // todo 可添加异步报警机制
            return null;
        }
        Long temp = currentIdNum;
        currentIdNum++;
        // todo 可异步写入磁盘（记录当前发号器发号水位，宕机后重启可从该水位继续发送）
        return temp;
    }

    @Override
    public Long getGeneratorNo() {
        return this.no;
    }
}
```
####进制转换
```
public class HexTransformatUtil {
    /**
     * 将十进制转换为任意进制值
     *
     * @param digths 转换后的进制最小位上，依次出现的字符值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
     * @param num    将被转换的十进制值
     * @param length 转换到指定字符串后，如果不足length长度位，自动补足最小值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"将在最前补"a"
     * @return
     */
    public static String hex10ToAnly(String digths, long num, long length) {
        StringBuffer str = new StringBuffer("");
        int base = digths.trim().length();
        if (0 == num) {
            str.append(digths.charAt(0));
        } else {
            Stack<Character> s = new Stack<Character>();
            while (num != 0) {
                s.push(digths.charAt((int) num % base));
                num /= base;
            }
            while (!s.isEmpty()) {
                str.append(s.pop());
            }
        }
        String prefix = "";
        String suffix = str.toString();
        if (length > suffix.length()) {
            for (int count = 0; count < length - suffix.length(); count++) {
                prefix = prefix + digths.charAt(0);
            }
        }
        return prefix + suffix;
    }

    /**
     * 将任意进制转换为十进制值
     *
     * @param digths   转换前的进制最小位上，依次出现的字符值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
     * @param hexValue 转换前的进制字符串值
     * @return
     */
    public static int hexAnlyTo10(String digths, String hexValue) {
        if (null == hexValue || "".equals(hexValue.trim())) return 0;
        int base = digths.trim().length();

        Map<String, Integer> digthMap = new HashMap<String, Integer>();
        int count = 0;
        for (char item : digths.trim().toCharArray()) {
            digthMap.put("" + item, count);
            count++;
        }
        String str = new StringBuffer(hexValue.trim()).reverse().toString();
        int sum = 0;
        for (int index = 0; index < str.length(); index++) {
            sum = new Double(Math.pow(base, index)).intValue() * digthMap.get("" + str.charAt(index)) + sum;
        }
        return sum;
    }
}
```
####短链接生成器
```
public String getShortLink(long sourceApp, String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }
        // 具体使用发号器
        IDGenerator generator = null;
        // 对长连接进行md5
        String linkMd5 = DigestUtils.md5DigestAsHex(longLink.getBytes(StandardCharsets.UTF_8));
        // 使用md5最后两位转10进制作为分片编号
        int linkShardNo = HexTransformatUtil.hexAnlyTo10(shortLinkAppConfig.characterSet, linkMd5.substring(linkMd5.length() - 2, linkMd5.length()));
        // 按分片号对机器发号器个数进行取余
        // 获取路由到的发号器
        Long no = linkShardNo % shortLinkAppConfig.IDGeneratorMaxSize;
        if (no != null && no < idGeneratorList.size()) {
            generator = idGeneratorList.get(no.intValue());
        }

        if (null == generator) {
            return null;
        }
        Long ID = generator.getNextId();
        if (ID == null) {
            return null;
        }
     
        // 应用编号+机器编号+发号器编号+ID编号
        String link = HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, sourceApp, shortLinkAppConfig.sourceAppBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, shortLinkAppConfig.testMachineId, shortLinkAppConfig.machineBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, generator.getGeneratorNo(), shortLinkAppConfig.IDGeneratorNumBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, generator.getNextId(), shortLinkAppConfig.IDBitSize);

        // 存入缓存
        cacheStoreService.put(link, longLink);
        return link;
    }
```

####配置参数
```
#短连接字符集
app.config.characterSet = abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ

#短链接总长度
app.config.shortLinkLength = 8

#应用占位长度大小
app.config.sourceAppBitSize = 2

#机器占位长度大小
app.config.machineBitSize = 1

#发号器编号占位长度大小
app.config.IDGeneratorNumBitSize = 1

#单发号器号数最大值（防止JVM内存溢出）
app.config.IDMaxNum = 1000000

#缓存过期时长(单位：秒)
app.config.expire = 3600

#缓存最大长度
app.config.cacheMaxSize = 500000

#以下配置为从配置中心获取，本地实现模拟从本地获取

#测试本机机器号
app.config.testMachineId = 42
```

##结果展示
###接口功能测试
- 获取长链接对应短链接
  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647777361422-3b1be197-6792-4585-801a-d42b267715ac.png)
- 获取短链接对应长链接
  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647777296687-e23e00af-49a6-4385-97ff-2cf035ab564b.png)
  使用postman本地测试接口。
###Jacoco报告
![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647785417391-3c455414-6a02-4116-a6b1-f86be430e4fc.png)
实现方式参考链接：https://blog.csdn.net/polo_longsan/article/details/105844985
###SwaggerUI
![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647825205036-25b94007-b854-4bd2-92c9-1ec13b80a189.png)
实现方式参考链接：https://www.cnblogs.com/zhaopengcheng/p/8583659.html
##性能测试
###测试工具
采用jmeter
###硬件环境
公司办公电脑，Mac Pro，参数如下：
CPU：2.6 GHz 六核Intel Core i7
内存：16g
###运行参数
####jemter设置
- 线程组
  
  线程数：200
  ramp-up：3
  循环次数：1000
- http请求配置，采用随机生成字符串
  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647913776944-cb4268c2-9f16-4be4-9457-7ccfed3e5618.png)
- 
####tomcat
```
##  最大线程数：建议这个配置数可以在服务器CUP核心数的200~250倍之间
server.tomcat.max-threads = 1000

##  最大连接数
server.tomcat.max-connections = 20000
```
####JVM
```
-Xms4028M 
-Xmx4028M 
-Xmn1024M 
-Xss1M 
```
###报告结果
![image.png](https://cdn.nlark.com/yuque/0/2022/png/26859869/1647918640918-47164075-d3d5-48ff-97e6-52294c9acfc8.png?x-oss-process=image%2Fresize%2Cw_888%2Climit_0)

