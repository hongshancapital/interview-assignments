# Server Test Suite

如何测试：
* step 1. 执行yarn
* step 2. 执行yarn start
* step 3. 执行yarn test ( 测试代码在 scripts/test 目录下，使用python编写，请确保安装了python3，并且确保安装过 pytest，requests 这几个package。若没有，可使用python自带的包管理器pip进行安装，使用命令 pip install ${PackageName} )

## **/toolbox/create 接口**

test_create.py 包含该接口的测试代码

test_create.json 包含测试用例

功能性上设计了如下测试用例：
1. 添加数据库中无记录的数据。应可正常添加
2. 添加数据库中有记录的数据。应无法添加，因为主键重复
3. 添加空值。应添加失败
4. 添加非string类型的数值。
5. 添加异常值，如null， none，"null"， "none" 等。应能添加
6. 某个参数丢失，如tools。应添加失败


安全性上设计了如下测试用例：
1. SQL注入攻击，删除数据
2. SQL注入攻击，添加注释

## **/toolbox 接口**

test_toolbox.py 包含该接口的测试代码

test_toolbox.json 包含测试用例

这个接口在设计有些缺陷，因为没加分页查询，即添加参数 pageNum 和 pageSize

现在该接口会一次性返回 table toolbox_prefs 中的所有记录，若该 table 有几百万条记录，一次性查询后返回会出问题

无其他功能上测试用例

## **/toolbox/options 接口**

test_options.py 包含该接口的测试代码

test_options.json 包含测试用例

数据从代码直接生成返回，无其他功能测试用例

## 最后
有些测试用例无法在代码中体现，如下：

网络方向测试用例:
1. 4G/5G/wifi 调用
2. 弱网环境调用接口

性能方向测试用例：
1. 针对每个接口，压测5分钟，CPU平均使用率不超过60%，内存平均使用率不超过80%
2. 尝试寻找性能极限，需要TPS，CPU使用率和内存使用率等指标来判断
3. 稳定性测试。低TPS，7*24小时压测，服务可正常运行









