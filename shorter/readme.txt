短域名转换服务

    包结构：commons：主要放置一些工具类，Hash类 和 Helper帮助类
           controller：放置api访问层
           service：放接口和实现层

   1：主要接口类 Shorter 提供对长域名或者原始域名的转换服务的操作
      主要定义方法有 shorting:原始URL转换短码,hasExist 查询是否存在一个短码
                   restore:将短码转换为原始的url，
                   update：将一个已经有的短码 对应的原始url 进行替换
      扩展定义了一个类型 type 为扩展使用 根据定义的类型来时实现不同的短码生成策略。目前提供type=0的实现
      内部接口 Atlas Body 为之后的对原始url添加属性做扩展设计，本期中只实现了其中的content包装原始的url
   2：接口实现类 ShorterImMemImpl 是内存存储数据的一种实现。
