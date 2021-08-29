#### 本项目实现思路
- 算法  
  本项目短网址采用随机固定长度加密的算法。目标产生最大长度为8位字符，而每一位是由 [a - z, A - Z, 0 - 9] 总共 62 个字母组成。  
  通常短网址会采用固定长度的字符，所以本项目采用固定8位长度，总共会有 62^8 ~= 218万亿种组合，对于随机产生的碰撞情况概率极低，且加密结果预测比较困难。
- 存储  
  短网址存储采用2个map方式存储，一个存储短网址到长网址的映射，一个存储长网址到短网址的映射。假设长网址长度平均50，短网址长度8，
  一个map存储7000万数据预计耗费4G内存左右，2个map存储7000万数据，预计耗费8G内存左右。生产环境建议加缓存和数据库存储方式。

#### 项目流程图
![流程图](./image/flow.png)


#### Jacoco测试报告
![测试报告](./image/jacoco.png)


#### Benchmark
采用JMH吞吐量测试模式，排除掉缓存影响
TPS:205137.849

Result "com.tinyurl.service.RandomTinyUrlCodecPerformanceTest.encode":  
  205137.849 ±(99.9%)　98555.613　ops/s　[Average]  
  (min,　avg,　max)　=　(133219.714,　205137.849,　318894.535),　stdev = 65188.436  
  CI　(99.9%):　[106582.236,　303693.463]　(assumes　normal　distribution)  
  
  
  Benchmark　　　　　　　　　　　　　　　　　　　Mode   　　　　　Cnt　　　　　Score　　　　　　　Error　　　　　　Units  
RandomTinyUrlCodecPerformanceTest.encode  　　　thrpt   　　　　　10  　　　　　205137.849 　± 　　98555.613  　　　　　ops/s