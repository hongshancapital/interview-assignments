# 作业: 实现长链接和短链接转换


## 先说一下我理解的为什么要使用短链接

**什么叫短链接:** 长网址缩短到一个很短的网址，用户访问这个短网址可以就可以访问到原本的长网址。这样可以达到易于记忆、转换的目的。

### 使用场景案例:新浪微博

我们在新浪微博上发布网址的时候，微博会自动判别网址，并将其转换，例如：https://t.cn/RuPKzRW。为什么要这样做的？

这是因为微博限制字数为140字一条，那么如果我们需要发一些链接上去，但是这个链接非常的长，以至于将近要占用我们内容的一半篇幅，这肯定是不能被允许的或者说用户体验很差的，所以短网址应运而生了，短网址这种服务可以说是在微博出现之后才流行开来的！往下看：

（1）首先，我先发一条微博带有一个URL地址： 


![image](https://github.com/earpick/operation/blob/master/IMG/%E6%96%B0%E6%B5%AA.png)


2）然后，看他转换之后显示的效果是什么样子的哪？ 

![image](https://github.com/earpick/operation/blob/master/IMG/%E6%96%B0%E6%B5%AA2.png)

（3）查看对应页面元素的HTML源码如下： 

![image](https://github.com/earpick/operation/blob/master/IMG/%E6%96%B0%E6%B5%AA3.PNG)

## ==实现== ##


一画胜千言


![image](https://github.com/earpick/operation/blob/master/IMG/%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86%E5%9B%BE.PNG)




## == JUnit+Jacoco生成测试报告 == ##
![image](https://github.com/earpick/operation/blob/master/IMG/JUnit%2BJacoco%E7%94%9F%E6%88%90%E6%B5%8B%E8%AF%95%E6%8A%A5%E5%91%8A.PNG)

