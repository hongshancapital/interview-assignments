# 文档说明

## 设计思路
1、生成短域名的思路参考  https://www.cnblogs.com/rickiyang/p/12178644.html 这一片文章，主要是基于hash来实现的。

2、代码当中由于时间比较紧急没有考虑内存溢出的情形。我的解决思路是，放到linkedHashMap当中，对linkedHahsMap实现LRU算法。


